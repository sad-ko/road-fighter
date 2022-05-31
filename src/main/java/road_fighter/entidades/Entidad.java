package road_fighter.entidades;

public enum Entidad {
	AUTO_ESTATICO(35), BORDE, JUGADOR, META, OBSTACULO, POWERUP, TEXT, ESCENARIO;

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
