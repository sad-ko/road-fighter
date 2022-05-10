package entidades;

import fisica.Vector2D;

public final class Borde extends Cuerpo {

	public Borde(float ancho, float largo) {
		super("Borde", new Vector2D(ancho, 0f), new Vector2D(1f, largo));
	}

}
