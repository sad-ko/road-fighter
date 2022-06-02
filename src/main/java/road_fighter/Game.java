package road_fighter;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import road_fighter.entidades.Entidad;
import road_fighter.entidades.FPS;
import road_fighter.entidades.VelocidadInfo;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;

public class Game extends SceneHandler {

	private Jugador jugador;
	private Partida partida;

	public Game(Main main) {
		super(main);
		//stage.setScene(this.scene);
		//load();
		//addTimeEventsAnimationTimer();
		//addInputEvents();
	}

	@Override
	protected void prepareScene() {
		Group root = new Group();
		this.scene = new Scene(root, Config.width, Config.height, Color.BLACK);
	}

	@Override
	protected void defineEventHandlers() {
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
		Group root = new Group();
		scene.setRoot(root);

		FPS fpsInfo = new FPS(fps, new Vector2D(800, Config.height - 50));
		VelocidadInfo velocidadInfo = new VelocidadInfo(new Vector2D(800, Config.height - 100));

		Mapa mapa = new Mapa(Config.height * 6, 224, 616);
		mapa.agregarObstaculos(Entidad.AUTO_ESTATICO, 100);

		this.partida = new Partida(mapa, 2000L);
		this.jugador = this.partida.comenzar(7);

		Invocador.getInstancia().add(fpsInfo);
		Invocador.getInstancia().add(velocidadInfo);
		Invocador.getInstancia().addToGroup(root.getChildren());

		AudioSound.getInstancia().stopSound();
		AudioSound.getInstancia().playGameStartSound();
		//jugador.setDesplazamiento(0);
		jugador.setAceleracion(0);
		
		TimerTask task2 = new TimerTask() {
			public void run() {
				AudioSFX.getInstancia().play("largada_start");
				AudioSound.getInstancia().playGameSound();				
				jugador.setAceleracion(5);
				TimerTask task = new TimerTask() {
					public void run() {
						
						partida.getCompetidor(6).setVelocidad(200);
					}
				};

				Timer timer = new Timer();
				timer.schedule(task, 500L);
		
			}
		};

		Timer timer2 = new Timer();
		timer2.schedule(task2, 4000L);
		
		//jugador.setDesplazamiento(5);
		
		if (start) {
			addTimeEventsAnimationTimer();
			addInputEvents();
		}
		
		AudioSFX.getInstancia().subirVolumenSound();
		AudioSound.getInstancia().bajarVolumenSound();

		
	}

}
