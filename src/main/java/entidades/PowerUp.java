package entidades;

import java.util.Timer;
import java.util.TimerTask;
import fisica.Vector2D;

/**
 * La clase {@code PowerUp} hija de {@code Cuerpo}, aplica un boost de velocidad
 * al {@code Jugador}.
 */
public class PowerUp extends Cuerpo {

	private float power = 1.5f;
	private long tiempo = 3000L;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del objeto en el plano (x,y).
	 */
	public PowerUp(Vector2D posicion) {
		super("PowerUp", posicion, new Vector2D(1f, 1f));
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
				jugador.setVelocidad(vel);
				System.out.println("POWER OUT...");
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, this.tiempo);
	}

	public float getPowerUp() {
		return power;
	}
}
