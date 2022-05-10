package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import entidades.Jugador;
import fisica.Vector2D;

/**
 * La clase {@code Partida} es la clase principal del juego, la que comienza,
 * termina y define el resultado de la partida.
 */
public class Partida {

	private List<Posicion> posiciones;
	private long tiempoEspera;
	private Mapa mapa;
	private Jugador ganador;

	/**
	 * @param mapa         :{@code Mapa} - Mapa donde se jugara la partida.
	 * @param tiempoEspera :{@code long} - Tiempo de espera (en milisegundos) para
	 *                     que lleguen el resto de jugadores una vez haya llegado
	 *                     alguien a la meta.
	 */
	public Partida(Mapa mapa, long tiempoEspera) {
		this.ganador = null;
		this.mapa = mapa;
		this.tiempoEspera = tiempoEspera;
		this.posiciones = new ArrayList<>();
	}

	private void terminarPartida() {
		// TODO: Ganador deberia ser asignado aqui en base a las posiciones finales.
		System.err.println("[PARTIDA TERMINADA]");
		this.posiciones.forEach(System.out::println);
	}

	/**
	 * Comienza la partida, agregando a los {@code Jugador}es y generando la
	 * {@code Meta} en el mapa.
	 * 
	 * @param cantJugadores :{@code int} - Cantidad de jugadores en la partida.
	 */
	public void comenzar(int cantJugadores) {
		this.mapa.agregarJugadores(this, cantJugadores);
		this.mapa.crearMeta(this);
	}

	public void agregarJugador(float pos, String nombre) {
		Jugador jugador = new Jugador(new Vector2D(pos, 0f), nombre);
		this.mapa.instanciar(jugador);
		this.posiciones.add(new Posicion(jugador));
	}

	/**
	 * Inicializa la espera del resto de los jugadores para que lleguen a la meta,
	 * una vez terminado el tiempo la partida sera dada por terminada.
	 */
	public void iniciarEspera() {
		TimerTask task = new TimerTask() {
			public void run() {
				terminarPartida();
			}
		};

		Timer timer = new Timer();
		timer.schedule(task, this.tiempoEspera);
	}

	/**
	 * Determina las posiciones de todos los {@code Jugador}es durante el lapso de
	 * la partida.
	 */
	public void determinarPosiciones() {
		// TODO: Comparar posiciones en el eje y de cada jugador y acomodar sus
		// posiciones acorde.
	}

	public Jugador getJugador(int index) {
		return this.posiciones.get(index).getJugador();
	}

	public List<Posicion> getPosiciones() {
		return posiciones;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public Jugador getGanador() {
		return ganador;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}

}
