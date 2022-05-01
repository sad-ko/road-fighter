package entidades;

import fisica.Vector2D;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
final public class Jugador extends Cuerpo {

	/**
	 * Velocidad inicial del {@code Jugador}, comienza en 0.0f
	 */
	private float velocidad = 0.0f;

	/**
	 * Velocidad maxima del {@code Jugador}, actualmente es 200.0f
	 */
	final private float velocidad_max = 200.0f;

	/**
	 * @param posicion     :{@code Vector2D} - Posicion del {@code Jugador} en el
	 *                     plano {x,y}.
	 * @param hitboxTamaño :{@code Vector2D} - Tamaño de la {@code Colision}
	 */
	public Jugador(Vector2D posicion, Vector2D hitboxTamaño) {
		super("Jugador", posicion, hitboxTamaño);
	}

	/**
	 * Permite girar al {@code Jugador} en el sentido indicado del eje X, el
	 * movimiento es una sumatoria de velocidad.
	 * 
	 * @param derecha :boolean - Indica si el {@code Jugador} debe girar a la
	 *                derecha o a la izquierda
	 */
	public void girar(boolean derecha) {
		this.posicion.x += derecha ? this.velocidad : (this.velocidad * -1);
	}

	/**
	 * Permite acelerar y mantener la aceleracion del {@code Jugador}, el movimiento
	 * es una sumatoria de velocidad y siempre va hacia arriba en el eje Y.
	 */
	public void acelerar() {
		this.posicion.y += this.velocidad;

		if (this.velocidad < this.velocidad_max) {
			this.velocidad++;
		}
	}

	/**
	 * Permite desacelerar al {@code Jugador}, el movimiento es una resta de
	 * velocidad hasta llegar a 0.0f y siempre va a ser hacia arriba en el eje Y.
	 */
	public void desacelerar() {
		this.posicion.y += this.velocidad;

		if (this.velocidad > 0.0f) {
			this.velocidad--;
		}
	}

	@Override
	public String toString() {
		int pad = this.posicion.toString().length();
		return String.format("Jugador: %" + (-pad) + "s | %.2f", this.posicion, this.velocidad);
	}

	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}
}
