package road_fighter;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import road_fighter.entidades.Entidad;
import road_fighter.entidades.FPS;
import road_fighter.entidades.TextoStart;
import road_fighter.entidades.VelocidadInfo;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;

public class MenuStart extends SceneHandler {

	private Group rootGroup;

	public MenuStart(Main main) {
		super(main);
		//stage.setScene(this.scene);
		//load();
		//addTimeEventsAnimationTimer();
		//addInputEvents();
	}

	@Override
	protected void prepareScene() {
		 rootGroup = new Group();
		this.scene = new Scene(rootGroup, Config.width, Config.height, Color.BLACK);
	}

	@Override
	protected void defineEventHandlers() {
		
		keyEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case UP:
				case W:
				case SPACE:
				case ENTER:
					//main.startGame();
					main.startMenuSetting();
					break;
				case Q:
				case ESCAPE:
					System.exit(0);
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	public void update(double delta) {

	}

	@Override
	protected void load(boolean start) {
		
		Group root = new Group();
		rootGroup.getChildren().add(root);
		
		Image fondo = new Image("file:src/main/resources/img/start.png", Config.width, Config.height, false, false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);
		
		TextoStart menu = new TextoStart();
		root.getChildren().add(menu.getRender());

		
		Invocador.getInstancia().addToGroup(root.getChildren());
		AudioSound.getInstancia().playMenuSound();	

		
		if (start) {
			addTimeEventsAnimationTimer();
			addInputEvents();
		}
	}
	
	public void unload() {
		rootGroup.getChildren().remove(0);
		super.unload();
	}

}
