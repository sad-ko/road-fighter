package road_fighter.entidades.cuerpos;

import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;
import road_fighter.logica.Partida;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
public class Competidor extends Auto {

	/**
	 * Velocidad maxima del {@code Jugador}, actualmente es 200.0f
	 */
	protected double currentPos = 0.0;
	protected boolean llegoMeta = false;
	protected String nombre;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del {@code Jugador} en el plano
	 *                 (x,y).
	 * @param nombre   :{@code String} - Nombre del jugador.
	 */
	public Competidor(Vector2D posicion, String nombre) {
		super(Entidad.JUGADOR, posicion, "img/auto.png", new Vector2D(11, 16));
		this.nombre = nombre;
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		if (!llegoMeta) {
			this.currentPos += this.velocidad;
		}
	}

	@Override
	protected void mover(double delta) {
		double y = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY((this.posicion.getY() - this.velocidad * delta / Config.acceleration) + y);
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
				AudioSFX.getInstancia().play("powerUp");
			}

			this.llegoMeta = true;
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

	public double getCurrentPos() {
		return currentPos;
	}
}
