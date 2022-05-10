package logica;

import java.util.ArrayList;
import java.util.List;
import entidades.Cuerpo;

/**
 * La clase abstracta {@code Invocador} es el encargado de administrar todos los
 * {@code Cuerpo}s instanciados en el {@code Mapa} y revisar sus colisiones.
 */
public abstract class Invocador {

	protected List<Cuerpo> instancias;

	protected Invocador() {
		this.instancias = new ArrayList<>();
	}

	public void instanciar(Cuerpo cuerpo) {
		this.instancias.add(cuerpo);
	}

	/**
	 * Itera la lista de {@code Cuerpo}s revisando sus colisiones, todos con todos.
	 * Complejidad computacional de O(n²), no muy performante...
	 */
	public void calcularColisiones() {
		int size = instancias.size();

		for (int i = 0; i < size; i++) {
			Cuerpo a = instancias.get(i);
			for (int j = i + 1; j < size; j++) {
				Cuerpo b = instancias.get(j);
				a.getHitbox().intersecta(b);
			}
		}
	}

	public int size() {
		return this.instancias.size();
	}

	public List<Cuerpo> getCuerposInstanciados() {
		return instancias;
	}
}
