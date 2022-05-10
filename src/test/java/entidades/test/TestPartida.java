package entidades.test;

import static org.junit.Assert.*;
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
		int bordes = 3;
		assertEquals(autosCreados + bordes + jugadoresAgregados + powerUps, partida.getMapa().size());
	}

	@Test
	public void testJugadoresAgregados() {
		int jugadoresAgregados = 3;
		assertEquals(jugadoresAgregados, partida.getPosiciones().size());
	}

	@Test
	public void testExplotarBorde() {
		Mapa mapa = partida.getMapa();
		Jugador jugador = partida.getPosiciones().get(0).getJugador();

		for (int i = 0; i < 70; i++) {
			mapa.calcularColisiones();
			jugador.acelerar(delta);
			jugador.desplazar(false, delta);
		}

		assertEquals(true, jugador.getExploto());
	}

	@Test
	public void testLlegarMeta() {
		Mapa mapa = partida.getMapa();
		Jugador jugador = partida.getPosiciones().get(1).getJugador();

		for (int i = 0; i < 700; i++) {
			mapa.calcularColisiones();
			jugador.acelerar(delta);
		}

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(jugador, partida.ganador);
	}

}
