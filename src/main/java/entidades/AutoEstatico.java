package entidades;

import fisica.Vector2D;

/**
 * La clase {@code AutoEstatico} hija de {@code Cuerpo}, es una clase a modo de
 * prueba para experimentar con otros {@code Cuerpo}s que pudieran interactuar
 * con {@code Jugador}
 */
public class AutoEstatico extends Cuerpo {

	public AutoEstatico(Vector2D posicion, Vector2D hitboxTamaño) {
		super("AutoEstatico", posicion, hitboxTamaño);
		// TODO Auto-generated constructor stub
	}
}
