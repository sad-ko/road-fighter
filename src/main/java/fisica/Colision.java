package fisica;

import entidades.Cuerpo;

/**
 * La clase {@code Colision} juzga si a intersectado con otra colision o no.
 * <p>
 * Actualmente solo se pueden crear rectangulos como colisiones.
 */
public final class Colision {

	/**
	 * Tamanio de la colision del {@code Cuerpo}, <strong>se interpreta este punto
	 * (x,y) como la esquina derecha superior de la colision, siendo la posicion del
	 * {@code Cuerpo} la esquina izquierda inferiror.</strong>
	 */
	private Vector2D tamanio;

	/**
	 * {@code Cuerpo} al cual esta colision esta vinculado.
	 */
	private Cuerpo cuerpoVinculado = null;

	/**
	 * Si activado, desactiva la capacidad de ser detectado en una interseccion.
	 */
	private boolean desactivado = false;

	/**
	 * @param tamanio :{@code Vector2D} - <strong>Indica el punto (x,y) de la
	 *                esquina derecha superior.</strong>
	 * @param padre   :{@code Cuerpo} - {@code Cuerpo} con el que esta vinculado.
	 */
	public Colision(final Vector2D tamanio, final Cuerpo padre) {
		this.tamanio = tamanio;
		this.cuerpoVinculado = padre;
	}

	/**
	 * Realiza los calculos necesarios para verificar si esta y otra colision se
	 * intersectaron.
	 * 
	 * @param p2 :{@code Vector2D} - Posicion del objeto a calcular.
	 * @param s2 :{@code Vector2D} - Tamanio del objeto a calcular.
	 * @return Si interesectaron o no.
	 */
	private boolean calcularInterseccion(Vector2D p2, Vector2D s2) {
		Vector2D p1 = this.cuerpoVinculado.getPosicion();
		// Copia solo los valores de los tamanios para no alterar sus valores
		// originales.
		float s1_x = this.tamanio.getX();
		float s1_y = this.tamanio.getY();
		float s2_x = s2.getX();
		float s2_y = s2.getY();
		// Ajusta el tamanio de las colisiones a las posiciones actuales de los cuerpos.
		s1_x += p1.getX();
		s1_y += p1.getY();
		s2_x += p2.getX();
		s2_y += p2.getY();
		return (p1.getX() < s2_x && p1.getY() < s2_y && p2.getX() < s1_x && p2.getY() < s1_y);
	}

	/**
	 * Verifica si las colisiones de ambos {@code Cuerpo}s intersectan o no. En caso
	 * de interseccion, llama a la funcion enChoque del mediador ColisionInspector.
	 * 
	 * @param otro :{@code Cuerpo} - {@code Cuerpo} con el cual deseamos verificar
	 *             su interseccion.
	 * @return Si interesecto con el {@code Cuerpo} o no.
	 */
	public boolean intersecta(Cuerpo otro) {
		Colision otroHitbox = otro.getHitbox();

		if (this.desactivado || otroHitbox.desactivado) {
			return false;
		}

		boolean intersecto = calcularInterseccion(otro.getPosicion(), otroHitbox.tamanio);
		if (intersecto && this.cuerpoVinculado != null) {
			// Mediador que administra las interacciones al colisionar.
			ColisionInspector.enChoque(this.cuerpoVinculado, otro);
		}

		return intersecto;
	}

	public boolean estaDesactivado() {
		return desactivado;
	}

	public void desactivar(boolean desactivado) {
		this.desactivado = desactivado;
	}

	@Override
	public String toString() {
		return "Tamanio: " + tamanio + ", desactivado: " + desactivado;
	}
}
