package road_fighter.escenas;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import road_fighter.Config;
import road_fighter.Main;
import road_fighter.entidades.menus.SalaMenu;
import road_fighter.logica.Invocador;
import road_fighter.networking.Comando;
import road_fighter.networking.Mensaje;

public class SalaActual extends SceneHandler {

	private Group root;
	private SalaMenu menu;

	public SalaActual(Main main) {
		super(main);
	}

	@Override
	protected void prepareScene() {
		root = new Group();
		this.scene = new Scene(root, Config.width, Config.height, Color.BLACK);
		main.client.setCurrentScene(this);
	}

	@Override
	protected void defineEventHandlers() {
		keyEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case Q:
				case X:
				case ESCAPE:
					Mensaje msg = new Mensaje(Comando.AL_LOBBY);
					main.client.enviar(msg);
					break;

				case UP:
					menu.moveUp();
					break;

				case DOWN:
					menu.moveDown();
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

		switch (main.client.comandoPendiente) {
		case HACER_NADA:
			break;

		case UNIRSE_SALA:
			restart();
			main.client.comandoPendiente = Comando.HACER_NADA;
			break;

		case AL_LOBBY:
			changeScene(Escenas.LOBBY);
			main.client.comandoPendiente = Comando.HACER_NADA;
			break;

		default:
			break;
		}

	}

	public void restart() {
		Invocador.getInstancia().clear();
		load(false);
	}

	@Override
	public void load(boolean start) {
		Invocador.getInstancia().setRoot(root);

		menu = new SalaMenu(main.client);
		Invocador.getInstancia().add(menu);

		if (start) {
			addTimeEventsAnimationTimer();
			addInputEvents();
		}
	}

	@Override
	public void unload() {
		root.getChildren().clear();
		super.unload();
	}

}
