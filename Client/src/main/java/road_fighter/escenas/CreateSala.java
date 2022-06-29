package road_fighter.escenas;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import road_fighter.Config;
import road_fighter.Main;
import road_fighter.entidades.menus.CreateSalaMenu;
import road_fighter.logica.Invocador;

public class CreateSala extends SceneHandler {

	private Group root;
	private CreateSalaMenu menu;

	public CreateSala(Main main) {
		super(main);
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
				case UP:
					menu.moveUp();
					break;

				case DOWN:
					menu.moveDown();
					break;

				case Q:
				case ESCAPE:
					main.changeScene(me, Escenas.LOBBY);
					break;

				case RIGHT:
					menu.moveRight();
					break;

				case LEFT:
					menu.moveLeft();
					break;

				case Z:
					if (menu.inConfirmar()) {
						main.client.enviar(menu.crearSala());
						main.changeScene(me, Escenas.LOBBY);
					}
					break;

				default:
					break;
				}
			}
		};
	}

	@Override
	public void load(boolean start) {
		Invocador.getInstancia().setRoot(root);
		menu = new CreateSalaMenu();
		Invocador.getInstancia().add(menu);
		menu.inputFocus();
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
