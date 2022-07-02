package road_fighter.entidades.cuerpos;

import road_fighter.Config;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;
import road_fighter.graficos.AudioSound;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
public final class Jugador extends Competidor {

	private boolean z = false;
	private boolean right = false;
	private boolean left = false;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del {@code Jugador} en el plano
	 *                 (x,y).
	 * @param nombre   :{@code String} - Nombre del jugador.
	 */
	public Jugador(Vector2D posicion, String nombre, int id) {
		super(posicion, nombre, id);
		this.nombre = nombre;
	}

	/**
	 * Permite acelerar y mantener la aceleracion del {@code Jugador}, el movimiento
	 * es una sumatoria de velocidad y siempre va hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	@Override
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

		if (this.velocidad > desaceleracion) {
			this.velocidad = this.velocidad - this.aceleracion * desaceleracion;
		} else {
			this.velocidad = 0.0;
		}
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

		double sentido = right ? aceleracion : -aceleracion;
		this.posicion.setX(this.posicion.getX() + sentido);
	}

	@Override
	protected void mover(double delta) {
		if (choque || exploto) {
			desacelerar(0.5);
			AudioSFX.getInstancia().play(choque ? "derrape" : "explosion");
			return;
		}

		if (z) {
			acelerar();
			AudioSFX.getInstancia().play("running");
		} else if (Config.currentVelocity > 0.0) {
			desacelerar(this.aceleracion);
			AudioSFX.getInstancia().stop("running");
		}

		if (right || left) {
			desplazar();
		}
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {
		super.enChoque(cuerpo);

		switch (cuerpo.getClase()) {

		case META:
			AudioSound.getInstancia().playGanadorSound();
			break;

		case POWERUP:
			AudioSFX.getInstancia().play("powerUp");
			break;

		default:
			break;
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

	@Override
	public void setVelocidad(double velocidad) {
		this.velocidadMax *= velocidad;
		this.velocidad *= velocidad;
	}

	@Override
	public void unsetVelocidad(double velocidad) {
		this.velocidadMax /= velocidad;
		this.velocidad /= velocidad;
	}

}
