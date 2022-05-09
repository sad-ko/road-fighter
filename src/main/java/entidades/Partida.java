package entidades;

import java.util.ArrayList;

import fisica.Vector2D;


public class Partida {

	private ArrayList<Jugador> jugadores;
	private float duracion;
	private Mapa mapa;
	float cant_jugadores;
	private ArrayList<Cuerpo> objetosInstanciados;
	
	
	public Partida(int cantidadJugadores) {
		this.mapa = new Mapa();
		this.cant_jugadores = cantidadJugadores;
		this.jugadores = new ArrayList<Jugador>();
	}
	
	public void calcularColisiones() {
		int size = objetosInstanciados.size();

		for (int i = 0; i < size; i++) {
			Cuerpo a = objetosInstanciados.get(i);
			for (int j = i + 1; j < size; j++) {
				Cuerpo b = objetosInstanciados.get(j);
				a.getHitbox().intersecta(b);
			}
		}
	}
	
	public void determinarPosiciones() {
		float posicionEjeXAuto = 0;
		
		//me aseguro que las distancias entre los autos
		//y tambien con el borde del mapa, sean siempre equidistantes entre si.
		float incrementoPosicion = (mapa.getLimiteDerecho()-mapa.getLimiteIzquierdo())/(cant_jugadores+1); 
		for (int i = 0; i < cant_jugadores; i++) {
			posicionEjeXAuto+=incrementoPosicion;
			agregarJugador(posicionEjeXAuto,"jugador "+(i+1));
		}
	}
	
	private void agregarJugador(float pos, String nombre) {	
		this.jugadores.add(new Jugador(new Vector2D(pos, 0f),new Vector2D(1f, 1f),nombre));
	}
	
	public void iniciarEspera() {
		
	}
	
	public void terminarPartida() {
		
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}
	
}
