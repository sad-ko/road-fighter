package road_fighter.entidades;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import road_fighter.Config;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;

public class TextoMenuSetting {

	public static final double height = Config.height;

	private final double Y = height * 3 / 5;
	
	private VBox render;
	private VBox renderTextAudio;
	private VBox renderTextSFX;
	private VBox renderTextPorcentajeAudio;
	private VBox renderTextPorcentajeSFX;




	private Font font;

	public TextoMenuSetting() {
		font = Font.loadFont(ClassLoader.getSystemResource("font/nintendo-nes-font.ttf").toString(), 30);
		startGame();
		Textaudio();
		TextSFX();
		TextPorcentajeAudio();
		TextPorcentajeSFX();
	}
	
	public void startGame() {
		Text text = new Text("START GAME -ENTER-");

		render = new VBox(text);
		render.setAlignment(Pos.TOP_CENTER);
		render.setTranslateY(Y);
		// Esto debería heredarse?
		render.setPrefWidth(Config.width);

		//Font font = Font.font("Verdana", FontWeight.NORMAL, 40);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);
	}
	
	public void Textaudio() {
		Text text = new Text("SOUND: ");
		
		renderTextAudio = new VBox(text);
		renderTextAudio.setAlignment(Pos.TOP_CENTER);
		renderTextAudio.setTranslateY(Y + 50);
		// Esto debería heredarse?
		renderTextAudio.setPrefWidth(Config.width);

		//Font font = Font.font("Verdana", FontWeight.NORMAL, 40);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);
	}
	
	public void TextSFX() {
		Text text = new Text("SFX: ");

		renderTextSFX = new VBox(text);
		renderTextSFX.setAlignment(Pos.TOP_CENTER);
		renderTextSFX.setTranslateY(Y + 100);
		// Esto debería heredarse?
		renderTextSFX.setPrefWidth(Config.width);

		//Font font = Font.font("Verdana", FontWeight.NORMAL, 40);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);
	}
	
	public String getPorcentajeAudio() {
		String porcentaje = String.valueOf((int) (AudioSound.getInstancia().getVolumen()*100));
		return porcentaje;
	}
	
	public String getPorcentajeSFX() {
		String porcentaje = String.valueOf((int) (AudioSFX.getInstancia().getVolumen()*100));
		return porcentaje;
	}
	
	public void TextPorcentajeAudio() {
		Text text = new Text(getPorcentajeAudio());

		renderTextPorcentajeAudio = new VBox(text);
		renderTextPorcentajeAudio.setAlignment(Pos.TOP_CENTER);
		renderTextPorcentajeAudio.setTranslateY(Y + 50);
		renderTextPorcentajeAudio.setTranslateX(renderTextPorcentajeAudio.getTranslateX() +150);
		// Esto debería heredarse?
		renderTextPorcentajeAudio.setPrefWidth(Config.width);

		//Font font = Font.font("Verdana", FontWeight.NORMAL, 40);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);
	}
	
	public void TextPorcentajeSFX() {
		Text text = new Text(getPorcentajeSFX());

		renderTextPorcentajeSFX = new VBox(text);
		renderTextPorcentajeSFX.setAlignment(Pos.TOP_CENTER);
		renderTextPorcentajeSFX.setTranslateY(Y + 100);
		renderTextPorcentajeSFX.setTranslateX(renderTextPorcentajeSFX.getTranslateX() +150);
		// Esto debería heredarse?
		renderTextPorcentajeSFX.setPrefWidth(Config.width);

		//Font font = Font.font("Verdana", FontWeight.NORMAL, 40);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(font);
		text.setFill(Color.WHITE);
	}
	
	public Node getRenderTextPorcentajeSFX() {
		return renderTextPorcentajeSFX;
	}

	public Node getRenderTextPorcentajeAudio() {
		return renderTextPorcentajeAudio;
	}
	
	public Node getRender() {
		return render;
	}
	
	public Node getRenderTextAudio() {
		return renderTextAudio;
	}
	
	public Node getRenderTextSFX() {
		return renderTextSFX;
	}
}
