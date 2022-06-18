package road_fighter;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
	private boolean screenShake = false;

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

		if (jugador.isExploto() && !screenShake) {
			TranslateTransition tt = new TranslateTransition(Duration.millis(50), scene.getRoot());
			tt.setFromX(-5);
			tt.setByX(5);
			tt.setAutoReverse(true);
			tt.playFromStart();
			tt.setOnFinished(event -> screenShake = false);
			screenShake = true;
		}
	}

	@Override
	protected void load(boolean start) {
		Invocador.getInstancia().setRoot(root);

		FPS fpsInfo = new FPS(fps, new Vector2D(Config.width * 0.78, Config.height * 0.9));
		VelocidadInfo velocidadInfo = new VelocidadInfo(new Vector2D(Config.width * 0.78, Config.height * 0.95));

		Mapa mapa = new Mapa(Config.mapaLength, Config.mapLeft, Config.mapRight);
		mapa.generarMapa(dificultad);

		this.partida = new Partida(mapa, 2000L);
		this.jugador = this.partida.comenzar(2);

		Invocador.getInstancia().add(fpsInfo);
		Invocador.getInstancia().add(velocidadInfo);

		AudioSound.getInstancia().stopSound();
		AudioSound.getInstancia().playGameStartSound();

		TimerTask startingUp = new TimerTask() {
			public void run() {
				AudioSFX.getInstancia().play("largada_start");
				AudioSound.getInstancia().playGameSound();
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
