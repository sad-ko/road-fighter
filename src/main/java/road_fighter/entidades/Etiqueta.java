package road_fighter.entidades;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import road_fighter.fisica.Vector2D;

public class Etiqueta extends Objeto {

	private Text text;
	private String contenido;

	public Etiqueta(String contenido, Vector2D posicion) {
		super(Entidad.TEXT, posicion);
		text = new Text();
		this.contenido = contenido;

		render = new VBox(text);
		render.setTranslateY(posicion.getY());
		render.setTranslateX(posicion.getX());

		text.setFont(Font.font("MONOSPACED", 32));
		text.setFill(Color.WHITE);
	}

	protected void setText(String contenido) {
		this.contenido = contenido;
		text.setText(contenido);
	}

	@Override
	public void update(double delta) {
		this.setText(contenido);
		render.toFront();
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
	}

	public void setVisible(boolean value) {
		this.render.setVisible(value);
	}

	public void setColor(Paint value) {
		this.text.setFill(value);
	}

	public void setFont(Font value) {
		this.text.setFont(value);
	}

}
