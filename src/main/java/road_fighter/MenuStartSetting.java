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

public class MenuStartSetting extends SceneHandler {

	private Group rootGroup;
	private Group root;
	TextoMenuSetting menu;

	public MenuStartSetting(Main main) {
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
				case ENTER:
					main.startGame();
					break;
				case A:
					AudioSFX.getInstancia().stop("largada_start");
					AudioSFX.getInstancia().bajarVolumenSound();
					AudioSFX.getInstancia().play("largada_start");
					menu.getRenderTextPorcentajeSFX().setVisible(false);
					menu.TextPorcentajeSFX();
					root.getChildren().add(menu.getRenderTextPorcentajeSFX());
					menu.getRenderTextPorcentajeSFX().setVisible(true);
					break;
				case D:
					AudioSFX.getInstancia().stop("largada_start");
					AudioSFX.getInstancia().subirVolumenSound();
					AudioSFX.getInstancia().play("largada_start");
					menu.getRenderTextPorcentajeSFX().setVisible(false);
					menu.TextPorcentajeSFX();
					root.getChildren().add(menu.getRenderTextPorcentajeSFX());
					menu.getRenderTextPorcentajeSFX().setVisible(true);
					break;
				case RIGHT:
					AudioSound.getInstancia().subirVolumenSound();
					menu.getRenderTextPorcentajeAudio().setVisible(false);
					menu.TextPorcentajeAudio();
					root.getChildren().add(menu.getRenderTextPorcentajeAudio());
					menu.getRenderTextPorcentajeAudio().setVisible(true);
					break;
				case LEFT:
					AudioSound.getInstancia().bajarVolumenSound();
					menu.getRenderTextPorcentajeAudio().setVisible(false);
					menu.TextPorcentajeAudio();
					root.getChildren().add(menu.getRenderTextPorcentajeAudio());
					menu.getRenderTextPorcentajeAudio().setVisible(true);
					break;
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
	public void update(double delta) {

	}

	@Override
	protected void load(boolean start) {
		root = new Group();
		rootGroup.getChildren().add(root);
		Invocador.getInstancia().setRoot(root);

		Image fondo = new Image("file:src/main/resources/img/start.png", Config.width, Config.height, false, false);
		ImageView imageView = new ImageView(fondo);
		root.getChildren().add(imageView);

		menu = new TextoMenuSetting();
		root.getChildren().add(menu.getRender());
		root.getChildren().add(menu.getRenderTextAudio());
		root.getChildren().add(menu.getRenderTextSFX());
		root.getChildren().add(menu.getRenderTextPorcentajeAudio());
		root.getChildren().add(menu.getRenderTextPorcentajeSFX());

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
