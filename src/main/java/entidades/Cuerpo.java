package entidades;

import fisica.Colision;
import fisica.Vector2D;

/**
 * La clase {@code Cuerpo} es una clase abstracta, hija de {@code Objeto}, a la
 * cual heredan todas las clases del paquete de entidades que requieran tener
 * una Colision.
 */
public abstract class Cuerpo extends Objeto {

	private Colision hitbox;

	/**
	 * @param clase        :{@code String} - Nombre de la clase no-abstracta que
	 *                     hereda de {@code Objeto}.
	 * @param posicion     :{@code Vector2D} - Posicion del {@code Cuerpo} en el plano
	 *                     {x,y}.
	 * @param hitboxTamaño :{@code Vector2D} - Tamaño de la {@code Colision}
	 */
	public Cuerpo(final String clase, Vector2D posicion, final Vector2D hitboxTamaño) {
		super(clase, posicion);
		this.hitbox = new Colision(hitboxTamaño, this);
	}

	public Colision getHitbox() {
		return hitbox;
	}
}
