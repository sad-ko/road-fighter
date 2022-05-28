package road_fighter.entidades;

import road_fighter.fisica.Vector2D;

/**
 * La clase {@code AutoEstatico} hija de {@code Auto} y es un Auto controlado
 * por el sistema que se mueve verticalmente con una velocidad constante.
 */
public class AutoEstatico extends Auto {

	/**
	 * @param posicion :{@code Vector2D} - Posicion del auto en el plano (x,y).
	 */
	public AutoEstatico(Vector2D posicion) {
		super(Entidad.AUTO_ESTATICO, posicion, "img/auto_estatico.png", new Vector2D(12f, 16f));
		this.velocidad = 25.0;
	}

	public void mover(double delta) {
		double y = this.posicion.getY() + this.velocidad * delta;
		this.posicion.setY(y);
	}

	@Override
	public void enChoque(Cuerpo cuerpo) {
		switch (cuerpo.getClase()) {
		case AUTO_ESTATICO:
			this.impacto((Auto) cuerpo);
			break;

		case BORDE:
			this.explotar();
			break;

		case JUGADOR:
			this.impacto((Auto) cuerpo);
			break;

		case OBSTACULO:
			this.explotar();
			cuerpo.remover();
			break;

		default:
			break;
		}
	}
}