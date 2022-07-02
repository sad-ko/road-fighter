package road_fighter.entidades.cuerpos;

import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.Sprite;

public class Obstaculo extends Colisionables {

	private static final int SIZE = (int) (Config.width * 0.002);
	private Sprite sprite;

	public Obstaculo(Vector2D posicion, boolean renderizable, long cuerpo_id) {
		super(Entidad.OBSTACULO, posicion, new Vector2D(63 * SIZE, -26 * SIZE), cuerpo_id);

		if (renderizable) {
			this.sprite = new Sprite("img/obstaculo.png", new Vector2D(64, 27), SIZE);
			this.sprite.realocate(new Vector2D(0, -27));

			this.render = sprite.getRender();
		}
	}

	public Obstaculo(Vector2D posicion, long cuerpo_id) {
		this(posicion, true, cuerpo_id);
	}

	@Override
	public void update(double delta) {
		double y = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY(this.posicion.getY() + y);

		if (this.render != null) {
			Sprite.setRenderPosition((ImageView) this.render, this.posicion);
		}
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
	}

	public static int getAncho() {
		return SIZE * 64;
	}

}
