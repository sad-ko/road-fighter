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
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;
import road_fighter.logica.Dificultad;

public class TextoMenuSetting extends Objeto {

	private static final double X = Config.width * 0.25;
	private static final double Y = Config.height * 0.6;

	private Font font;
	private Text[] texts;
	private int focus = 0;

	protected VBox render;

	public TextoMenuSetting(int dificultad) {
		super(Entidad.TEXT, new Vector2D(0, 0));
		font = Font.loadFont(ClassLoader.getSystemResource("font/nintendo-nes-font.ttf").toString(),
				Config.height / 32);
		render = new VBox();
		texts = new Text[3];

		startGame(dificultad);
		audioText();
		textSFX();
	}

	@Override
	public void update(double delta) {
		texts[1].setText("SOUND: " + getPorcentajeAudio());
		texts[2].setText("SFX: " + getPorcentajeSFX());
		texts[focus].setFill(Color.CORAL);
	}

	public void startGame(int dificultad) {
		texts[0] = new Text("START GAME: " + Dificultad.values()[dificultad]);
		texts[0].setTextAlignment(TextAlignment.CENTER);
		texts[0].setFont(font);
		texts[0].setFill(Color.WHITE);

		texts[0].setX(X);
		texts[0].setY(Y);

		VBox renderTitle = new VBox(texts[0]);
		renderTitle.setAlignment(Pos.TOP_CENTER);
		renderTitle.setTranslateY(Y);
		renderTitle.setPrefWidth(Config.width);

		render.getChildren().add(renderTitle);
	}

	public void audioText() {
		texts[1] = new Text("SOUND: " + getPorcentajeAudio());
		texts[1].setTextAlignment(TextAlignment.CENTER);
		texts[1].setFont(font);
		texts[1].setFill(Color.WHITE);

		texts[1].setX(X);
		texts[1].setY(Y + 50);

		VBox renderTextAudio = new VBox(texts[1]);
		renderTextAudio.setAlignment(Pos.TOP_CENTER);
		renderTextAudio.setTranslateY(Y + 50);
		renderTextAudio.setPrefWidth(Config.width);

		render.getChildren().add(renderTextAudio);
	}

	public void textSFX() {
		texts[2] = new Text("SFX: " + getPorcentajeSFX());
		texts[2].setTextAlignment(TextAlignment.CENTER);
		texts[2].setFont(font);
		texts[2].setFill(Color.WHITE);

		texts[2].setX(X);
		texts[2].setY(Y + 100);

		VBox renderTextSFX = new VBox(texts[2]);
		renderTextSFX.setAlignment(Pos.TOP_CENTER);
		renderTextSFX.setTranslateY(Y + 100);
		renderTextSFX.setPrefWidth(Config.width);

		render.getChildren().add(renderTextSFX);
	}

	private int getPorcentajeAudio() {
		return (int) (AudioSound.getInstancia().getVolumen() * 100);
	}

	private int getPorcentajeSFX() {
		return (int) (AudioSFX.getInstancia().getVolumen() * 100);
	}

	public void moveUp() {
		texts[focus].setFill(Color.WHITE);
		focus = (focus == 0) ? texts.length - 1 : focus - 1;
	}

	public void moveDown() {
		texts[focus].setFill(Color.WHITE);
		focus = (focus == texts.length - 1) ? 0 : focus + 1;
	}

	public void setDificultad(int dificultad) {
		texts[0].setText("START GAME: " + Dificultad.values()[dificultad]);
	}

	@Override
	public Node getRender() {
		return render;
	}

	public int getFocus() {
		return focus;
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}

}
