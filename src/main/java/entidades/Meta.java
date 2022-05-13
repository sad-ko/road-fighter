package entidades;

import fisica.Vector2D;
import logica.Partida;

/**
 * La clase {@code Meta} es una clase que hereda de {@code Cuerpo} y es el que
 * define cuando se llega al final del mapa.
 */
public class Meta extends Cuerpo {

	private final Partida partidaActual;

	/**
	 * @param meta          :{@code float} - Posicion de la meta en el eje Y.
	 * @param ancho         :{@code float} - Ancho de la meta en el eje X.
	 * @param partidaActual :{@code Partida} - Partida actual donde Meta esta siendo
	 *                      instanciada.
	 */
	public Meta(float meta, float ancho, Partida partidaActual) {
		super("Meta", new Vector2D(0f, meta), new Vector2D(ancho, 2f));
		this.partidaActual = partidaActual;
	}

	public Partida getPartidaActual() {
		return this.partidaActual;
	}
}
