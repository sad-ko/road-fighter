package road_fighter.fisica;

public final class Vector2D {

	private double x;
	private double y;

	public Vector2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(final Vector2D vector) {
		this.x = vector.x;
		this.y = vector.y;
	}

	public double distancia(double x, double y) {
		double _x = x - this.x;
		double _y = y - this.y;
		return Math.sqrt(_x * _x + _y * _y);
	}

	public double distancia(Vector2D vec) {
		double _x = vec.x - this.x;
		double _y = vec.y - this.y;
		return Math.sqrt(_x * _x + _y * _y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public static Vector2D parseVector(String vector) {
		String[] values = vector.split(";");
		double _x = Double.parseDouble(values[0]);
		double _y = Double.parseDouble(values[1]);
		return new Vector2D(_x, _y);
	}

	@Override
	public String toString() {
		return String.format("%9.1f;%9.1f", x, y);
	}
}
