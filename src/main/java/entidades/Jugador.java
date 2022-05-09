package entidades;

import fisica.Vector2D;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
public final class Jugador extends Auto {

	/**
	 * Velocidad maxima del {@code Jugador}, actualmente es 200.0f
	 */
	private final float velocidadMax = 200.0f;
	private String nombre;


	/**
	 * @param posicion     :{@code Vector2D} - Posicion del {@code Jugador} en el
	 *                     plano {x,y}.
	 * @param hitboxTamanio :{@code Vector2D} - Tamaño de la {@code Colision}
	 */
	public Jugador(Vector2D posicion, Vector2D hitboxTamanio, String nombreJugador) {
		super("Jugador", posicion, hitboxTamanio);
		super.setVelocidad(1);
		this.nombre=nombreJugador;
	}

	/**
	 * Permite girar al {@code Jugador} en el sentido indicado del eje X, el
	 * movimiento es una sumatoria de velocidad.
	 * 
	 * @param derecha :boolean - Indica si el {@code Jugador} debe girar a la
	 *                derecha o a la izquierda
	 */
	public void girar(boolean derecha, float delta) {
		this.posicion.x += derecha ? this.velocidad : (this.velocidad * -1);
		this.posicion.x *= delta;
	}

	/**
	 * Permite acelerar y mantener la aceleracion del {@code Jugador}, el movimiento
	 * es una sumatoria de velocidad y siempre va hacia arriba en el eje Y.
	 */
	public void acelerar(float delta) {
		this.posicion.y += this.velocidad * delta;

		if (this.velocidad < this.velocidadMax) {
			this.velocidad++;
		}
	}

	/**
	 * Permite desacelerar al {@code Jugador}, el movimiento es una resta de
	 * velocidad hasta llegar a 0.0f y siempre va a ser hacia arriba en el eje Y.
	 */
	public void desacelerar(float delta) {
		this.posicion.y += this.velocidad * delta;

		if (this.velocidad > 0.0f) {
			this.velocidad--;
		}
	}

	@Override
	public String toString() {
		int pad = this.posicion.toString().length();
		return String.format("Jugador "+this.nombre+": %" + (-pad) + "s | velocidad: %.2f", this.posicion, this.velocidad);
	}

	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

}
