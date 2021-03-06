package road_fighter.fisica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import road_fighter.entidades.cuerpos.Cuerpo;

public class SpatialGrid {

	private double sceneWidth;
	private int cols;
	private int rows;
	private int cellSize;
	private Map<Integer, List<Cuerpo>> grid;

	public SpatialGrid(final double sceneWidth, final double sceneHeight, final int cellSize) {
		this.cols = (int) Math.ceil(sceneWidth / cellSize);
		this.rows = (int) Math.ceil(sceneHeight / cellSize);
		this.cellSize = cellSize;
		this.sceneWidth = sceneWidth;

		grid = new HashMap<>(rows * cols);
		for (int i = 0; i < rows * cols; i++) {
			grid.put(i, new ArrayList<>());
		}
	}

	public void clear() {
		for (List<Cuerpo> cell : grid.values()) {
			cell.clear();
		}
	}

	public void add(Cuerpo cuerpo) {
		Set<Integer> cells = findInCells(cuerpo);

		for (Integer id : cells) {
			if (grid.containsKey(id)) {
				grid.get(id).add(cuerpo);
			}
		}
	}

	public void checkCollisions() {
		for (List<Cuerpo> cell : grid.values()) {
			int size = cell.size();

			for (int i = 0; i < size; i++) {
				Cuerpo a = cell.get(i);
				for (int j = i + 1; j < size; j++) {
					Cuerpo b = cell.get(j);
					a.getHitbox().intersecta(b);
				}
			}
		}
	}

	private int capCellId(int id) {
		return (id >= cols * rows) ? id - cols : id;
	}

	private Set<Integer> findInCells(Cuerpo cuerpo) {
		Set<Integer> cells = new HashSet<>();
		int width = (int) (sceneWidth / cellSize);

		double ax = cuerpo.getPosicion().getX();
		double ay = cuerpo.getPosicion().getY();
		double bx = cuerpo.getHitbox().getTamanio().getX() + ax;
		double by = cuerpo.getHitbox().getTamanio().getY() + ay;

		// Si esta fuera de la pantalla, no se revisa su colision
		if (ax > sceneWidth || bx < 0) {
			System.out.println(cuerpo.getPosicion());
			return cells;
		}

		int id_ax = (int) Math.abs(ax / cellSize);
		int id_ay = (int) Math.abs(ay / cellSize);

		int id_bx = (int) Math.abs(bx / cellSize);
		int id_by = (int) Math.abs(by / cellSize);

		int bottomLeft = capCellId(id_ax + id_ay * width);
		int topRight = capCellId(id_bx + id_by * width);

		cells.add(bottomLeft);

		// El cuerpo ocupa una sola celda
		if (bottomLeft == topRight) {
			return cells;
		}

		int topLeft = capCellId(id_ax + id_by * width);
		int bottomRight = capCellId(id_bx + id_ay * width);

		cells.add(topRight);
		cells.add(topLeft);
		cells.add(bottomRight);

		// Hay celdas de por medio
		if (topRight - topLeft > 1) {
			int diff = bottomRight - topRight;
			for (int i = topRight - 1; i > topLeft; i--) {
				cells.add(i); // Celdas entre topLeft y topRight
				cells.add(diff + i); // Celdas entre bottomLeft y topRight
			}
		}

		// Hay celdas de por medio, entre top y bottom
		if (bottomLeft % width == topLeft) {
			int diff = bottomRight - bottomLeft;
			for (int i = bottomLeft - width; i > topLeft; i -= width) {
				cells.add(i); // Celdas entre topLeft y bottomLeft
				cells.add(diff + i); // Celdas entre topRight y bottomRight
			}
		}

		return cells;
	}
}
