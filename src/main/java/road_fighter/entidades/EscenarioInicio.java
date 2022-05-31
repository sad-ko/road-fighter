package road_fighter.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.fisica.Vector2D;

public class EscenarioInicio extends Objeto {

	public EscenarioInicio() {
		super(Entidad.ESCENARIO, new Vector2D(0, 0));
		Image bg01 = new Image("file:src/main/resources/img/background_start.png", Config.width, Config.height, false,
				false);
		ImageView b1 = new ImageView(bg01);
		this.render = b1;
		this.render.setViewOrder(99);
	}

	@Override
	public void update(double delta) {
		if (Config.currentVelocity > 0.0) {
			posicion.setY(posicion.getY() + (Config.currentVelocity * delta / Config.acceleration));
			render.setTranslateY(posicion.getY());
		}
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub

	}

}
