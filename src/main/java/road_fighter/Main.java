package road_fighter;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@SuppressWarnings("unused")
	private Game game;

	@Override
	public void start(Stage stage) {
		game = new Game(stage);

		stage.getIcons().add(new Image("file:src/main/resources/ico/logo.png"));
		stage.setTitle("Road Fighter");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
