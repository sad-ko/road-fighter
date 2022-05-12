package logica;

import entidades.Jugador;

public class Posicion {

	private final Jugador jugador;
	private int posicionActual;

	public Posicion(Jugador jugador) {
		this.jugador = jugador;
		this.posicionActual = 0;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public int getPosicionActual() {
		return posicionActual;
	}
	
	public void setPosicionActual(int posicionActual) {
		this.posicionActual = posicionActual;
	}

	@Override
	public String toString() {
		return jugador.getNombre() + " - Pos: " + posicionActual;
	}
}
