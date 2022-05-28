package road_fighter.entidades;

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
	public Borde(double ancho, double largo) {
		super(Entidad.BORDE, new Vector2D(ancho, largo), new Vector2D(10f, -largo));
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
