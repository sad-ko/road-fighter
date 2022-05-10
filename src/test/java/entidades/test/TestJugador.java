package entidades.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import entidades.Auto;
import entidades.AutoEstatico;
import entidades.Cuerpo;
import entidades.Jugador;
import entidades.PowerUp;
import fisica.Vector2D;

public class TestJugador {
	@Test
	public void test() {
		ArrayList<Cuerpo> objetosInstanciados = new ArrayList<>();

		Vector2D posInicialJugador = new Vector2D(0f, 0f);
		Jugador jugador = new Jugador(posInicialJugador, "player");

		Vector2D posInicialPowerUP = new Vector2D(101f, 29299f);
		PowerUp power = new PowerUp(posInicialPowerUP);

		Vector2D posInicialAutoEstatico = new Vector2D(-99f, 27499f);
		AutoEstatico auto = new AutoEstatico(posInicialAutoEstatico);

		objetosInstanciados.add(jugador);
		objetosInstanciados.add(power);
		objetosInstanciados.add(auto);

		for (int i = 0; i <= 350; i++) {
			revisarColisiones(objetosInstanciados);

			System.out.println(jugador);
			jugador.desplazar(i % 2 == 0, 1); // Zig Zag

			// Desacelera durante 50 iteraciones
			if (i < 100 || i > 150) {
				jugador.acelerar(1);
			} else {
				jugador.desacelerar(1);
			}
		}

		System.err.println("[END]");

	}

	// esto lo agregue en clase Partida
	public static void revisarColisiones(ArrayList<Cuerpo> objetosInstanciados) {
		int size = objetosInstanciados.size();

		for (int i = 0; i < size; i++) {
			Cuerpo a = objetosInstanciados.get(i);
			for (int j = i + 1; j < size; j++) {
				Cuerpo b = objetosInstanciados.get(j);
				a.getHitbox().intersecta(b);
			}
		}
	}

	/**
	 * 
	 */
	@Test
	public void acelerarJugadorTest() {
		Jugador jugador = new Jugador(new Vector2D(0, 0), "Player1");
		jugador.acelerar(1);

		assertTrue(jugador.getVelocidad() == 2);
		assertTrue(jugador.getPosicion().getY() == 2); // Revisar funcionamiento, deberia ser 1 o 2?
	}

	/**
	 * 
	 */
	@Test
	public void desacelerarHastaFrenarTest() {
		Jugador jugador = new Jugador(new Vector2D(0, 0), "Player1");

		jugador.acelerar(1);
		jugador.desacelerar(1);
		jugador.desacelerar(1);
		jugador.desacelerar(1);

		assertTrue(jugador.getVelocidad() == 0);
	}

	/**
	 * 
	 */
	@Test
	public void acelerarHastaVelocidadMaximaTest() {
		Jugador jugador = new Jugador(new Vector2D(0, 0), "Player1");

		for (int i = 0; i < 250; i++) {
			jugador.acelerar(i);
		}

		assertTrue(jugador.getVelocidad() == 200);
	}

	@Test
	public void caso01_MoverseIzq_y_Derecha() {
		Jugador jugador1 = new Jugador(new Vector2D(0f, 0f), "player1");

		// true para girar a la derecha, false para girar a la izquierda
		jugador1.desplazar(true, 1);
		jugador1.desplazar(true, 1);
		jugador1.desplazar(true, 1);
		jugador1.desplazar(false, 1);

		assertEquals(2, jugador1.getPosicion().getX(), 0.00);

	}

	@Test
	public void caso02_ChocaOtroJugador() {
		Jugador jugador1 = new Jugador(new Vector2D(0f, 0f), "player1");
		Jugador jugador2 = new Jugador(new Vector2D(5f, 0f), "player2");

		ArrayList<Cuerpo> objetosInstanciados = new ArrayList<>();
		objetosInstanciados.add(jugador1);
		objetosInstanciados.add(jugador2);

		for (int i = 0; i < 5; i++) {
			jugador1.desplazar(true, 1); // true para girar a la derecha, false para girar a la izquierda
			System.out.println(jugador1);
			System.out.println(jugador2);
			// revisarColisiones(objetosInstanciados);
		}
		assertTrue(jugador1.getHitbox().intersecta(jugador2));
	}

	/**
	 * 
	 */
	@Test
	public void jugadorChocaPowerUpTest() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Player1");
		PowerUp nitro = new PowerUp(new Vector2D(5f, 0f));

		List<Cuerpo> objetosInstanciados = new ArrayList<>();
		objetosInstanciados.add(jugador);
		objetosInstanciados.add(nitro);

		for (int i = 0; i < 5; i++) {
			jugador.desplazar(true, 1);
		}
		assertTrue(jugador.getHitbox().intersecta(nitro));
	}

	/**
	 * 
	 */
	@Test
	public void jugadorChocaAutoEstaticoTest() {
		Jugador jugador = new Jugador(new Vector2D(0f, 0f), "Player1");
		Auto enemigo = new AutoEstatico(new Vector2D(5f, 0f));

		List<Cuerpo> objetosInstanciados = new ArrayList<>();
		objetosInstanciados.add(jugador);
		objetosInstanciados.add(enemigo);

		for (int i = 0; i < enemigo.getPosicion().getX(); i++) {
			jugador.desplazar(true, 1);
		}
		assertTrue(jugador.getHitbox().intersecta(enemigo));
	}

}
