package road_fighter.logica;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
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
	private Group root;
	private List<Objeto> instancias;
	private List<Cuerpo> colisionables;
	private List<Objeto> toRemove;
	private boolean inUpdate = false;

	private Invocador() {
		this.instancias = new ArrayList<>();
		this.colisionables = new ArrayList<>();
		this.toRemove = new ArrayList<>();
		this.grid = new SpatialGrid(Config.width, Config.mapaLength + Config.height, Config.cellSize);
	}

	public static Invocador getInstancia() {
		if (instancia == null) {
			instancia = new Invocador();
		}
		return instancia;
	}

	public void setRoot(Group root) {
		if (this.root == null) {
			this.root = root;
		}
	}

	private void addToRoot(Objeto obj) {
		if (obj.getRender() != null) {
			this.root.getChildren().add(obj.getRender());
		}
	}

	public void add(Cuerpo cuerpo) {
		this.add((Objeto) cuerpo);
		colisionables.add(cuerpo);
	}

	public void add(Objeto obj) {
		this.instancias.add(obj);
		this.addToRoot(obj);
	}

	public void remove(Objeto obj) {
		if (!this.inUpdate) {
			instancias.remove(obj);
			root.getChildren().remove(obj.getRender());

			if (obj.getClass() == Cuerpo.class) {
				colisionables.remove(obj);
			}
		} else {
			this.toRemove.add(obj);
		}
	}

	public void clear() {
		root.getChildren().clear();
		root = null;
		instancias.clear();
		colisionables.clear();
	}

	public void removePendings() {
		this.inUpdate = false;
		for (Objeto objeto : toRemove) {
			this.remove(objeto);
		}
		this.toRemove.clear();
	}

	public void update(double delta) {
		this.inUpdate = true;

		for (Objeto obj : instancias) {
			obj.update(delta);
		}
	}

	/**
	 * Itera la lista de {@code Cuerpo}s revisando sus colisiones, todos con todos.
	 * Complejidad computacional de O(n²), no muy performante...
	 */
	public void OLD_calcularColisiones() {
		int size = colisionables.size();

		for (int i = 0; i < size; i++) {
			Cuerpo a = colisionables.get(i);
			for (int j = i + 1; j < size; j++) {
				Cuerpo b = colisionables.get(j);
				a.getHitbox().intersecta(b);
			}
		}
	}

	public void calcularColisiones() {
		grid.clear();

		for (Cuerpo cuerpo : colisionables) {
			grid.add(cuerpo);
		}

		grid.checkCollisions();
	}

	public int size() {
		return this.instancias.size();
	}
}
