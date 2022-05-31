package road_fighter.entidades.cuerpos;

import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;

/**
 * La clase {@code Borde} hija de {@code Cuerpo}, delimita los limites
 * horizontales del mapa.
 */
public final class Borde extends Colisionables {

	/**
	 * @param ancho :{@code float} - Limite del mapa en el eje X.
	 * @param largo :{@code float} - Limite del mapa en el eje Y.
	 */
	public Borde(double posX) {
		super(Entidad.BORDE, new Vector2D(posX, Config.height), new Vector2D(10, -Config.height));
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
	}

}
