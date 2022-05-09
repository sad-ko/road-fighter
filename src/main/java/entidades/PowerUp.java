package entidades;

import java.util.Timer;
import java.util.TimerTask;
import fisica.Vector2D;

/**
 * La clase {@code PowerUp} hija de {@code Cuerpo}, es una clase a modo de
 * prueba para experimentar con otros {@code Cuerpo}s que pudieran interactuar
 * con {@code Jugador}
 */
public class PowerUp extends Cuerpo {

	private float power = 1.5f;
	private long tiempo = 1000L;

	public PowerUp(Vector2D posicion, Vector2D hitboxTamanio) {
		super("PowerUp", posicion, hitboxTamanio);
	}

	public float getPowerUp() {
		return power;
	}

	/**
	 * Remueve el bonus aplicado al jugador una vez su tiempo se ha agotado
	 * 
	 * @param jugador :{@code Jugador} con el bonus previamente aplicado
	 */
	public void timeout(Jugador jugador) {
		TimerTask task = new TimerTask() {
			public void run() {
				float vel = jugador.getVelocidad() / power;
				jugador.setVelocidad(Math.round(vel));

				System.err.println("POWER OUT...");
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, this.tiempo);
	}
}
