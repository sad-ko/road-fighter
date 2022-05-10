package entidades;

import fisica.Vector2D;

public abstract class Auto extends Cuerpo {

	/**
	 * Velocidad inicial del {@code Auto}, comienza en 0.0f
	 */
	protected float velocidad = 0.0f;
	protected boolean exploto = false; // Temporal, solo para probar los Unit Test

	protected Auto(String clase, Vector2D posicion) {
		super(clase, posicion, new Vector2D(1f, 1f));
	}

	public String getNombre() {
		return this.getClase();
	}

	public void impacto(Auto otroAuto) {
		System.err.println(this.getNombre() + " choco con " + otroAuto.getNombre());
		// TODO: Calculos del impacto
	}

	public void explotar() {
		// TODO: Animar choque contra borde del mapa
		this.exploto = true; // Temporal, solo para probar los Unit Test
	}

	public boolean getExploto() {
		return this.exploto;
	}

	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}

}
