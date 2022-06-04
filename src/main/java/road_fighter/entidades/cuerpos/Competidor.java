package road_fighter.entidades.cuerpos;

import javafx.animation.RotateTransition;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
	protected double velocidadMax = 200.0;
	protected double aceleracion = 5;
	protected double currentPos = 0.0;
	protected boolean llegoMeta = false;
	protected String nombre;

	private boolean power = false;
	private Lighting rainbowEffect;
	private double hue = 0.0;
	private RotateTransition rotateAnim;
	private boolean isBot = false;

	/**
	 * @param posicion :{@code Vector2D} - Posicion del {@code Jugador} en el plano
	 *                 (x,y).
	 * @param nombre   :{@code String} - Nombre del jugador.
	 */
	public Competidor(Vector2D posicion, String nombre) {
		super(Entidad.JUGADOR, posicion, "img/auto.png", new Vector2D(11, 16));
		this.nombre = nombre;
		this.rainbowEffect = new Lighting(new Light.Distant(90, 90, Color.hsb(hue, 1.0, 1.0)));
	}

	@Override
	public void update(double delta) {
		super.update(delta);
		if (!llegoMeta) {
			this.currentPos += this.velocidad;
		}

		if (power) {
			this.hue++;
			this.rainbowEffect.setLight(new Light.Distant(90, 90, Color.hsb(hue, 1.0, 1.0)));
			this.render.setEffect(rainbowEffect);
		}

		if (isBot) {
			if (this.velocidad < this.velocidadMax) {
				this.velocidad += this.aceleracion;
			}
		}
	}

	@Override
	protected void mover(double delta) {
		double y = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY((this.posicion.getY() - this.velocidad * delta / Config.acceleration) + y);
	}

	@Override
	protected void impacto(Auto otroAuto) {
		super.impacto(otroAuto);

		rotateAnim = new RotateTransition(Duration.millis(1000), render);
		rotateAnim.setByAngle(360);
		rotateAnim.setOnFinished(event -> render.setRotate(0));
		rotateAnim.play();
	}

	@Override
	public void remover() {
		this.render.setImage(sprite.getSprite());
		this.exploto = false;

		this.posicion.setX(Config.mapRight - Config.mapLeft + this.ancho);
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {
		switch (cuerpo.getClase()) {
		case AUTO_ESTATICO:
			this.impacto((Auto) cuerpo);
			break;

		case BORDE:
			if (this.velocidad > 1.5) {
				if (rotateAnim != null) {
					rotateAnim.stop();
				}
				this.explotar();
			}
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
			if (power) {
				return;
			}

			PowerUp powerUp = (PowerUp) cuerpo;
			this.power = true;
			this.setVelocidad(powerUp.getPowerUp());
			powerUp.timeout(this);
			powerUp.remover();
			AudioSFX.getInstancia().play("powerUp");
			break;

		default:
			break;
		}
	}

	public void bot(boolean activate) {
		this.isBot = activate;
	}

	public String getNombre() {
		return this.nombre;
	}

	public double getCurrentPos() {
		return currentPos;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	@Override
	public void setVelocidad(double velocidad) {
		this.velocidad *= velocidad;
	}

	public void unsetVelocidad(double velocidad) {
		this.velocidad /= velocidad;
	}
}
