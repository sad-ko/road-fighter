package entidades;

import fisica.Vector2D;

public abstract class Auto extends Cuerpo {

	/**
	 * Velocidad inicial del {@code Auto}, comienza en 0.0f
	 */
	protected float velocidad = 0.0f;

	protected Auto(String clase, Vector2D posicion, Vector2D hitboxTamaño) {
		super(clase, posicion, hitboxTamaño);
	}

	public void impacto(Auto otroAuto) {
		System.err.println(this.getClase() + " choco con " + otroAuto.getClase());
	}

}
