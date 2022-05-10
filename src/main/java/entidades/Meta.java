package entidades;

import fisica.Vector2D;
import logica.Partida;

public class Meta extends Cuerpo {

	public final Partida partidaActual;

	public Meta(float meta, float ancho, Partida partidaActual) {
		super("Meta", new Vector2D(0f, meta), new Vector2D(ancho, 2f));
		this.partidaActual = partidaActual;
	}
}
