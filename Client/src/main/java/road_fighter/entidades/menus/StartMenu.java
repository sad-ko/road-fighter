package road_fighter.entidades.menus;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import road_fighter.Config;

public class StartMenu extends Menu {

	private Text text;

	public StartMenu() {
		text = new Text("PRESS START");

		VBox box = new VBox(text);
		box.setAlignment(Pos.TOP_CENTER);
		box.setTranslateY(Y);
		box.setPrefWidth(Config.width);

		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);

		this.render = box;
	}
}
