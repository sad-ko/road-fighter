package road_fighter.entidades.cuerpos;

import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;
import road_fighter.logica.Invocador;

/**
 * La clase {@code AutoEstatico} hija de {@code Auto} y es un Auto controlado
 * por el sistema que se mueve verticalmente con una velocidad constante.
 */
public class AutoEstatico extends Auto {

	/**
	 * @param posicion :{@code Vector2D} - Posicion del auto en el plano (x,y).
	 */
	public AutoEstatico(Vector2D posicion) {
		super(Entidad.AUTO_ESTATICO, posicion, "img/auto_estatico.png", new Vector2D(12, 16));
		this.velocidad = 2.5;
	}

	@Override
	protected void mover(double delta) {
		double y = (Config.currentVelocity > 0.0) ? (Config.currentVelocity * delta / Config.acceleration) : 0.0;
		this.posicion.setY((this.posicion.getY() - this.velocidad) + y);

		/*if (this.posicion.getY() < Meta.yPos() + 16 * SIZE) {
			this.remover();
		}*/
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
			
		case META:
			this.remover();
			break;

		default:
			break;
		}
	}

	public static int getAncho() {
		return SIZE * 12;
	}

	@Override
	public void remover() {
		Invocador.getInstancia().remove(this);
	}

}
