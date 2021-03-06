package road_fighter.entidades.cuerpos;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.Sprite;
import road_fighter.logica.Invocador;
import road_fighter.logica.Partida;
import road_fighter.networking.Comando;

/**
 * La clase {@code Jugador} hija de {@code Cuerpo}, es la clase principal con la
 * cual el usuario va a poder interactuar con el entorno.
 */
public class Competidor extends Auto {

	/**
	 * Velocidad maxima del {@code Jugador}, actualmente es 200.0f
	 */
	protected double velocidadMax = 200.0;
	protected double aceleracion = 2.5;
	protected double currentPos = 0.0;
	protected boolean llegoMeta = false;
	protected String nombre;
	protected FadeTransition spawningAnim;
	protected int id;

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
	public Competidor(Vector2D posicion, String nombre, int id, boolean renderizable) {
		super(Entidad.JUGADOR, posicion, "img/auto.png", new Vector2D(11, 16), renderizable, id);
		this.nombre = nombre;
		this.id = id;

		if (renderizable) {
			this.rainbowEffect = new Lighting(new Light.Distant(0, 90, Color.hsb(hue, 0.75, 0.75)));

			spawningAnim = new FadeTransition(Duration.millis(500), render);
			spawningAnim.setFromValue(1.0);
			spawningAnim.setToValue(0.3);
			spawningAnim.setCycleCount(6);
			spawningAnim.setAutoReverse(true);
			spawningAnim.setOnFinished(event -> {
				render.setOpacity(1.0);
				hitbox.desactivar(false);
				aceleracion = 2.5;
			});

			spawning();
			aceleracion = 0.0;

			ColorAdjust colorAdjust = new ColorAdjust();
			colorAdjust.setContrast(0.1);
			colorAdjust.setHue(hue + 100 * id);
			colorAdjust.setBrightness(0.1);
			colorAdjust.setSaturation(0.2);
			this.render.setEffect(colorAdjust);
		}
	}

	public Competidor(Vector2D posicion, String nombre, int id) {
		this(posicion, nombre, id, true);
	}

	protected void spawning() {
		if (this.renderizable) {
			spawningAnim.play();
			render.setImage(sprite.getSprite());
		}
		hitbox.desactivar(true);
		aceleracion = 0.5;
	}

	@Override
	public void update(double delta) {
		super.update(delta);

		if (!llegoMeta) {
			this.currentPos += this.velocidad;
		} else if (this.aceleracion > 0.0) {
			this.aceleracion -= 0.1;
		}

		if (power && renderizable) {
			this.hue++;
			this.rainbowEffect.setLight(new Light.Distant(0, 90, Color.hsb(hue, 0.75, 0.75)));
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
		double y_offset = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY((this.posicion.getY() - this.velocidad * delta / Config.acceleration) + y_offset);
	}

	@Override
	protected void impacto(Auto otroAuto) {
		super.impacto(otroAuto);

		if (renderizable) {
			rotateAnim = new RotateTransition(Duration.seconds(1), render);
			rotateAnim.setByAngle(360.0 * this.orientation);
			rotateAnim.setOnFinished(event -> render.setRotate(0.0));
			rotateAnim.play();
		}
	}

	@Override
	public void remover() {
		spawning();
		this.exploto = false;
		this.posicion.setX(Config.mapRight - Config.mapLeft + this.ancho);

		if (renderizable) {
			Sprite.setRenderPosition(this.render, this.posicion);
		}
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {
		Invocador.getInstancia().cuerposPendientes.add(Invocador.getInstancia().getIndex(this));
		Invocador.getInstancia().cuerposPendientes.add(Invocador.getInstancia().getIndex(cuerpo));

		switch (cuerpo.getClase()) {
		case AUTO_ESTATICO:
		case JUGADOR:
			this.impacto((Auto) cuerpo);
			break;

		case BORDE:
			if (this.velocidad > 2.0 || this.choque) {
				if (rotateAnim != null) {
					rotateAnim.stop();
				}
				this.explotar();
			}
			break;

		case META:
			Meta meta = (Meta) cuerpo;
			Partida partidaActual = meta.getPartidaActual();

			if (partidaActual.getGanador() == null) {
				partidaActual.setGanador(this);
				partidaActual.iniciarEspera();
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

			if (this.render != null) {
				powerUp.getRender().setVisible(false);
			}

			powerUp.timeout(this);
			break;

		default:
			break;
		}
	}

	public void acelerar() {
		if (this.velocidad < this.velocidadMax) {
			this.velocidad += this.aceleracion;
		}
	}

	public void desacelerar() {
		if (this.velocidad > this.aceleracion * this.aceleracion) {
			this.velocidad = this.velocidad - this.aceleracion * this.aceleracion;
		} else {
			this.velocidad = 0.0;
		}
	}

	public void desplazar(Comando comando) {
		double sentido = (comando == Comando.DESPLAZAR_DERECHA) ? aceleracion : -aceleracion;
		this.posicion.setX(this.posicion.getX() + sentido);
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

	public int getId() {
		return id;
	}

}
