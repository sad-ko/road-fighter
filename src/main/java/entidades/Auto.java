package entidades;

import fisica.Vector2D;

public abstract class Auto extends Cuerpo {

	/**
	 * Velocidad inicial del {@code Auto}, comienza en 0.0f
	 */
	protected float velocidad = 0.0f;


	protected Auto(String clase, Vector2D posicion, Vector2D hitboxTamanio) {
		super(clase, posicion, hitboxTamanio);
	}

	public String getNombre() {
		return this.getClase();
	}
	
	public void impacto(Auto otroAuto) {
		System.err.println(this.getNombre() + " choco con " + otroAuto.getNombre());
	}
	
	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}

}
