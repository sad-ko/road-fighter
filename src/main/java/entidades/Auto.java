package entidades;

import fisica.Vector2D;

/**
 * La clase {@code Auto} es una clase abstracta que hereda de {@code Cuerpo} y
 * es la base para los autos creados en el juego.
 */
public abstract class Auto extends Cuerpo {

	/**
	 * Velocidad inicial del {@code Auto}, comienza en 0.0f
	 */
	protected float velocidad = 0.0f;
	protected boolean exploto = false; // Temporal, solo para probar los Unit Test

	/**
	 * @param clase    :{@code String} - Nombre de la clase no-abstracta que hereda
	 *                 de {@code Auto}.
	 * @param posicion :{@code Vector2D} - Posicion del auto en el plano (x,y).
	 */
	protected Auto(String clase, Vector2D posicion) {
		super(clase, posicion, new Vector2D(1f, 1f));
	}

	/**
	 * Calcula las siguientes posiciones de ambos autos al chocar.
	 * 
	 * @param otroAuto :{@code Auto} - Auto con el que se impacto
	 */
	public void impacto(Auto otroAuto) {
		System.err.println(this.getNombre() + " choco con " + otroAuto.getNombre());
		// TODO: Calculos del impacto
	}

	public void explotar() {
		// TODO: Animar choque contra borde del mapa
		this.exploto = true; // Temporal, solo para probar los Unit Test
	}

	public String getNombre() {
		return this.getClase();
	}

	public boolean getExploto() {
		return this.exploto; // Temporal, solo para probar los Unit Test
	}

	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}

}
