package entidades;

import fisica.Vector2D;

/**
 * La clase {@code Objeto} es una clase abstracta a la cual heredan todas las
 * clases del paquete de entidades.
 */
public abstract class Objeto {

	/**
	 * Nombre de la clase no-abstracta que hereda de {@code Cuerpo}.
	 */
	final private String clase;

	/**
	 * Posicion del objeto en el plano (x,y).
	 */
	protected Vector2D posicion;

	/**
	 * @param clase    :{@code String} - Nombre de la clase no-abstracta que hereda
	 *                 de {@code Cuerpo}.
	 * @param posicion :{@code Vector2D} - Posicion del objeto en el plano {x,y}.
	 */
	public Objeto(final String clase, final Vector2D posicion) {
		this.clase = clase;
		this.posicion = posicion;
	}

	public String getClase() {
		return this.clase;
	}

	public Vector2D getPosicion() {
		return posicion;
	}

	/**
	 * Remueve el objeto del mapa <strong>[FALTA IMPLEMENTAR]</strong>
	 */
	public void removerObjeto() {
		//...
	}
}
