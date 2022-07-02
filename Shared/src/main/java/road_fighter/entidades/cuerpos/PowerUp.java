package road_fighter.entidades.cuerpos;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.ImageView;
import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.Sprite;

/**
 * La clase {@code PowerUp} hija de {@code Cuerpo}, aplica un boost de velocidad
 * al {@code Jugador}.
 */
public class PowerUp extends Colisionables {

	private static final int SIZE = (int) (Config.width * 0.025);
	private double power = 1.2;
	private long tiempo = 3500L;
	private Sprite sprite;
	private boolean toRemove = false;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del objeto en el plano (x,y).
	 */
	public PowerUp(Vector2D posicion, boolean renderizable, long cuerpo_id) {
		super(Entidad.POWERUP, posicion, new Vector2D(SIZE, -SIZE), cuerpo_id);

		if (renderizable) {
			this.sprite = new Sprite("img/powerUp.png", new Vector2D(SIZE, SIZE));
			this.sprite.realocate(new Vector2D(0, -SIZE));

			this.render = sprite.getRender();
		}
	}

	public PowerUp(Vector2D posicion, long cuerpo_id) {
		this(posicion, true, cuerpo_id);
	}

	/**
	 * Remueve el bonus aplicado al jugador una vez su tiempo se ha agotado
	 * 
	 * @param competidor :{@code Jugador} con el bonus previamente aplicado
	 */
	public void timeout(Competidor competidor) {
		TimerTask task = new TimerTask() {
			public void run() {
				competidor.unsetVelocidad(power);
				competidor.setPower(false);
				competidor.getRender().setEffect(null);
				toRemove = true;
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
	public void update(double delta) {
		double y = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY(this.posicion.getY() + y);

		if (this.render != null) {
			Sprite.setRenderPosition((ImageView) this.render, this.posicion);
		}

		if (toRemove) {
			this.remover();
		}
	}

	public static int getAncho() {
		return SIZE;
	}

}
