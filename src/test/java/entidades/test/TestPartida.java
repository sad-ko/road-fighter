package entidades.test;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import entidades.Jugador;
import logica.Mapa;
import logica.Partida;

public class TestPartida {

	private final int FPS = 60;
	private final float delta = (float) 1 / FPS;
	private static Partida partida;

	@BeforeClass
	/**
	 * Funcion que se ejecuta previo a realizar todos los tests.
	 */
	public static void setup() {
		Mapa mapa = new Mapa(1500f, 5f, 150f);
		mapa.agregarObstaculos("AutoEstatico", 5);
		mapa.agregarObstaculos("PowerUp", 3);
		partida = new Partida(mapa, 5L);
		partida.comenzar(3);
	}

	@Test
	public void testMapaCreado() {
		int jugadoresAgregados = 3;
		int autosCreados = 5;
		int powerUps = 3;
		int bordes = 2;
		int meta = 1;
		int resultado = autosCreados + bordes + jugadoresAgregados + powerUps + meta;
		assertEquals(resultado, partida.getMapa().getInvocador().size());
	}

	@Test
	public void testJugadoresAgregados() {
		int jugadoresAgregados = 3;
		assertEquals(jugadoresAgregados, partida.getPosiciones().size());
	}

	@Test
	/**
	 * Desplazamos al primer jugador por el eje X mientras acelera para verificar si
	 * choca contra el borde izquierdo previamente definido en el constructor del
	 * mapa.
	 */
	public void testExplotarBorde() {
		Mapa mapa = partida.getMapa();
		Jugador jugador = partida.getJugador(0);

		for (int i = 0; i < 70; i++) {
			mapa.getInvocador().calcularColisiones();
			jugador.acelerar(delta);
			jugador.desplazar(false, delta);
		}

		assertEquals(true, jugador.getExploto());
	}

	@Test
	/**
	 * Aceleramos al segundo jugador hasta la meta del mapa para verificar que una
	 * partida puede terminar propiamente al agotarse el tiempo de espera por los
	 * otros jugadores.
	 */
	public void testLlegarMeta() {
		Mapa mapa = partida.getMapa();
		Jugador jugador = partida.getJugador(1);

		for (int i = 0; i < 700; i++) {
			mapa.getInvocador().calcularColisiones();
			jugador.acelerar(delta);
		}

		try {
			// Esperamos 3 segundos a que el tiempo de espera se agote.
			new CountDownLatch(1).await(2L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(jugador, partida.getGanador());
	}

}
