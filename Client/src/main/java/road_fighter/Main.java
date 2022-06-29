package road_fighter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import road_fighter.escenas.CreateSala;
import road_fighter.escenas.Escenas;
import road_fighter.escenas.Game;
import road_fighter.escenas.Lobby;
import road_fighter.escenas.MenuIntro;
import road_fighter.escenas.MenuOptions;
import road_fighter.escenas.SalaActual;
import road_fighter.escenas.SceneHandler;
import road_fighter.escenas.SetUsername;

public class Main extends Application {

	private Stage stage;
	public Client client;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		changeScene(null, Escenas.MENU_INTRO);
		stage.getIcons().add(new Image("file:src/main/resources/ico/logo.png"));
		stage.setTitle("Road Fighter");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

	public void exit() {
		if (client != null) {
			client.close();
		}
		System.exit(0);
	}

	public void changeScene(SceneHandler previousScene, Escenas nextScene) {
		if (previousScene != null) {
			previousScene.unload();
		}

		SceneHandler newScene = null;

		switch (nextScene) {
		case MENU_INTRO:
			newScene = new MenuIntro(this);
			break;

		case MENU_OPTIONS:
			newScene = new MenuOptions(this);
			break;

		case SET_USERNAME:
			newScene = new SetUsername(this);
			break;

		case LOBBY:
			newScene = new Lobby(this);
			break;

		case CREATE_SALA:
			newScene = new CreateSala(this);
			break;

		case SALA:
			newScene = new SalaActual(this);
			break;

		case GAME:
			newScene = new Game(this);
			break;

		default:
			break;
		}

		if (newScene != null) {
			Scene scene = newScene.getScene();
			scene.getStylesheets().add(ClassLoader.getSystemResource("stylesheet.css").toString());
			this.stage.setScene(scene);
			newScene.load(true);
		}
	}

}
