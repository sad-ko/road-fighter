package entidades.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import entidades.AutoEstatico;
import entidades.Cuerpo;
import entidades.Jugador;
import entidades.PowerUp;
import fisica.Vector2D;
import entidades.Mapa;
import entidades.Partida;

public class TestJugador {

	@Test
	public void test() {
		ArrayList<Cuerpo> objetosInstanciados = new ArrayList<Cuerpo>();

		Vector2D hitbox_size = new Vector2D(1f, 1f);

		Vector2D init_pos = new Vector2D(0f, 0f);
		Jugador jugador = new Jugador(init_pos, hitbox_size,"player");

		Vector2D pow_init_pos = new Vector2D(101f, 29299f);
		PowerUp power = new PowerUp(pow_init_pos, hitbox_size);

		Vector2D auto_init_pos = new Vector2D(-99f, 27499f);
		AutoEstatico auto = new AutoEstatico(auto_init_pos, hitbox_size);

		objetosInstanciados.add(jugador);
		objetosInstanciados.add(power);
		objetosInstanciados.add(auto);

		for (int i = 0; i <= 350; i++) {
			revisarColisiones(objetosInstanciados);

			System.out.println(jugador);
			jugador.girar(i % 2 == 0, 1); // Zig Zag

			// Desacelera durante 50 iteraciones
			if (i < 100 || i > 150) {
				jugador.acelerar(1);
			} else {
				jugador.desacelerar(1);
			}
		}

		System.err.println("[END]");

	}

	//esto lo agregue en clase Partida
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
	
	Vector2D hitbox_size = new Vector2D(1f, 1f);

	Vector2D init_pos1 = new Vector2D(0f, 0f);
	Jugador jugador1 = new Jugador(init_pos1, hitbox_size,"player1");
	
	//true para girar a la derecha, false para girar a la izquierda
	jugador1.girar(true, 1);
	jugador1.girar(true, 1);
	jugador1.girar(true, 1);
	jugador1.girar(false, 1);
	
	assertEquals(2, jugador1.getPosicion().x,0.00);
	
	}
	
	@Test
	public void caso02_ChocaOtroJugador() {

		ArrayList<Cuerpo> objetosInstanciados = new ArrayList<Cuerpo>();

		Vector2D hitbox_size = new Vector2D(1f, 1f);

		Vector2D init_pos1 = new Vector2D(0f, 0f);
		Jugador jugador1 = new Jugador(init_pos1, hitbox_size, "player1");

		Vector2D init_pos2 = new Vector2D(5f, 0f);
		Jugador jugador2 = new Jugador(init_pos2, hitbox_size, "player2");

		objetosInstanciados.add(jugador1);
		objetosInstanciados.add(jugador2);

		for (int i = 0; i < 5; i++) {
			jugador1.girar(true, 1); // true para girar a la derecha, false para girar a la izquierda
			System.out.println(jugador1);
			System.out.println(jugador2);		
			//revisarColisiones(objetosInstanciados);
		}	
		assertEquals(true, jugador1.getHitbox().intersecta(jugador2));
		
		
	}
	
	@Test
	public void caso03_posicionesIniciales() { //busca detectar error de que NO haya superposiciones de jugadores en la largada
		Partida partida = new Partida(3);
		
		partida.determinarPosiciones();
		
		for (Jugador elemento : partida.getJugadores()) {
			System.out.println(elemento.toString());	
		}
		
		for (int i = 0; i < partida.getJugadores().size(); i++) {	
			Jugador player1=partida.getJugadores().get(i);
			
			for (int j = i+1; j < partida.getJugadores().size() && i != j; j++) {
				Jugador player2=partida.getJugadores().get(j);
				System.out.println(player1.getNombre()+" choco con + "+player2.getNombre()+" ? " +player1.getHitbox().intersecta(player2));
				assertFalse(player1.getHitbox().intersecta(player2));
			}
		}
		
	}
	
	
}
