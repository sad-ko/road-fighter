package road_fighter.entidades.cuerpos;

import road_fighter.entidades.Entidad;
import road_fighter.entidades.Objeto;
import road_fighter.fisica.Colision;
import road_fighter.fisica.Vector2D;

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
	protected Cuerpo(final Entidad clase, Vector2D posicion, final Vector2D hitboxTamanio) {
		super(clase, posicion);
		this.hitbox = new Colision(hitboxTamanio, this);
	}

	public abstract void enChoque(Cuerpo cuerpo);

	public Colision getHitbox() {
		return hitbox;
	}
}
