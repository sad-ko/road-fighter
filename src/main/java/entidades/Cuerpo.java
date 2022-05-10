package entidades;

import fisica.Colision;
import fisica.Vector2D;

/**
 * La clase {@code Cuerpo} es una clase abstracta, hija de {@code Objeto}, a la
 * cual heredan todas las clases del paquete de entidades que requieran tener
 * una {@code Colision}.
 */
public abstract class Cuerpo extends Objeto {

	protected Colision hitbox;

	/**
	 * @param clase         :{@code String} - Nombre de la clase no-abstracta que
	 *                      hereda de {@code Objeto}.
	 * @param posicion      :{@code Vector2D} - Posicion del {@code Cuerpo} en el
	 *                      plano (x,y).
	 * @param hitboxTamanio :{@code Vector2D} - Tamaño de la {@code Colision}
	 */
	protected Cuerpo(final String clase, Vector2D posicion, final Vector2D hitboxTamanio) {
		super(clase, posicion);
		this.hitbox = new Colision(hitboxTamanio, this);
	}

	public Colision getHitbox() {
		return hitbox;
	}

	@Override
	public String toString() {
		return "Posicion: " + posicion + " | Hitbox: " + hitbox;
	}
}
