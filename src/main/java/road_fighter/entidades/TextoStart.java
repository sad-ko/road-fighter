package road_fighter.entidades;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import road_fighter.Config;
import road_fighter.fisica.Vector2D;

public class TextoStart extends Objeto {

	private final double Y = (Config.height) * 3 / 5;
	private Text text;

	public TextoStart() {
		super(Entidad.TEXT, new Vector2D(0, 0));
		text = new Text("PRESS START");

		VBox box = new VBox(text);
		box.setAlignment(Pos.TOP_CENTER);
		box.setTranslateY(Y);
		box.setPrefWidth(Config.width);

		Font font = Font.loadFont(ClassLoader.getSystemResource("font/nintendo-nes-font.ttf").toString(), 30);

		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);

		this.render = box;
	}

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub

	}
}
