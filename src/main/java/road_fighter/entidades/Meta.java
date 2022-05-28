package road_fighter.entidades;

import road_fighter.fisica.Vector2D;
import road_fighter.logica.Partida;

/**
 * La clase {@code Meta} es una clase que hereda de {@code Cuerpo} y es el que
 * define cuando se llega al final del mapa.
 */
public class Meta extends Colisionables {

	private final Partida partidaActual;

	/**
	 * @param meta          :{@code float} - Posicion de la meta en el eje Y.
	 * @param ancho         :{@code float} - Ancho de la meta en el eje X.
	 * @param partidaActual :{@code Partida} - Partida actual donde Meta esta siendo
	 *                      instanciada.
	 */
	public Meta(double meta, double ancho, Partida partidaActual) {
		super(Entidad.META, new Vector2D(0f, 0f), new Vector2D(ancho, 2f));
		this.partidaActual = partidaActual;
	}

	public Partida getPartidaActual() {
		return this.partidaActual;
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
