package fisica;

import entidades.AutoEstatico;
import entidades.Cuerpo;
import entidades.Jugador;
import entidades.PowerUp;

/**
 * La interfaz {@code ColisionInspector} actua como mediador en la interaccion
 * de las distintas clases de {@code Cuerpo}. Cada interaccion entre dos
 * {@code Cuerpo}s debe ser propiamente definida en esta interfaz
 */
public interface ColisionInspector {

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
		case "Jugador::PowerUp":
			jugador = (Jugador) c1;
			powerUp = (PowerUp) c2;

			jugador.setVelocidad(jugador.getVelocidad() * powerUp.getPowerUp());
			powerUp.timeout(jugador);
			powerUp.removerObjeto();

			System.err.println("POWER UP!!!");
			break;

		case "AutoEstatico::Jugador":
			autoEst = (AutoEstatico) c1;
			jugador = (Jugador) c2;

			jugador.impacto(autoEst);
			break;
		
		case "Jugador::Obstaculo":
			break;
		}
	}
}
