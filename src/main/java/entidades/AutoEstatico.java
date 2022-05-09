package entidades;

import fisica.Vector2D;

/**
 * La clase {@code AutoEstatico} hija de {@code Cuerpo}, es una clase a modo de
 * prueba para experimentar con otros {@code Cuerpo}s que pudieran interactuar
 * con {@code Jugador}
 */
public class AutoEstatico extends Auto {

	public AutoEstatico(Vector2D posicion, Vector2D hitboxTamanio) {
		super("AutoEstatico", posicion, hitboxTamanio);
		this.velocidad = 100.0f;
	}
}
