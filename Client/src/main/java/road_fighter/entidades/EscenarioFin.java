package road_fighter.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.fisica.Vector2D;

public class EscenarioFin extends Objeto {

	public EscenarioFin(double longitud) {
		super(Entidad.ESCENARIO, new Vector2D(0, -longitud + Config.height));
		Image bg01 = new Image("file:src/main/resources/img/background_finish.png", Config.width, Config.height, false,
				false);
		ImageView b1 = new ImageView(bg01);
		b1.relocate(0, -Config.height);
		this.render = b1;
		this.render.setViewOrder(101);
	}

	@Override
	public void update(double delta) {
		if (posicion.getY() < Config.height && Config.currentVelocity > 0.0) {
			posicion.setY(posicion.getY() + (Config.currentVelocity * delta / Config.acceleration));
			render.setTranslateY(posicion.getY());
		}
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub

	}

}
