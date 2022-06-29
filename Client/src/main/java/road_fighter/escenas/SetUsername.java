package road_fighter.escenas;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import road_fighter.Client;
import road_fighter.Config;
import road_fighter.Main;
import road_fighter.entidades.menus.SetUsernameMenu;
import road_fighter.logica.Invocador;

public class SetUsername extends SceneHandler {

	private Group root;
	private SetUsernameMenu menu;

	public SetUsername(Main main) {
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
				case Q:
				case X:
				case ESCAPE:
					main.changeScene(me, Escenas.MENU_OPTIONS);
					break;

				case ENTER:
				case Z:
					root.requestFocus();
					if (main.client == null) {
						try {
							main.client = new Client("localhost", 20000, menu.getUsername(), me);
							main.client.listen();
						} catch (IOException io) {
							io.printStackTrace();
						}
					}
					main.changeScene(me, Escenas.LOBBY);
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
		menu = new SetUsernameMenu();
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
