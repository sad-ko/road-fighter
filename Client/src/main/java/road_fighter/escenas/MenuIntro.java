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
import road_fighter.entidades.menus.StartMenu;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Invocador;

public class MenuIntro extends SceneHandler {

	private Group root;

	public MenuIntro(Main main) {
		super(main);
	}

	@Override
	protected void prepareScene() {
		root = new Group();
		this.scene = new Scene(root, Config.width, Config.height, Color.BLACK);
	}

	@Override
	protected void defineEventHandlers() {
		keyEventHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case SPACE:
				case ENTER:
				case Z:
					changeScene(Escenas.MENU_OPTIONS);
					break;

				case Q:
				case X:
				case ESCAPE:
					main.exit();
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

		Image fondo = new Image("file:src/main/resources/img/start.png", Config.width, Config.height / 2, false, false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);

		StartMenu menu = new StartMenu();
		root.getChildren().add(menu.getRender());

		AudioSound.getInstancia().playMenuSound();

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
