package road_fighter.entidades.cuerpos;

import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.logica.Invocador;

public abstract class Colisionables extends Cuerpo {

	protected Colisionables(Entidad clase, Vector2D posicion, Vector2D hitboxTamanio) {
		super(clase, posicion, hitboxTamanio);
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {
	}

	@Override
	public void remover() {
		Invocador.getInstancia().remove(this);
	}
}
