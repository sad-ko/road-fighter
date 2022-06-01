package parte_uno.test;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

import road_fighter.entidades.Entidad;
import road_fighter.entidades.cuerpos.Competidor;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;

public class TestPartida {

	private Partida partida;
	private Jugador jugador;
	private Invocador invocador;

	@Before
	/**
	 * Funcion que se ejecuta previo a realizar cada tests.
	 */
	public void setup() {
		Mapa mapa = new Mapa(1500f, 5f, 150f);
		mapa.agregarObstaculos(Entidad.AUTO_ESTATICO, 5);
		mapa.agregarObstaculos(Entidad.POWERUP, 3);
		mapa.agregarObstaculos(Entidad.OBSTACULO, 15);

		invocador = Invocador.getInstancia();

		partida = new Partida(mapa, 2L);
		jugador = partida.comenzar(3);
	}

	@Test
	public void testMapaCreado() {
		int jugadoresAgregados = 3;
		int autosCreados = 5;
		int obstaculos = 15;
		int powerUps = 3;
		int bordes = 2;
		int meta = 1;
		int resultado = autosCreados + obstaculos + bordes + jugadoresAgregados + powerUps + meta;
		assertEquals(resultado, invocador.size());
	}

	@Test
	public void testJugadoresAgregados() {
		int jugadoresAgregados = 3;
		assertEquals(jugadoresAgregados, partida.getCantidadJugadores());
	}

	@Test
	/**
	 * Desplazamos al primer jugador por el eje X mientras acelera para verificar si
	 * choca contra el borde izquierdo previamente definido en el constructor del
	 * mapa.
	 */
	public void testExplotarBorde() {
		for (int i = 0; i < 70; i++) {
			invocador.calcularColisiones();
			jugador.acelerar();
			jugador.desplazar();
		}

		assertEquals(true, jugador.getExploto());
	}

	// @Test
	/**
	 * Aceleramos al segundo jugador hasta la meta del mapa para verificar que una
	 * partida puede tener un ganador.
	 */
	/*
	 * public void testLlegarMeta() { Jugador jugador = partida.getJugador(1);
	 * 
	 * for (int i = 0; i < 700; i++) { invocador.calcularColisiones();
	 * jugador.acelerar(delta); }
	 * 
	 * assertEquals(jugador, partida.getGanador()); }
	 */

	@Test
	/**
	 * Busca verificar que las posiciones al final de la partida de 3 jugadores que
	 * aceleran y/o desaceleran sean correctas.
	 */
	public void testDeterminarPodioJugadores() {
		Competidor jugador2 = partida.getCompetidor(1);
		Competidor jugador3 = partida.getCompetidor(2);

		for (int i = 0; i < 700; i++) {

			jugador.acelerar();
			jugador3.setVelocidad(5);
			invocador.calcularColisiones();
			partida.determinarPosiciones();
		}

		try {
			// Esperamos 3 segundos a que el tiempo de espera se agote.
			new CountDownLatch(1).await(1L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

		assertEquals(jugador, partida.getCompetidor(0)); // posicion 1 : jugador
		assertEquals(jugador3, partida.getCompetidor(1)); // posicion 2 : jugador3
		assertEquals(jugador2, partida.getCompetidor(2)); // posicion 3 : jugador2
	}

}
