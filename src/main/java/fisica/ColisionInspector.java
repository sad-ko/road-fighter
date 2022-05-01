package fisica;

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
		String caso = c1.getClase() + "::" + c2.getClase();

		switch (caso) {
		case "Jugador::PowerUp":
			Jugador jugador = (Jugador) c1;
			PowerUp power = (PowerUp) c2;
			
			jugador.setVelocidad(jugador.getVelocidad() * power.getPowerUp());
			power.timeout(jugador); //Tiempo limite del powerUp
			
			System.err.println("POWER UP!!!");
			break;

		case "Jugador::AutoEstatico":
			System.err.println(c1.getClase() + " choco con " + c2.getClase());
			break;
		}
	}
}
