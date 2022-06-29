package road_fighter.escenas;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import road_fighter.Config;
import road_fighter.Main;
import road_fighter.entidades.menus.LobbyMenu;
import road_fighter.logica.Invocador;
import road_fighter.networking.Comando;

public class Lobby extends SceneHandler {

	private Group root;
	private LobbyMenu menu;

	public Lobby(Main main) {
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
				case UP:
					menu.moveUp();
					break;

				case DOWN:
					menu.moveDown();
					break;

				case Q:
				case X:
				case ESCAPE:
					changeScene(Escenas.MENU_OPTIONS);
					break;

				case Z:
					main.client.enviar(menu.unirseSala());
					break;

				case C:
					changeScene(Escenas.CREATE_SALA);
					break;

				case D:
					if (menu.isOwner()) {
						main.client.enviar(menu.eliminarSala());
						restart();
					}
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
			changeScene(Escenas.SALA);
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

		menu = new LobbyMenu(main.client);
		Invocador.getInstancia().add(menu);

		Image fondo = new Image("file:src/main/resources/img/start.png", Config.width, Config.height / 2, false, false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);

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
