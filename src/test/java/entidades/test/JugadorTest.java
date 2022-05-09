package entidades.test;

import java.util.ArrayList;
import entidades.AutoEstatico;
import entidades.Cuerpo;
import entidades.Jugador;
import entidades.PowerUp;
import fisica.Vector2D;

public class JugadorTest {
	public static void main(String[] args) {
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
			jugador.girar(i % 2 == 0,1); //Zig Zag
			
			//Desacelera durante 50 iteraciones
			if (i < 100 || i > 150) {
				jugador.acelerar(1);
			} else {
				jugador.desacelerar(1);
			}
		}
		
		System.err.println("[END]");
	}
	
	
	public static void revisarColisiones(ArrayList<Cuerpo> objetosInstanciados) {
		int size = objetosInstanciados.size();
		
		for (int i = 0; i < size; i++) {
			Cuerpo a = objetosInstanciados.get(i);
			for (int j = i+1; j < size; j++) {
				Cuerpo b = objetosInstanciados.get(j);
				a.getHitbox().intersecta(b); 
			}
		}
	}
	
}
