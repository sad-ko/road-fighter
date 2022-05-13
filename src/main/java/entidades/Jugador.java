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
	private float velocidadMax = 200.0f;
	private float aceleracion = 1f;
	private String nombre;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del {@code Jugador} en el plano
	 *                 (x,y).
	 * @param nombre   :{@code String} - Nombre del jugador.
	 */
	public Jugador(Vector2D posicion, String nombre) {
		super("Jugador", posicion);
		this.nombre = nombre;
		this.velocidad = 1f;
	}

	/**
	 * Permite girar al {@code Jugador} en el sentido indicado del eje X, el
	 * movimiento es una sumatoria de velocidad.
	 * 
	 * @param derecha :boolean - Indica si el {@code Jugador} debe girar a la
	 *                derecha o a la izquierda
	 * @param delta   :float - Lapso de tiempo en segundos, desde el anterior frame
	 *                a este
	 */
	public void desplazar(boolean derecha, float delta) {
		float sentido = derecha ? this.velocidad : (this.velocidad * -1);
		this.posicion.setX(this.posicion.getX() + sentido * delta);
	}

	/**
	 * Permite acelerar y mantener la aceleracion del {@code Jugador}, el movimiento
	 * es una sumatoria de velocidad y siempre va hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	public void acelerar(float delta) {
		if (this.velocidad < this.velocidadMax) {
			this.velocidad += this.aceleracion;
		}
		this.posicion.setY(this.posicion.getY() + this.velocidad * delta);
	}

	/**
	 * Permite desacelerar al {@code Jugador}, el movimiento es una resta de
	 * velocidad hasta llegar a 0.0f y siempre va a ser hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	public void desacelerar(float delta) {
		if (this.velocidad > 0) {
			this.velocidad -= this.aceleracion;
		}
		this.posicion.setY(this.posicion.getY() + this.velocidad * delta);
	}

	public String getNombre() {
		return this.nombre;
	}

	public float getVelocidadMax() {
		return velocidadMax;
	}

}
