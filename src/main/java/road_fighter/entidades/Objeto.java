package road_fighter.entidades;

import javafx.scene.Node;
import road_fighter.fisica.Vector2D;
import road_fighter.logica.Invocador;

/**
 * La clase {@code Objeto} es una clase abstracta de la cual heredan todas las
 * clases del paquete de entidades.
 */
public abstract class Objeto {

	/**
	 * Nombre de la clase no-abstracta que hereda de {@code Objeto}.
	 */
	private final Entidad clase;

	/**
	 * Posicion del objeto en el plano (x,y).
	 */
	protected Vector2D posicion;

	/**
	 * 
	 */
	protected Node render;

	/**
	 * @param clase    :{@code String} - Nombre de la clase no-abstracta que hereda
	 *                 de {@code Objeto}.
	 * @param posicion :{@code Vector2D} - Posicion del objeto en el plano (x,y).
	 */
	protected Objeto(final Entidad clase, final Vector2D posicion) {
		this.clase = clase;
		this.posicion = new Vector2D(posicion);
	}

	public abstract void update(double delta);

	public void remover() {
		Invocador.getInstancia().remove(this);
	}

	public Entidad getClase() {
		return this.clase;
	}

	public Vector2D getPosicion() {
		return posicion;
	}

	public Node getRender() {
		return render;
	}
}
