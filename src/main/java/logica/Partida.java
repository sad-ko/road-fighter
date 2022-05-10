package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import entidades.Jugador;
import entidades.Meta;
import fisica.Vector2D;

public class Partida {

	private List<Posicion> posiciones;
	private long tiempoEspera;
	private Mapa mapa;
	public Jugador ganador;

	public Partida(Mapa mapa, long tiempoEspera) {
		this.ganador = null;
		this.mapa = mapa;
		this.tiempoEspera = tiempoEspera;
		this.posiciones = new ArrayList<>();
	}

	private void agregarJugador(float pos, String nombre) {
		Jugador temp = new Jugador(new Vector2D(pos, 0f), nombre);
		this.mapa.getCuerposInstanciados().add(temp);
		this.posiciones.add(new Posicion(temp));
	}

	private void terminarPartida() {
		System.err.println("[PARTIDA TERMINADA]");
		this.posiciones.forEach(System.out::println);
	}

	public void comenzar(float cantJugadores) {
		// Me aseguro que las distancias entre los autos y tambien con el borde del
		// mapa, sean siempre equidistantes entre si.
		float incrementoPosicion = (mapa.getLimiteDerecho() - mapa.getLimiteIzquierdo()) / (cantJugadores + 1);
		float posicionEjeXAuto = 0;

		for (int i = 0; i < cantJugadores; i++) {
			posicionEjeXAuto += incrementoPosicion;
			agregarJugador(posicionEjeXAuto, "Jugador N°" + (i + 1));
		}

		Meta meta = new Meta(this.mapa.getLongitud(), this.mapa.getLimiteDerecho(), this);
		this.mapa.getCuerposInstanciados().add(meta);
	}

	public void iniciarEspera() {
		TimerTask task = new TimerTask() {
			public void run() {
				terminarPartida();
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, this.tiempoEspera);
	}

	public void determinarPosiciones() {
		// TODO: Comparar posiciones en el eje y de cada jugador y acomodar sus
		// posiciones acorde.
	}

	public List<Posicion> getPosiciones() {
		return posiciones;
	}

	public Mapa getMapa() {
		return mapa;
	}

}
