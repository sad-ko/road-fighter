package road_fighter.entidades.cuerpos;

import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
public final class Jugador extends Competidor {

	/**
	 * Velocidad maxima del {@code Jugador}, actualmente es 200.0f
	 */
	private double velocidadMax = 200.0;
	private double aceleracion = 5;

	private double desplazamiento = 5;

	private boolean z = false;
	private boolean right = false;
	private boolean left = false;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del {@code Jugador} en el plano
	 *                 (x,y).
	 * @param nombre   :{@code String} - Nombre del jugador.
	 */
	public Jugador(Vector2D posicion, String nombre) {
		super(posicion, nombre);
		this.nombre = nombre;
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
	public void desplazar() {
		if (!right && !left) {
			return;
		}

		double sentido = right ? desplazamiento : (desplazamiento * -1);
		this.posicion.setX(this.posicion.getX() + sentido);
	}

	/**
	 * Permite acelerar y mantener la aceleracion del {@code Jugador}, el movimiento
	 * es una sumatoria de velocidad y siempre va hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	public void acelerar() {
		if (!z) {
			return;
		}

		if (this.velocidad < this.velocidadMax) {
			this.velocidad += this.aceleracion;
		}
	}

	/**
	 * Permite desacelerar al {@code Jugador}, el movimiento es una resta de
	 * velocidad hasta llegar a 0.0f y siempre va a ser hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	public void desacelerar(double desaceleracion) {
		if (z) {
			return;
		}

		if (this.velocidad > 0.0) {
			this.velocidad -= (this.aceleracion * desaceleracion);
		} else {
			this.velocidad = 0.00;
		}
	}

	@Override
	protected void mover(double delta) {
		if (choque || exploto) {
			z = false;
			desacelerar(0.5);
			AudioSFX.getInstancia().play(choque ? "derrape" : "explosion");
			return;
		}

		if (z) {
			acelerar();
			AudioSFX.getInstancia().play("running");
		} else {
			desacelerar(this.velocidad);
			AudioSFX.getInstancia().stop("running");
		}

		if (right || left) {
			desplazar();
		}
	}

	public void setZ(boolean z) {
		this.z = z;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public double getVelocidadMax() {
		return velocidadMax;
	}

	public void setAceleracion(double aceleracion) {
		this.aceleracion = aceleracion;
	}

}
