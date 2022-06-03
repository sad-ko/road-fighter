package parte_uno.test;

import static org.junit.Assert.*;
import org.junit.Test;

import road_fighter.entidades.cuerpos.AutoEstatico;
import road_fighter.entidades.cuerpos.Obstaculo;
import road_fighter.fisica.Vector2D;
import road_fighter.logica.Invocador;

public class TestAutoEstatico {

	@Test
	public void testChoqueConOtroAutoEstatico() {
		AutoEstatico auto_1 = new AutoEstatico(new Vector2D(0f, 0f));
		AutoEstatico auto_2 = new AutoEstatico(new Vector2D(0f, 25f));

		Invocador invocador = Invocador.getInstancia();
		invocador.add(auto_1);
		invocador.add(auto_2);

		while (auto_1.getPosicion().getY() < auto_2.getPosicion().getY()) {
			auto_1.update(1);
		}

		invocador.calcularColisiones();
		assertEquals(true, auto_1.getImpacto());
	}

	@Test
	public void testExplotoAlChocarContraObjeto() {
		AutoEstatico auto = new AutoEstatico(new Vector2D(0f, 0f));
		Obstaculo obstaculo = new Obstaculo(new Vector2D(0f, 25f));

		Invocador invocador = Invocador.getInstancia();
		invocador.add(auto);
		invocador.add(obstaculo);

		while (auto.getPosicion().getY() < obstaculo.getPosicion().getY()) {
			auto.update(1);
		}

		invocador.calcularColisiones();
		assertEquals(true, auto.getImpacto());
	}

}
