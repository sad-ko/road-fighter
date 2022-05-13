package entidades.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import entidades.Auto;
import entidades.AutoEstatico;
import entidades.Jugador;
import entidades.Obstaculo;
import entidades.PowerUp;
import fisica.Vector2D;
import logica.Invocador;

public class TestJugador {

	/**
	 * Test donde se verifica que el jugado cuando acelere, se haya desplazado una
	 * distancia Y, y su velocidad actual haya sido modificada.
	 */
	@Test
	public void testAcelerarJugador() {
		Jugador jugador = new Jugador(new Vector2D(0, 0), "Arnold");
		jugador.acelerar(1);

		assertTrue(jugador.getVelocidad() == 2);
		assertTrue(jugador.getPosicion().getY() == 2);
	}

	/**
	 * Test donde se verifica que un jugador al desacelerar varias veces, se frene.
	 */
	@Test
	public void testDesacelerarHastaFrenar() {
		Jugador jugador = new Jugador(new Vector2D(0, 0), "Alexander");

		jugador.acelerar(1);
		jugador.desacelerar(1);
		jugador.desacelerar(1);
		jugador.desacelerar(1);

		assertTrue(jugador.getVelocidad() == 0);
	}

	/**
	 * Test donde se verifica que un jugador acelere hasta una velocidad maxima
	 * indicada.
	 */
	@Test
	public void testAcelerarHastaVelocidadMaxima() {
		Jugador jugador = new Jugador(new Vector2D(0, 0), "Ned");

		for (int i = 0; i < 250; i++) {
			jugador.acelerar(i);
		}

		assertTrue(jugador.getVelocidad() == jugador.getVelocidadMax());
	}

	@Test
	/**
	 * Test que verifica el poder desplazarse horizontalmente.
	 */
	public void testDesplazoHorizontal() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Bob");

		// true para girar a la derecha, false para girar a la izquierda
		jugador.desplazar(true, 1);
		jugador.desplazar(true, 1);
		jugador.desplazar(true, 1);
		jugador.desplazar(false, 1);

		assertEquals(2f, jugador.getPosicion().getX(), 0f);
	}

	@Test
	/**
	 * Test que verifica si un jugador colisiona con otro.
	 */
	public void testChocarOtroJugador() {
		Jugador jugador1 = new Jugador(new Vector2D(0f, 0f), "Elton");
		Jugador jugador2 = new Jugador(new Vector2D(5f, 0f), "Tito");

		Invocador invocador = new Invocador();
		invocador.instanciar(jugador1);
		invocador.instanciar(jugador2);

		for (int i = 0; i < 5; i++) {
			jugador1.desplazar(true, 1);
			invocador.calcularColisiones();
		}

		assertEquals(jugador1.getImpacto(), jugador2.getImpacto());
	}

	/**
	 * Test donde se verifica que el Jugador haya obtenido un Power Up del mapa.
	 */
	@Test
	public void jugadorChocaPowerUpTest() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Elsa Capunta");
		PowerUp nitro = new PowerUp(new Vector2D(5f, 0f));

		Invocador invocador = new Invocador();
		invocador.instanciar(jugador);
		invocador.instanciar(nitro);

		for (int i = 0; i < 250; i++) {
			jugador.desplazar(true, 1);
			invocador.calcularColisiones();
		}

		assertEquals(1 * nitro.getPowerUp(), jugador.getVelocidad(), 0f);
	}

	/**
	 * Test donde se verifica que el Jugador haya perdido el PowerUp obtenido
	 * despues del tiempo definido por el PowerUp.
	 */
	@Test
	public void jugadorPierdePowerUpTest() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Elsa Capunta");
		PowerUp nitro = new PowerUp(new Vector2D(5f, 0f));

		Invocador invocador = new Invocador();
		invocador.instanciar(jugador);
		invocador.instanciar(nitro);

		for (int i = 0; i < 250; i++) {
			jugador.desplazar(true, 1);
			invocador.calcularColisiones();
		}

		try {
			// Esperamos 3 segundos a que el tiempo de espera se agote.
			new CountDownLatch(1).await(nitro.getTiempo(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

		assertEquals(1, jugador.getVelocidad(), 0f);
	}

	/**
	 * Test donde se verifica la interseccion de un Jugador contra un enemigo
	 * estatico del mapa.
	 */
	@Test
	public void testChocaAutoEstatico() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Carlos");
		Auto enemigo = new AutoEstatico(new Vector2D(5f, 0f));

		Invocador invocador = new Invocador();
		invocador.instanciar(jugador);
		invocador.instanciar(enemigo);

		for (int i = 0; i < enemigo.getPosicion().getX(); i++) {
			jugador.desplazar(true, 1);
		}

		assertTrue(jugador.getHitbox().intersecta(enemigo));
	}

	/**
	 * Test donde se verifica la interseccion de un Jugador contra un obstaculo del
	 * mapa, y verifica que el auto haya explotado.
	 */
	@Test
	public void jugadorChocaObstaculoTest() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Elvis Nieto");
		Obstaculo obstaculo = new Obstaculo(new Vector2D(5f, 0f));

		Invocador invocador = new Invocador();
		invocador.instanciar(jugador);
		invocador.instanciar(obstaculo);

		for (int i = 0; i < obstaculo.getPosicion().getX(); i++) {
			jugador.desplazar(true, 1);
		}

		assertTrue(jugador.getHitbox().intersecta(obstaculo));
		assertTrue(jugador.getExploto());
	}

}
