package road_fighter.escenas;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import road_fighter.Config;
import road_fighter.Main;
import road_fighter.entidades.FPS;
import road_fighter.entidades.VelocidadInfo;
import road_fighter.entidades.cuerpos.Competidor;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;
import road_fighter.networking.Comando;

public class Game extends SceneHandler {

	private Group root;
	private Jugador jugador;
	private Partida partida;
	private Timer starting;
	private TranslateTransition screenShakeAnim;
	private boolean screenShake = false;

	public Game(Main main) {
		super(main);

		screenShakeAnim = new TranslateTransition(Duration.millis(50), scene.getRoot());
		screenShakeAnim.setFromX(-5);
		screenShakeAnim.setByX(5);
		screenShakeAnim.setAutoReverse(true);
		screenShakeAnim.setOnFinished(event -> screenShake = false);
	}

	@Override
	protected void prepareScene() {
		root = new Group();
		this.scene = new Scene(root, Config.width, Config.height, Color.BLACK);
		main.client.setCurrentScene(this);
	}

	@Override
	protected void defineEventHandlers() {
		SceneHandler me = this;

		keyEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {

				case Z:
					main.client.setZ(true);
					break;

				case LEFT:
					main.client.setLeft(true);
					break;

				case RIGHT:
					main.client.setRight(true);
					break;

				case Q:
				case ESCAPE:
					main.changeScene(me, Escenas.MENU_INTRO);
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
					main.client.setZ(false);
					break;

				case LEFT:
					main.client.setLeft(false);
					break;

				case RIGHT:
					main.client.setRight(false);
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
		main.client.mover();

		if (jugador.isExploto() && !screenShake) {
			screenShakeAnim.playFromStart();
			screenShake = true;
		}

//		if (main.client.colisionesPendientes != null) {
//			Invocador.getInstancia().colisiono(main.client.colisionesPendientes[0],
//					main.client.colisionesPendientes[1]);
//			main.client.colisionesPendientes = null;
//
//		}

		switch (main.client.comandoPendiente) {
		case HACER_NADA:
			break;

		case ACELERAR:
			int id = Integer.parseInt(main.client.dataPendiente[1]);

			if (id != main.client.id) {
				Competidor cmp = this.partida.getCompetidor(id);
				cmp.acelerar();
			} else {
				jugador.setZ(true);
			}

			main.client.comandoPendiente = Comando.HACER_NADA;
			break;

		case DESACELERAR:
			id = Integer.parseInt(main.client.dataPendiente[1]);

			if (id != main.client.id) {
				Competidor cmp = this.partida.getCompetidor(id);
				cmp.desacelerar();
			} else {
				jugador.setZ(false);
			}

			main.client.comandoPendiente = Comando.HACER_NADA;
			break;

		case DESPLAZAR_IZQUIERDA:
			id = Integer.parseInt(main.client.dataPendiente[1]);
			if (id != main.client.id) {
				Competidor cmp = this.partida.getCompetidor(id);
				cmp.desplazar(Comando.DESPLAZAR_IZQUIERDA);
			} else {
				jugador.desplazar(Comando.DESPLAZAR_IZQUIERDA);
			}
			main.client.comandoPendiente = Comando.HACER_NADA;
			break;

		case DESPLAZAR_DERECHA:
			id = Integer.parseInt(main.client.dataPendiente[1]);
			if (id != main.client.id) {
				Competidor cmp = this.partida.getCompetidor(id);
				cmp.desplazar(Comando.DESPLAZAR_DERECHA);
			} else {
				jugador.desplazar(Comando.DESPLAZAR_DERECHA);
			}
			main.client.comandoPendiente = Comando.HACER_NADA;
			break;

		default:
			main.client.comandoPendiente = Comando.HACER_NADA;
			break;
		}

		Config.currentVelocity = this.jugador.getVelocidad();
		partida.debug();
	}

	@Override
	public void load(boolean start) {
		Invocador.getInstancia().setRoot(root);

		FPS fpsInfo = new FPS(fps, new Vector2D(Config.width * 0.78, Config.height * 0.9));
		VelocidadInfo velocidadInfo = new VelocidadInfo(new Vector2D(Config.width * 0.78, Config.height * 0.95));

		Mapa mapa = new Mapa(Config.mapaLength, Config.mapLeft, Config.mapRight, main.client.seed);
		mapa.generarMapa(main.client.getCurrentSala().getDificultad());

		this.partida = new Partida(mapa, 2000L);
		this.partida.comenzar(main.client.getCurrentSala().getCantidadActual(), main.client.getPlayersInSala());
		this.jugador = this.partida.getJugador(main.client.id);

		Invocador.getInstancia().add(fpsInfo);
		Invocador.getInstancia().add(velocidadInfo);

		AudioSound.getInstancia().stopSound();
		AudioSound.getInstancia().playGameStartSound();

		TimerTask startingUp = new TimerTask() {
			public void run() {
				AudioSFX.getInstancia().play("largada_start");
				AudioSound.getInstancia().playGameSound();
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
