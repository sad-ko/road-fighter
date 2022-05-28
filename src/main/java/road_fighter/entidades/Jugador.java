package road_fighter.entidades;

import road_fighter.fisica.Vector2D;
import road_fighter.logica.Partida;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
public final class Jugador extends Auto {

	/**
	 * Velocidad maxima del {@code Jugador}, actualmente es 200.0f
	 */
	private double velocidadMax = 200.0;
	private double aceleracion = 0.01;
	private String nombre;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del {@code Jugador} en el plano
	 *                 (x,y).
	 * @param nombre   :{@code String} - Nombre del jugador.
	 */
	public Jugador(Vector2D posicion, String nombre) {
		super(Entidad.JUGADOR, posicion, "img/auto.png", new Vector2D(11, 16));
		this.nombre = nombre;
		this.velocidad = 1;
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
	public void desplazar(boolean derecha, double delta) {
		double sentido = derecha ? 2 : (2 * -1);
		this.posicion.setX(this.posicion.getX() + sentido * delta);
	}

	/**
	 * Permite acelerar y mantener la aceleracion del {@code Jugador}, el movimiento
	 * es una sumatoria de velocidad y siempre va hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	public void acelerar(double delta) {
		if (this.velocidad < this.velocidadMax) {
			this.velocidad += this.aceleracion;
		}
		this.posicion.setY(this.posicion.getY() - this.velocidad * delta);
	}

	/**
	 * Permite desacelerar al {@code Jugador}, el movimiento es una resta de
	 * velocidad hasta llegar a 0.0f y siempre va a ser hacia arriba en el eje Y.
	 * 
	 * @param delta :float - Lapso de tiempo en segundos, desde el anterior frame a
	 *              este
	 */
	public void desacelerar(double delta) {
		if (this.velocidad > 0.0) {
			this.velocidad -= this.aceleracion;
		}
		this.posicion.setY(this.posicion.getY() - this.velocidad * delta);
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {
		switch (cuerpo.getClase()) {
		case AUTO_ESTATICO:
			this.impacto((Auto) cuerpo);
			break;

		case BORDE:
			this.explotar();
			break;

		case JUGADOR:
			this.impacto((Auto) cuerpo);
			break;

		case META:
			Meta meta = (Meta) cuerpo;
			Partida partidaActual = meta.getPartidaActual();

			if (partidaActual.getGanador() == null) {
				partidaActual.setGanador(this);
				partidaActual.iniciarEspera();
			}
			break;

		case OBSTACULO:
			this.explotar();
			cuerpo.remover();
			break;

		case POWERUP:
			PowerUp powerUp = (PowerUp) cuerpo;

			this.setVelocidad(this.getVelocidad() * powerUp.getPowerUp());
			powerUp.timeout(this);
			powerUp.remover();
			break;

		default:
			break;
		}
	}

	public String getNombre() {
		return this.nombre;
	}

	public double getVelocidadMax() {
		return velocidadMax;
	}
}
