package road_fighter.entidades;

import java.util.concurrent.atomic.AtomicInteger;
import road_fighter.fisica.Vector2D;

public class FPS extends Etiqueta {

	private AtomicInteger currentFPS;

	public FPS(AtomicInteger fps, Vector2D posicion) {
		super("FPS: " + fps, posicion);
		this.currentFPS = fps;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		this.setText("FPS: " + currentFPS);
	}

}
