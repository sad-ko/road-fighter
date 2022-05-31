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
	private double sceneHeight;
	private int cols;
	private int rows;
	private int cellSize;
	private Map<Integer, List<Cuerpo>> grid;

	public SpatialGrid(final double sceneWidth, final double sceneHeight, final int cellSize) {
		this.cols = (int) Math.ceil(sceneWidth / cellSize);
		this.rows = (int) Math.ceil(sceneHeight / cellSize);
		this.cellSize = cellSize;
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;

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
			grid.get(id).add(cuerpo);
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

	private Set<Integer> findInCells(Cuerpo cuerpo) {
		Set<Integer> cells = new HashSet<>();
		int width = (int) (sceneWidth / cellSize);

		double ax = cuerpo.getPosicion().getX();
		double ay = cuerpo.getPosicion().getY();
		double bx = cuerpo.getHitbox().getTamanio().getX() + ax;
		double by = cuerpo.getHitbox().getTamanio().getY() + ay;

		if (ax > sceneWidth || ay > sceneHeight || bx < 0 || by < 0) {
			return cells;
		}

		int id_ax = (int) (ax / cellSize);
		int id_ay = (int) (ay / cellSize);

		int id_bx = (int) (bx / cellSize);
		int id_by = (int) (by / cellSize);

		int bottomLeft = id_ax + id_ay * width;
		int topRight = id_bx + id_by * width;

		bottomLeft = (bottomLeft >= cols * rows) ? bottomLeft - width : bottomLeft;
		topRight = (topRight >= cols * rows) ? topRight - width : topRight;

		cells.add(bottomLeft);

		// El cuerpo ocupa una sola celda
		if (bottomLeft == topRight) {
			return cells;
		}

		int topLeft = id_ax + id_by * width;
		int bottomRight = id_bx + id_ay * width;

		topLeft = (topLeft >= cols * rows) ? topLeft - width : topLeft;
		bottomRight = (bottomRight >= cols * rows) ? bottomRight - width : bottomRight;

		cells.add(topRight);
		cells.add(topLeft);
		cells.add(bottomRight);

		if (topRight - topLeft > 1) {
			for (int i = topRight - 1; i > topLeft; i--) {
				cells.add(i);
			}

			for (int i = bottomLeft + 1; i < bottomRight; i++) {
				cells.add(i);
			}
		}

		if (bottomLeft % width == topLeft) {
			for (int i = bottomLeft - width; i > topLeft; i -= width) {
				cells.add(i);
			}

			for (int i = bottomRight - width; i > topRight; i -= width) {
				cells.add(i);
			}
		}

		return cells;
	}
}
