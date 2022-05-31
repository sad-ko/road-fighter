package road_fighter.logica;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import road_fighter.Config;
import road_fighter.entidades.Objeto;
import road_fighter.entidades.cuerpos.Cuerpo;
import road_fighter.fisica.SpatialGrid;

/**
 * La clase abstracta {@code Invocador} es el encargado de administrar todos los
 * {@code Cuerpo}s instanciados en el {@code Mapa} y revisar sus colisiones.
 */
public class Invocador {

	private static Invocador instancia = null;

	private SpatialGrid grid;
	private List<Objeto> objInstancias;
	private List<Cuerpo> instancias;

	private Invocador() {
		this.instancias = new ArrayList<>();
		this.objInstancias = new ArrayList<>();
		this.grid = new SpatialGrid(Config.width, Config.height, Config.cellSize);
	}

	public static Invocador getInstancia() {
		if (instancia == null) {
			instancia = new Invocador();
		}
		return instancia;
	}

	public void add(Cuerpo cuerpo) {
		this.instancias.add(cuerpo);
	}

	public void add(Objeto obj) {
		this.objInstancias.add(obj);
	}

	public void clear() {
		for (Cuerpo cuerpo : instancias) {
			cuerpo.remover();
		}
		instancias.clear();

		for (Objeto obj : objInstancias) {
			obj.remover();
		}
		objInstancias.clear();
	}

	/**
	 * Itera la lista de {@code Cuerpo}s revisando sus colisiones, todos con todos.
	 * Complejidad computacional de O(n²), no muy performante...
	 */
	public void OLD_calcularColisiones() {
		int size = instancias.size();

		for (int i = 0; i < size; i++) {
			Cuerpo a = instancias.get(i);
			for (int j = i + 1; j < size; j++) {
				Cuerpo b = instancias.get(j);
				a.getHitbox().intersecta(b);
			}
		}
	}

	public void calcularColisiones() {
		grid.clear();

		for (Cuerpo cuerpo : instancias) {
			grid.add(cuerpo);
		}

		grid.checkCollisions();
	}

	public void addToGroup(ObservableList<Node> groupList) {
		for (Cuerpo cuerpo : instancias) {
			if (cuerpo.getRender() != null) {
				groupList.add(cuerpo.getRender());
			}
		}

		for (Objeto obj : objInstancias) {
			if (obj.getRender() != null) {
				groupList.add(obj.getRender());
			}
		}
	}

	public void update(double delta) {
		for (Cuerpo cuerpo : instancias) {
			cuerpo.update(delta);
		}

		for (Objeto obj : objInstancias) {
			obj.update(delta);
		}
	}

	public int size() {
		return this.instancias.size();
	}
}
