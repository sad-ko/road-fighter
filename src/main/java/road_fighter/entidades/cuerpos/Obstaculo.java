package road_fighter.entidades.cuerpos;

import road_fighter.entidades.Entidad;
import road_fighter.fisica.Vector2D;

public class Obstaculo extends Colisionables {

	public Obstaculo(Vector2D posicion) {
		super(Entidad.OBSTACULO, posicion, new Vector2D(1f, 1f));
	}

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}

}
