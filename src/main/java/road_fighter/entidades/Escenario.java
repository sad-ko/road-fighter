package road_fighter.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.fisica.Vector2D;

public class Escenario extends Objeto {

	public Escenario(double longitud) {
		super(Entidad.ESCENARIO, new Vector2D(0, -longitud + Config.height));
		Image bg01 = new Image("file:src/main/resources/img/background.png", Config.width, Config.height * 2, false,
				false);
		ImageView b1 = new ImageView(bg01);
		this.render = b1;
		this.render.setViewOrder(100);
	}

	@Override
	public void update(double delta) {
		if (Config.currentVelocity > 0.0) {
			posicion.setY(posicion.getY() + (Config.currentVelocity * delta / Config.acceleration));

			if (posicion.getY() < Config.height) {
				render.setTranslateY(posicion.getY() % Config.height);
			}
		}
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
	}

}
