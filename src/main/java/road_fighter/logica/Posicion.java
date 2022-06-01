package road_fighter.logica;

import road_fighter.entidades.cuerpos.Competidor;

public class Posicion {

	private final Competidor competidor;
	private int posicionActual;

	public Posicion(Competidor competidor) {
		this.competidor = competidor;
		this.posicionActual = 0;
	}

	public Competidor getCompetidor() {
		return competidor;
	}

	public void setPosicionActual(int posicionActual) {
		this.posicionActual = posicionActual;
	}

	@Override
	public String toString() {
		return competidor.getNombre() + " - Pos: " + posicionActual + " - " + competidor.getCurrentPos();
	}
}
