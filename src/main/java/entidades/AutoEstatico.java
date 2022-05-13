package entidades;

import fisica.Vector2D;

/**
 * La clase {@code AutoEstatico} hija de {@code Auto} y es un Auto controlado
 * por el sistema que se mueve verticalmente con una velocidad constante.
 */
public class AutoEstatico extends Auto {

	/**
	 * @param posicion :{@code Vector2D} - Posicion del auto en el plano (x,y).
	 */
	public AutoEstatico(Vector2D posicion) {
		super("AutoEstatico", posicion);
		this.velocidad = 25.0f;
	}

	public void mover(float delta) {
		float y = this.posicion.getY() + this.velocidad * delta;
		this.posicion.cambiarCoordenadas(this.posicion.getX(), y);
	}
}
