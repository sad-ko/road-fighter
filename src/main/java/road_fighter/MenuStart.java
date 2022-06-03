package road_fighter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import road_fighter.entidades.TextoStart;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Invocador;

public class MenuStart extends SceneHandler {

	private Group rootGroup;

	public MenuStart(Main main) {
		super(main);
		// stage.setScene(this.scene);
		// load();
		// addTimeEventsAnimationTimer();
		// addInputEvents();
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
					// main.startGame();
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
	protected void load(boolean start) {
		Group root = new Group();
		rootGroup.getChildren().add(root);
		Invocador.getInstancia().setRoot(root);

		Image fondo = new Image("file:src/main/resources/img/start.png", Config.width, Config.height, false, false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);

		TextoStart menu = new TextoStart();
		root.getChildren().add(menu.getRender());

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
