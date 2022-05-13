package entidades.test;

import static org.junit.Assert.*;
import org.junit.Test;
import entidades.AutoEstatico;
import entidades.Obstaculo;
import fisica.Vector2D;
import logica.Invocador;

public class TestAutoEstatico {

	@Test
	public void testChoqueConOtroAutoEstatico() {
		AutoEstatico auto_1 = new AutoEstatico(new Vector2D(0f, 0f));
		AutoEstatico auto_2 = new AutoEstatico(new Vector2D(0f, 25f));

		Invocador invocador = new Invocador();
		invocador.instanciar(auto_1);
		invocador.instanciar(auto_2);

		while (auto_1.getPosicion().getY() < auto_2.getPosicion().getY()) {
			auto_1.mover(1);
		}

		invocador.calcularColisiones();
		assertEquals(true, auto_1.getImpacto());
	}

	@Test
	public void testExplotoAlChocarContraObjeto() {
		AutoEstatico auto = new AutoEstatico(new Vector2D(0f, 0f));
		Obstaculo obstaculo = new Obstaculo(new Vector2D(0f, 25f));

		Invocador invocador = new Invocador();
		invocador.instanciar(auto);
		invocador.instanciar(obstaculo);

		while (auto.getPosicion().getY() < obstaculo.getPosicion().getY()) {
			auto.mover(1);
		}

		invocador.calcularColisiones();
		assertEquals(true, auto.getExploto());
	}

}
