package road_fighter.entidades;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import road_fighter.fisica.Vector2D;

public class FPS extends Objeto {

	private Text text;
	private AtomicInteger currentFPS;

	public FPS(AtomicInteger fps) {
		super(Entidad.TEXT, new Vector2D(410f*2, 10f));
		this.currentFPS = fps;
		text = new Text();

		render = new VBox(text);
		render.setTranslateY(posicion.getY());
		render.setTranslateX(posicion.getX());

		text.setFont(Font.font("MONOSPACED",32));
		text.setFill(Color.WHITE);
	}

	@Override
	public void update(double deltaTime) {
		text.setText("FPS: " + currentFPS);
		render.toFront();
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub

	}

}
