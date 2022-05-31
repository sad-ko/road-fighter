package road_fighter;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextoStart {

	private final double Y = (Config.height) * 3 / 5; 
	
	private Text text;
	private VBox render;

	public TextoStart() {
		text = new Text("PRESS START");
			
		render = new VBox(text);
		render.setAlignment(Pos.TOP_CENTER);
		render.setTranslateY(Y);

		render.setPrefWidth(Config.width); 
		
		Font font = Font.loadFont(ClassLoader.getSystemResource("font/nintendo-nes-font.ttf").toString(), 30);

		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);
	}

	public Node getRender() {
		return render;
	}
}
