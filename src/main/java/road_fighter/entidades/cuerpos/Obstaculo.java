package road_fighter.entidades.cuerpos;

import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.Sprite;

public class Obstaculo extends Colisionables {

	private Sprite sprite;

	public Obstaculo(Vector2D posicion) {
		super(Entidad.OBSTACULO, posicion, new Vector2D(155, -50));

		this.sprite = new Sprite("img/obstaculo.png", new Vector2D(80, 28), 2);
		this.sprite.realocate(new Vector2D(0, -28));

		this.render = sprite.getRender();
	}

	@Override
	public void update(double delta) {
		double y = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY(this.posicion.getY() + y);
		Sprite.setRenderPosition((ImageView) this.render, this.posicion);
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
	}

}
