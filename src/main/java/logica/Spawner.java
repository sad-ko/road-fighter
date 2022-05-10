package logica;

import java.util.ArrayList;
import java.util.List;
import entidades.Cuerpo;

public abstract class Spawner {

	protected List<Cuerpo> cuerposInstanciados;

	protected Spawner() {
		this.cuerposInstanciados = new ArrayList<>();
	}

	public void instanciar(Cuerpo cuerpo) {
		this.cuerposInstanciados.add(cuerpo);
	}

	public int size() {
		return this.cuerposInstanciados.size();
	}

	public List<Cuerpo> getCuerposInstanciados() {
		return cuerposInstanciados;
	}

	public void calcularColisiones() {
		int size = cuerposInstanciados.size();

		for (int i = 0; i < size; i++) {
			Cuerpo a = cuerposInstanciados.get(i);
			for (int j = i + 1; j < size; j++) {
				Cuerpo b = cuerposInstanciados.get(j);
				a.getHitbox().intersecta(b);
			}
		}
	}
}
