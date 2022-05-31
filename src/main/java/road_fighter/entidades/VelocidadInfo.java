package road_fighter.entidades;

import road_fighter.Config;
import road_fighter.fisica.Vector2D;

public class VelocidadInfo extends Etiqueta {

	public VelocidadInfo(Vector2D posicion) {
		super(String.format("%.2f km/s", Config.currentVelocity), posicion);
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		this.setText(String.format("%.2f km/s", Config.currentVelocity));
	}

}
