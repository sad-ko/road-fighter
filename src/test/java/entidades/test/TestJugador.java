package entidades.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import entidades.AutoEstatico;
import entidades.Cuerpo;
import entidades.Jugador;
import entidades.PowerUp;
import fisica.Vector2D;

public class TestJugador {
	@Test
	public void test() {
		ArrayList<Cuerpo> objetosInstanciados = new ArrayList<Cuerpo>();

		Vector2D init_pos = new Vector2D(0f, 0f);
		Jugador jugador = new Jugador(init_pos, "player");

		Vector2D pow_init_pos = new Vector2D(101f, 29299f);
		PowerUp power = new PowerUp(pow_init_pos);

		Vector2D auto_init_pos = new Vector2D(-99f, 27499f);
		AutoEstatico auto = new AutoEstatico(auto_init_pos);

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

	@Test
	public void caso01_MoverseIzq_y_Derecha() {

		Vector2D init_pos1 = new Vector2D(0f, 0f);
		Jugador jugador1 = new Jugador(init_pos1, "player1");

		// true para girar a la derecha, false para girar a la izquierda
		jugador1.desplazar(true, 1);
		jugador1.desplazar(true, 1);
		jugador1.desplazar(true, 1);
		jugador1.desplazar(false, 1);

		assertEquals(2, jugador1.getPosicion().x, 0.00);

	}

	@Test
	public void caso02_ChocaOtroJugador() {

		ArrayList<Cuerpo> objetosInstanciados = new ArrayList<Cuerpo>();

		Vector2D init_pos1 = new Vector2D(0f, 0f);
		Jugador jugador1 = new Jugador(init_pos1, "player1");

		Vector2D init_pos2 = new Vector2D(5f, 0f);
		Jugador jugador2 = new Jugador(init_pos2, "player2");

		objetosInstanciados.add(jugador1);
		objetosInstanciados.add(jugador2);

		for (int i = 0; i < 5; i++) {
			jugador1.desplazar(true, 1); // true para girar a la derecha, false para girar a la izquierda
			System.out.println(jugador1);
			System.out.println(jugador2);
			// revisarColisiones(objetosInstanciados);
		}
		assertEquals(true, jugador1.getHitbox().intersecta(jugador2));

	}
}
