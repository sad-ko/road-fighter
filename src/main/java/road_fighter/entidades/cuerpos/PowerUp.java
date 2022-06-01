package road_fighter.entidades.cuerpos;

import java.util.Timer;
import java.util.TimerTask;

import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;

/**
 * La clase {@code PowerUp} hija de {@code Cuerpo}, aplica un boost de velocidad
 * al {@code Jugador}.
 */
public class PowerUp extends Colisionables {

	private double power = 1.5;
	private long tiempo = 5000L;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del objeto en el plano (x,y).
	 */
	public PowerUp(Vector2D posicion) {
		super(Entidad.POWERUP, posicion, new Vector2D(1, 1));
	}

	/**
	 * Remueve el bonus aplicado al jugador una vez su tiempo se ha agotado
	 * 
	 * @param competidor :{@code Jugador} con el bonus previamente aplicado
	 */
	public void timeout(Competidor competidor) {
		TimerTask task = new TimerTask() {
			public void run() {
				double vel = competidor.getVelocidad() / power;
				competidor.setVelocidad(vel);
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, this.tiempo);
	}

	public double getPowerUp() {
		return power;
	}

	public long getTiempo() {
		return tiempo;
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
