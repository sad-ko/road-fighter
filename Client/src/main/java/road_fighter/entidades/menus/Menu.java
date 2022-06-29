package road_fighter.entidades.menus;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.entidades.Objeto;
import road_fighter.fisica.Vector2D;
import road_fighter.logica.Invocador;

public abstract class Menu extends Objeto {

	protected static final double X = Config.width * 0.25;
	protected static final double Y = Config.height / 2;
	protected static final double FONT_SIZE = Config.height / 16;

	protected Font font;
	protected Text info;

	protected Menu() {
		super(Entidad.TEXT, new Vector2D(0, 0));
		font = Font.loadFont(ClassLoader.getSystemResource("font/monogram.ttf").toString(), FONT_SIZE);
	}

	protected Text info(String msg, double y_offset) {
		Text t = new Text(msg);
		Font auxFont = Font.loadFont(ClassLoader.getSystemResource("font/monogram.ttf").toString(), FONT_SIZE / 2);

		t.setFont(auxFont);
		t.setFill(Color.color(0.4, 0.59, 0.93, 0.75));
		t.setLayoutX(FONT_SIZE / 4);
		t.setLayoutY(Config.height - FONT_SIZE / y_offset);
		return t;
	}

	@Override
	public void update(double delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remover() {
		Invocador.getInstancia().remove(this);
	}

}
