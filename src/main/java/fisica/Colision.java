package fisica;

import entidades.Cuerpo;

/**
 * La clase {@code Colision} juzga si a intersectado con otra colision o no.
 * <p>
 * Actualmente solo se pueden crear rectangulos como colisiones.
 */
public final class Colision {

	/**
	 * Tamaño de la colision del {@code Cuerpo}, <strong>se interpreta al punto
	 * (x,y) como la esquina derecha superior de la colision, siendo la posicion del
	 * {@code Cuerpo} la esquina izquierda inferiror.</strong>
	 */
	private Vector2D tamaño;

	/**
	 * {@code Cuerpo} al cual esta colision esta vinculado.
	 */
	private Cuerpo cuerpoVinculado = null;

	/**
	 * Si activado, desactiva la capacidad de ser detectado en una interseccion.
	 */
	private boolean desactivado = false;

	/**
	 * @param tamaño :{@code Vector2D} - <strong>Indica el punto (x,y) de la esquina
	 *               derecha superior.</strong>
	 * @param padre  :{@code Cuerpo} - {@code Cuerpo} con el que esta vinculado.
	 */
	public Colision(final Vector2D tamaño, final Cuerpo padre) {
		this.tamaño = tamaño;
		this.cuerpoVinculado = padre;
	}

	/**
	 * Realiza los calculos necesarios para verificar si esta y otra colision se
	 * intersectaron.
	 * 
	 * @param p2 :{@code Vector2D} - Posicion del objeto a calcular.
	 * @param s2 :{@code Vector2D} - Tamaño del objeto a calcular.
	 * @return Si interesecto con el objeto o no.
	 */
	private boolean calcularInterseccion(Vector2D p2, Vector2D s2) {
		Vector2D p1 = this.cuerpoVinculado.getPosicion();
		// Copia solo los valores de los tamaños para no alterar sus valores originales.
		float s1_x = this.tamaño.x;
		float s1_y = this.tamaño.y;
		float s2_x = s2.x;
		float s2_y = s2.y;
		// Ajusta el tamaño de las colisiones a las posiciones actuales de los cuerpos.
		s1_x += p1.x;
		s1_y += p1.y;
		s2_x += p2.x;
		s2_y += p2.y;
		return (p1.x < s2_x && p1.y < s2_y && p2.x < s1_x && p2.y < s1_y);
	}

	/**
	 * Verifica si las colisiones de ambos {@code Cuerpo}s intersectan o no.
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

		boolean intersecto = calcularInterseccion(otro.getPosicion(), otroHitbox.tamaño);
		if (intersecto) {
			if (this.cuerpoVinculado != null) {
				// Mediador que administra las interacciones al colisionar.
				ColisionInspector.enChoque(this.cuerpoVinculado, otro);
			}
		}

		return intersecto;
	}

	public boolean estaDesactivado() {
		return desactivado;
	}

	public void desactivar(boolean desactivado) {
		this.desactivado = desactivado;
	}
}
