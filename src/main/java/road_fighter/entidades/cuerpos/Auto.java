package road_fighter.entidades.cuerpos;

import javafx.scene.image.ImageView;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.Sprite;

/**
 * La clase {@code Auto} es una clase abstracta que hereda de {@code Cuerpo} y
 * es la base para los autos creados en el juego.
 */
public abstract class Auto extends Cuerpo {

	/**
	 * Velocidad inicial del {@code Auto}, comienza en 0.0f
	 */
	protected double velocidad = 0.0;
	private boolean exploto = false; // Temporal, solo para probar los Unit Test
	private boolean impacto = false; // Temporal, solo para probar los Unit Test
	private Sprite sprite;

	private int orientation;

	/**
	 * @param clase    :{@code String} - Nombre de la clase no-abstracta que hereda
	 *                 de {@code Auto}.
	 * @param posicion :{@code Vector2D} - Posicion del auto en el plano (x,y).
	 */
	protected Auto(Entidad clase, Vector2D posicion, String imgDir, Vector2D imgSize) {
		super(clase, posicion, new Vector2D(imgSize.getX() * 3, -imgSize.getY() * 3));

		this.sprite = new Sprite(imgDir, imgSize, 3);
		this.sprite.realocate(new Vector2D(0, -imgSize.getY()));

		this.render = sprite.getRender();
	}

	/**
	 * Calcula las siguientes posiciones de ambos autos al chocar.
	 * 
	 * @param otroAuto :{@code Auto} - Auto con el que se impacto
	 */
	public void impacto(Auto otroAuto) {
		this.impacto = true; // Temporal, solo para probar los Unit Test
		otroAuto.impacto = true;
		/*
		 * orientation = (otroAuto.getPosicion().getX() - posicion.getX() > 0) ? -1 : 1;
		 * 
		 * TimerTask task = new TimerTask() { public void run() { impacto = false; } };
		 * 
		 * Timer timer = new Timer(); timer.schedule(task, 1000L);
		 */
	}

	public void explotar() {
		// TODO: Animar choque contra borde del mapa
		this.exploto = true; // Temporal, solo para probar los Unit Test
	}

	protected abstract void mover(double delta);

	@Override
	public void update(double delta) {
		Sprite.setRenderPosition((ImageView) this.render, this.posicion);
		mover(delta);

		if (impacto) {
			this.posicion.setX(posicion.getX() + (5 * this.orientation));
		}
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub

	}

	public boolean getExploto() {
		return this.exploto; // Temporal, solo para probar los Unit Test
	}

	public boolean getImpacto() {
		return this.impacto; // Temporal, solo para probar los Unit Test
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

}
