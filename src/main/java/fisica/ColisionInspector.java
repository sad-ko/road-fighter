package fisica;

import entidades.AutoEstatico;
import entidades.Cuerpo;
import entidades.Jugador;
import entidades.Meta;
import entidades.Obstaculo;
import entidades.PowerUp;

/**
 * La clase {@code ColisionInspector} actua como mediador en la interaccion
 * de las distintas clases de {@code Cuerpo} al chocar. Cada interaccion entre
 * dos {@code Cuerpo}s debe ser propiamente definida en esta clase.
 */
public final class ColisionInspector {

	/**
	 * Esta clase no puede ser instanciada.
	 */
	private ColisionInspector() {}

	/**
	 * Funcion estatica que gestiona como deben interactuar los dos cuerpos
	 * colisionados.
	 * 
	 * @param c1 :{@code Cuerpo} - Primer cuerpo de la colision
	 * @param c2 :{@code Cuerpo} - Segundo cuerpo de la colision
	 */
	public static void enChoque(Cuerpo c1, Cuerpo c2) {
		String claseC1 = c1.getClase();
		String claseC2 = c2.getClase();
		String caso = null;

		// Ordenamos los cuerpos por el nombre de su clase alfabeticamente.
		if (claseC1.compareTo(claseC2) < 0) {
			caso = claseC1 + "::" + claseC2;
		} else {
			caso = claseC2 + "::" + claseC1;
			Cuerpo temp = c1;
			c1 = c2;
			c2 = temp;
		}

		Jugador jugador = null;
		PowerUp powerUp = null;
		AutoEstatico autoEst = null;
		

		switch (caso) {
		case "AutoEstatico::AutoEstatico":
			autoEst = (AutoEstatico) c1;
			AutoEstatico autoEst_2 = (AutoEstatico) c2;

			autoEst.impacto(autoEst_2);
			break;

		case "AutoEstatico::Borde":
			autoEst = (AutoEstatico) c1;
			autoEst.explotar();
			break;

		case "AutoEstatico::Jugador":
			autoEst = (AutoEstatico) c1;
			jugador = (Jugador) c2;

			jugador.impacto(autoEst);
			break;

		case "AutoEstatico::Obstaculo":
			break;

		case "Borde::Jugador":
			jugador = (Jugador) c2;
			jugador.explotar();
			break;

		case "Jugador::Jugador":
			jugador = (Jugador) c1;
			Jugador jugador_2 = (Jugador) c2;

			jugador.impacto(jugador_2);
			break;

		case "Jugador::Meta":
			jugador = (Jugador) c1;
			Meta meta = (Meta) c2;

			meta.partidaActual.setGanador(jugador);
			meta.partidaActual.iniciarEspera();

			break;

		case "Jugador::PowerUp":
			jugador = (Jugador) c1;
			powerUp = (PowerUp) c2;

			jugador.setVelocidad(jugador.getVelocidad() * powerUp.getPowerUp());
			powerUp.timeout(jugador);
			powerUp.removerObjeto();

			System.err.println("POWER UP!!!");
			break;

		case "Jugador::Obstaculo":
			jugador = (Jugador) c1;
			Obstaculo obstaculo = (Obstaculo) c2;
			jugador.explotar();
			obstaculo.removerObjeto();
			break;
		}
	}
	
}
