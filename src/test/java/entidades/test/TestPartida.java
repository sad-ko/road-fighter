package entidades.test;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import entidades.Jugador;
import logica.Invocador;
import logica.Mapa;
import logica.Partida;

public class TestPartida {

	private final int FPS = 60;
	private final float delta = (float) 1 / FPS;
	private Partida partida;
	private Invocador invocador;

	@Before
	/**
	 * Funcion que se ejecuta previo a realizar cada tests.
	 */
	public void setup() {
		Mapa mapa = new Mapa(1500f, 5f, 150f);
		mapa.agregarObstaculos("AutoEstatico", 5);
		mapa.agregarObstaculos("PowerUp", 3);
		mapa.agregarObstaculos("Obstaculo", 15);

		invocador = mapa.getInvocador();

		partida = new Partida(mapa, 2L);
		partida.comenzar(3);
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
		assertEquals(resultado, partida.getMapa().getInvocador().size());
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
		Jugador jugador = partida.getJugador(0);

		for (int i = 0; i < 70; i++) {
			invocador.calcularColisiones();
			jugador.acelerar(delta);
			jugador.desplazar(false, delta);
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
		Jugador jugador = partida.getJugador(0);
		Jugador jugador2 = partida.getJugador(1);
		Jugador jugador3 = partida.getJugador(2);

		for (int i = 0; i < 700; i++) {

			jugador.acelerar(delta);
			jugador2.acelerar(delta);
			jugador3.acelerar(delta);

			if (i > 10 && i < 50) {
				jugador2.desacelerar(delta);
			}

			if (i > 20 && i < 30) {
				jugador3.desacelerar(delta);
			}

			invocador.calcularColisiones();
			partida.determinarPosiciones();
		}

		try {
			// Esperamos 3 segundos a que el tiempo de espera se agote.
			new CountDownLatch(1).await(1L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

		assertEquals(jugador, partida.getJugador(0)); // posicion 1 : jugador
		assertEquals(jugador3, partida.getJugador(1)); // posicion 2 : jugador3
		assertEquals(jugador2, partida.getJugador(2)); // posicion 3 : jugador2
	}

}
