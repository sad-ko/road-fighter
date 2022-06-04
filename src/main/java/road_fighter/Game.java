package road_fighter;

import java.util.Timer;
import java.util.TimerTask;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import road_fighter.entidades.FPS;
import road_fighter.entidades.VelocidadInfo;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Dificultad;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;

public class Game extends SceneHandler {

	private Group root;
	private Jugador jugador;
	private Partida partida;
	private Timer starting;
	private Dificultad dificultad;

	public Game(Main main, int dificultad) {
		super(main);
		this.dificultad = Dificultad.values()[dificultad];
	}

	@Override
	protected void prepareScene() {
		root = new Group();
		this.scene = new Scene(root, Config.width, Config.height, Color.BLACK);
	}

	@Override
	protected void defineEventHandlers() {
		SceneHandler me = this;

		keyEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {

				case Z:
					jugador.setZ(true);
					break;

				case LEFT:
					jugador.setLeft(true);
					break;

				case RIGHT:
					jugador.setRight(true);
					break;

				case Q:
				case ESCAPE:
					main.startMenuIntro(me);
					break;

				default:
					break;
				}
			}
		};

		keyReleasedEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {

				case Z:
					jugador.setZ(false);
					break;

				case LEFT:
					jugador.setLeft(false);
					break;

				case RIGHT:
					jugador.setRight(false);
					break;

				default:
					break;
				}
			}
		};
	}

	@Override
	public void update(double delta) {
		super.update(delta);

		Invocador.getInstancia().calcularColisiones();
		partida.determinarPosiciones();
		Config.currentVelocity = this.jugador.getVelocidad();
	}

	@Override
	protected void load(boolean start) {
		Invocador.getInstancia().setRoot(root);

		FPS fpsInfo = new FPS(fps, new Vector2D(800, Config.height - 50));
		VelocidadInfo velocidadInfo = new VelocidadInfo(new Vector2D(800, Config.height - 100));

		Mapa mapa = new Mapa(Config.height * 12, Config.mapLeft, Config.mapRight);
		mapa.generarMapa(dificultad);

		this.partida = new Partida(mapa, 2000L);
		this.jugador = this.partida.comenzar(2);

		Invocador.getInstancia().add(fpsInfo);
		Invocador.getInstancia().add(velocidadInfo);

		AudioSound.getInstancia().stopSound();
		AudioSound.getInstancia().playGameStartSound();

		jugador.setAceleracion(0);

		TimerTask startingUp = new TimerTask() {
			public void run() {
				AudioSFX.getInstancia().play("largada_start");
				AudioSound.getInstancia().playGameSound();
				jugador.setAceleracion(5);
				partida.getCompetidor(1).bot(true);
			}
		};

		starting = new Timer();
		starting.schedule(startingUp, 3000L);

		if (start) {
			addTimeEventsAnimationTimer();
			addInputEvents();
		}
	}

	@Override
	public void unload() {
		starting.cancel();
		root.getChildren().clear();
		super.unload();
	}

}
