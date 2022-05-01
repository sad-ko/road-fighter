package fisica;

public final class Vector2D {
	public float x;
	public float y;
	
	public Vector2D(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	public void cambiarCoordenadas(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
    public double distancia(float x, float y) {
        float _x = x - this.x;
        float _y = y - this.y;
        return Math.sqrt(_x * _x + _y * _y);
    }

    public double distancia(Vector2D vec) {
        float _x = vec.x - this.x;
        float _y = vec.y - this.y;
        return Math.sqrt(_x * _x + _y * _y);
    }

	@Override
	public String toString() {
		return String.format("(%9.1f, %9.1f)", x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
}
