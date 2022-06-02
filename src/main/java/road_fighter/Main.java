package road_fighter;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@SuppressWarnings("unused")
	private Stage stage;
	private MenuStart menuSceneHandler;
	private Game gameSceneHandler;
	private MenuStartSetting menuSettingSceneHandler;

	@Override
	public void start(Stage stage) {
		// menu = new Menu(stage);
		//gameSceneHandler = new Game(stage);
		
		this.stage = stage;

		menuSceneHandler = new MenuStart(this);
		Scene scene = menuSceneHandler.getScene();
		stage.setScene(scene);

		menuSceneHandler.load(true);

		stage.getIcons().add(new Image("file:src/main/resources/ico/logo.png"));
		stage.setTitle("Road Fighter");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}


	public void startGame() {
		menuSettingSceneHandler.unload();//menuSceneHandler.unload();
		gameSceneHandler = new Game(this);
		Scene scene = gameSceneHandler.getScene();
		stage.setScene(scene);
		gameSceneHandler.load(true);
	}
	
	public void startMenu() {
		gameSceneHandler.unload();
		menuSceneHandler = new MenuStart(this);
		Scene scene = menuSceneHandler.getScene();
		stage.setScene(scene);
		menuSceneHandler.load(true);
	}
	
	public void startMenuSetting() {
		menuSceneHandler.unload();
		menuSettingSceneHandler = new MenuStartSetting(this);
		Scene scene = menuSettingSceneHandler.getScene();
		stage.setScene(scene);
		menuSettingSceneHandler.load(true);
	}
}
