package road_fighter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import road_fighter.entidades.TextoMenuSetting;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Invocador;

public class MenuOptions extends SceneHandler {

	private Group root;
	private TextoMenuSetting menu;
	private int dificultad = 0;

	public MenuOptions(Main main) {
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

				case Z:
				case ENTER:
					if (menu.getFocus() == 0) {
						main.startGame(me, dificultad);
					}
					break;

				case RIGHT:
					switch (menu.getFocus()) {
					case 0:
						dificultad = (dificultad == 2) ? 2 : dificultad + 1;
						menu.setDificultad(dificultad);
						break;

					case 1:
						AudioSound.getInstancia().subirVolumenSound();
						break;

					case 2:
						AudioSFX.getInstancia().subirVolumenSound();
						AudioSFX.getInstancia().play("largada_start", true);
						break;
					}
					break;

				case LEFT:
					switch (menu.getFocus()) {
					case 0:
						dificultad = (dificultad == 0) ? 0 : dificultad - 1;
						menu.setDificultad(dificultad);
						break;

					case 1:
						AudioSound.getInstancia().bajarVolumenSound();
						break;

					case 2:
						AudioSFX.getInstancia().bajarVolumenSound();
						AudioSFX.getInstancia().play("largada_start", true);
						break;
					}
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
	}

	@Override
	protected void load(boolean start) {
		Invocador.getInstancia().setRoot(root);

		Image fondo = new Image("file:src/main/resources/img/start.png", Config.width, Config.height, false, false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);

		menu = new TextoMenuSetting(this.dificultad);
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
