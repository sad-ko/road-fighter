package road_fighter.graficos;

public abstract class Audio {

	protected double volumen;

	protected Audio() {
		this.volumen = 0.0;
	}

	public void subirVolumenSound() {
		double incremento = this.volumen + 0.1;

		if (incremento < 1) {
			this.volumen = incremento;
		} else {
			this.volumen = 1;
		}
	}

	public void bajarVolumenSound() {
		double decremento = this.volumen - 0.1;

		if (decremento > 0) {
			this.volumen = decremento;
		} else {
			this.volumen = 0;
		}
	}

	public double getVolumen() {
		return volumen;
	}

}
