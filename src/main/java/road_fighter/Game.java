package road_fighter;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import road_fighter.entidades.Entidad;
import road_fighter.entidades.FPS;
import road_fighter.entidades.Jugador;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;

public class Game extends SceneHandler {

	@SuppressWarnings("unused")
	private Jugador jugador;
	private Partida partida;
	private FPS fpsInfo;

	public Game(Stage stage) {
		super();
		stage.setScene(this.scene);
		load();
		addTimeEventsAnimationTimer();
		//addInputEvents();
	}

	@Override
	protected void prepareScene() {
		Group root = new Group();
		this.scene = new Scene(root, Config.width, Config.height, Color.BLACK);
	}

	@Override
	protected void defineEventHandlers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(double delta) {
		super.update(delta);

		Invocador.getInstancia().calcularColisiones();
		partida.determinarPosiciones();
		fpsInfo.update(delta);
	}

	@Override
	protected void load() {
		Group root = new Group();
		scene.setRoot(root);

		Image fondo = new Image("file:src/main/resources/img/background2.png", Config.width, Config.height, false,
				false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);

		Mapa mapa = new Mapa(Config.height, 290, 624);
		mapa.agregarObstaculos(Entidad.AUTO_ESTATICO, 5000); // 8 fps...
		this.partida = new Partida(mapa, 2000L);
		partida.comenzar(1);

		fpsInfo = new FPS(fps);
		root.getChildren().add(fpsInfo.getRender());

		this.jugador = partida.getJugador(0);
		Invocador.getInstancia().addToGroup(root.getChildren());
	}

}
