package road_fighter.logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import road_fighter.Config;
import road_fighter.entidades.Etiqueta;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.fisica.Vector2D;
import road_fighter.graficos.AudioSFX;

/**
 * La clase {@code Partida} es la clase principal del juego, la que comienza,
 * termina y define el resultado de la partida.
 */
public class Partida {

	private List<Posicion> posiciones;
	private long tiempoEspera;
	private Mapa mapa;
	private Jugador ganador;
	private Etiqueta victoryLabel;

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

		victoryLabel = new Etiqueta("VICTORY", new Vector2D(Config.width / 6, Config.height / 4));
		victoryLabel.setFont(Font.font("MONOSPACED", 128));
		victoryLabel.setColor(Color.GOLD);
		victoryLabel.setVisible(false);
		Invocador.getInstancia().add(victoryLabel);
	}

	private void terminarPartida() {
		// TODO: Ganador deberia ser asignado aqui en base a las posiciones finales.
		System.out.println("[PARTIDA TERMINADA]");
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
		
		AudioSFX.getInstancia().play("largada_start");
	}

	public void agregarJugador(Jugador jugador) {
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
	 * la partida. Es decir, el podio
	 */
	public void determinarPosiciones() {

		Collections.sort(this.posiciones, new Comparator<Posicion>() {
			@Override
			public int compare(Posicion o1, Posicion o2) {
				double coordenadaYjug1 = o1.getJugador().getCurrentPos();
				double coordenadaYjug2 = o2.getJugador().getCurrentPos();

				return (int) (coordenadaYjug1 - coordenadaYjug2);
			}
		});

		for (int i = 0; i < this.posiciones.size(); i++) {
			this.posiciones.get(i).setPosicionActual(i + 1);
		}
	}

	public Jugador getJugador(int index) {
		return this.posiciones.get(index).getJugador();
	}

	public int getCantidadJugadores() {
		return posiciones.size();
	}

	public Mapa getMapa() {
		return mapa;
	}

	public Jugador getGanador() {
		return ganador;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
		this.victoryLabel.setVisible(true);
	}

}
