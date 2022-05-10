package entidades;

import fisica.Vector2D;

public class Obstaculo extends Cuerpo {

	protected Obstaculo(Vector2D posicion, Vector2D hitboxTamanio) {
		super("Obstaculo", posicion, hitboxTamanio);
	}

}
