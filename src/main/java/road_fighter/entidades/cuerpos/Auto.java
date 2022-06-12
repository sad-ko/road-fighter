package road_fighter.entidades.cuerpos;

import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AnimatedSprite;
import road_fighter.graficos.Sprite;

/**
 * La clase {@code Auto} es una clase abstracta que hereda de {@code Cuerpo} y
 * es la base para los autos creados en el juego.
 */
public abstract class Auto extends Cuerpo {

	protected static final int SIZE = (int) (Config.width * 0.003);
	protected double velocidad = 0.0;
	protected int orientation;
	protected boolean choque = false;
	protected boolean exploto = false;
	protected AnimatedSprite explosionAnimation;
	protected Sprite sprite;
	protected ImageView render;
	protected double ancho;

	/**
	 * @param clase    :{@code String} - Nombre de la clase no-abstracta que hereda
	 *                 de {@code Auto}.
	 * @param posicion :{@code Vector2D} - Posicion del auto en el plano (x,y).
	 */
	protected Auto(Entidad clase, Vector2D posicion, String imgDir, Vector2D imgSize) {
		super(clase, posicion, new Vector2D(imgSize.getX() * SIZE * 0.8, -imgSize.getY() * SIZE));

		this.sprite = new Sprite(imgDir, imgSize, SIZE);
		this.sprite.realocate(new Vector2D(0, -imgSize.getY()));

		this.render = sprite.getRender();
		this.ancho = imgSize.getX();

		Image explosion_1 = new Image("file:src/main/resources/img/explosion_1.png", 12 * SIZE, 13 * SIZE, false,
				false);
		Image explosion_2 = new Image("file:src/main/resources/img/explosion_2.png", 15 * SIZE, 16 * SIZE, false,
				false);
		Image explosion_3 = new Image("file:src/main/resources/img/explosion_3.png", 13 * SIZE, 16 * SIZE, false,
				false);

		explosionAnimation = new AnimatedSprite(new Image[] { explosion_1, explosion_2, explosion_3 }, render,
				Duration.seconds(1.5));
	}

	/**
	 * Calcula las siguientes posiciones de ambos autos al chocar.
	 * 
	 * @param otroAuto :{@code Auto} - Auto con el que se impacto
	 */
	protected void impacto(Auto otroAuto) {
		this.choque = true;
		orientation = (otroAuto.getPosicion().getX() - posicion.getX() > 0) ? -1 : 1;

		TimerTask task = new TimerTask() {
			public void run() {
				choque = false;
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, 1000L);
	}

	protected void explotar() {
		this.exploto = true;
		this.velocidad = 0;

		explosionAnimation.setOnFinished(event -> this.remover());
		explosionAnimation.play();
	}

	protected abstract void mover(double delta);

	@Override
	public void update(double delta) {
		if (choque) {
			this.posicion.setX(posicion.getX() + (2 * this.orientation));
		}

		mover(delta);
		Sprite.setRenderPosition(this.render, this.posicion);

		double x = this.posicion.getX();
		if (x < Config.mapLeft - this.ancho) {
			this.posicion.setX(x + this.ancho / 2);
		} else if (x > Config.mapRight) {
			this.posicion.setX(x - this.ancho / 2);
		}
	}

	public boolean isChoque() {
		return this.choque;
	}

	public boolean isExploto() {
		return exploto;
	}

	public double getVelocidad() {
		return velocidad;
	}

	@Override
	public Node getRender() {
		return this.render;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

}
