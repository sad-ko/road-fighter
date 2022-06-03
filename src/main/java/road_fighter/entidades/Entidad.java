package road_fighter.entidades;

public enum Entidad {
	AUTO_ESTATICO(35), BORDE, JUGADOR, META, OBSTACULO(160), POWERUP(30), TEXT, ESCENARIO;

	private double ancho;

	Entidad(double ancho) {
		this.ancho = ancho;
	}

	Entidad() {
		this.ancho = 0;
	}

	public double getAncho() {
		return ancho;
	}
}
