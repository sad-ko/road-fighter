package road_fighter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import test.Test;

public class Main extends Application {

	private Stage stage;
	private Game gameScene;
	private MenuIntro menuIntroScene;
	private MenuOptions menuOptionsScene;

	@Override
	public void start(Stage stage) {
		this.stage = stage;

		menuIntroScene = new MenuIntro(this);
		Scene scene = menuIntroScene.getScene();
		stage.setScene(scene);

		menuIntroScene.load(true);

		stage.getIcons().add(new Image("file:src/main/resources/ico/logo.png"));
		stage.setTitle("Road Fighter");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		Test.test_method();
		launch();
	}

	public void startGame(SceneHandler previousScene, int dificultad) {
		previousScene.unload();
		gameScene = new Game(this, dificultad);
		Scene scene = gameScene.getScene();
		stage.setScene(scene);
		gameScene.load(true);
	}

	public void startMenuIntro(SceneHandler previousScene) {
		previousScene.unload();
		menuIntroScene = new MenuIntro(this);
		Scene scene = menuIntroScene.getScene();
		stage.setScene(scene);
		menuIntroScene.load(true);
	}

	public void startMenuOptions(SceneHandler previousScene) {
		previousScene.unload();
		menuOptionsScene = new MenuOptions(this);
		Scene scene = menuOptionsScene.getScene();
		stage.setScene(scene);
		menuOptionsScene.load(true);
	}
}
