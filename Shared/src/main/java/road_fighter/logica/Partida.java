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
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.entidades.Etiqueta;
import road_fighter.entidades.Resultados;
import road_fighter.entidades.cuerpos.Competidor;
import road_fighter.fisica.Vector2D;

/**
 * La clase {@code Partida} es la clase principal del juego, la que comienza,
 * termina y define el resultado de la partida.
 */
public class Partida {

	private List<Posicion> posiciones;
	private long tiempoEspera;
	private Mapa mapa;
	private Competidor ganador;
	private Etiqueta resultadosLabel;
	private Resultados resultadosScreen;

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

		resultadosLabel = new Etiqueta("...", new Vector2D(Config.width * 0.78, Config.height / 6));
		resultadosLabel.setFont(Font.font("MONOSPACED", Config.width / 64));
		resultadosLabel.setColor(Color.CORAL);
		Invocador.getInstancia().add(resultadosLabel);

		resultadosScreen = new Resultados();
		resultadosScreen.getRender().setVisible(false);
		Invocador.getInstancia().add(resultadosScreen);
	}

	private String resultados() {
		StringBuilder str = new StringBuilder();

		str.append(posiciones.get(0));
		for (int i = 1; i < posiciones.size(); i++) {
			str.append("\n" + posiciones.get(i));
		}

		return str.toString();
	}

	/**
	 * Comienza la partida, agregando a los {@code Jugador}es y generando la
	 * {@code Meta} en el mapa.
	 * 
	 * @param cantidad :{@code int} - Cantidad de jugadores en la partida.
	 */
	public void comenzar(int cantidad, List<String> jugadores) {
		this.mapa.posicionarCompetidores(this, cantidad, jugadores);
		this.mapa.crearMeta(this);
	}

	public void agregarCompetidor(Competidor jugador) {
		this.posiciones.add(new Posicion(jugador));
	}

	public Jugador getJugador(int id) {
		for (Posicion posicion : posiciones) {
			if (posicion.getCompetidor().getId() == id) {
				Competidor competidor = posicion.getCompetidor();
				Jugador jugador = new Jugador(competidor.getPosicion(), competidor.getNombre(), id);

				Invocador.getInstancia().remove(competidor);
				Invocador.getInstancia().add(jugador);
				return jugador;
			}
		}
		return null;
	}

	/**
	 * Inicializa la espera del resto de los jugadores para que lleguen a la meta,
	 * una vez terminado el tiempo la partida sera dada por terminada.
	 */
	public void iniciarEspera() {
		TimerTask task = new TimerTask() {
			public void run() {
				resultadosScreen.setResultados(resultados());
				resultadosScreen.getRender().setVisible(true);
				resultadosLabel.getRender().setVisible(false);
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
				double coordenadaYjug1 = o1.getCompetidor().getCurrentPos();
				double coordenadaYjug2 = o2.getCompetidor().getCurrentPos();

				return (int) (coordenadaYjug2 - coordenadaYjug1);
			}
		});

		for (int i = 0; i < this.posiciones.size(); i++) {
			this.posiciones.get(i).setPosicionActual(i + 1);
		}

		if (this.ganador == null) {
			resultadosLabel.setText(resultados());
		}
	}

	public int getCantidadJugadores() {
		return posiciones.size();
	}

	public Competidor getCompetidor(int index) {
		return posiciones.get(index).getCompetidor();
	}

	public List<Competidor> getCompetidores() {
		List<Competidor> competidores = new ArrayList<>();
		for (Posicion posicion : posiciones) {
			competidores.add(posicion.getCompetidor());
		}
		return competidores;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public Competidor getGanador() {
		return ganador;
	}

	public void setGanador(Competidor ganador) {
		this.ganador = ganador;
	}

	private long frame = 0;

	public void debug() {
		frame++;

		if (frame % 60 == 0) {
			StringBuilder str = new StringBuilder();
			str.append("Frame: " + frame + "\n");
			for (Posicion posicion : posiciones) {
				Competidor cmp = posicion.getCompetidor();
				str.append(cmp.getPosicion() + " - " + cmp.getVelocidad() + " " + cmp.getCurrentPos() + "\n");
			}
			System.out.println(str);
		}
	}

}
