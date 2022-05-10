package entidades;

import fisica.Vector2D;

/**
 * La clase {@code Borde} hija de {@code Cuerpo}, delimita los limites
 * horizontales del mapa.
 */
public final class Borde extends Cuerpo {

	/**
	 * @param ancho :{@code float} - Limite del mapa en el eje X.
	 * @param largo :{@code float} - Limite del mapa en el eje Y.
	 */
	public Borde(float ancho, float largo) {
		super("Borde", new Vector2D(ancho, 0f), new Vector2D(1f, largo));
	}

}
