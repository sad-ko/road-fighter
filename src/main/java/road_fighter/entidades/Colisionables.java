package road_fighter.entidades;

import road_fighter.fisica.Vector2D;

public abstract class Colisionables extends Cuerpo {

	protected Colisionables(Entidad clase, Vector2D posicion, Vector2D hitboxTamanio) {
		super(clase, posicion, hitboxTamanio);
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {}
}
