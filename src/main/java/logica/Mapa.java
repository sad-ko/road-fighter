package logica;

import java.util.Random;
import entidades.AutoEstatico;
import entidades.Borde;
import entidades.PowerUp;
import fisica.Vector2D;

public class Mapa extends Spawner {

	private float longitud;
	private float limiteDerecho;
	private float limiteIzquierdo;
	private Random rand;

	public Mapa(float longitud, float limiteIzquierdo, float limiteDerecho) {
		super();
		this.longitud = longitud;
		this.limiteDerecho = limiteDerecho;
		this.limiteIzquierdo = limiteIzquierdo;

		// -1 porque Borde crea una colision 1f de ancho, de esta forma desplazamos el
		// limite izquierdo propiamente
		Borde ld = new Borde(limiteDerecho, longitud);
		Borde li = new Borde(limiteIzquierdo - 1f, longitud);

		this.instanciar(ld);
		this.instanciar(li);

		this.rand = new Random();
	}

	private Vector2D generarCoordenadas() {
		// Para que el Cuerpo no sea instanciado exactamente en el borde
		float pad = 5f;
		float ld = limiteDerecho + pad;
		float li = limiteIzquierdo - pad;
		float l = longitud - pad;

		float x = ld + rand.nextFloat() * (li - ld);
		float y = pad + rand.nextFloat() * (l - pad);

		return new Vector2D(x, y);
	}

	public void agregarObstaculos(String obstaculoClase, int cantidad) {
		switch (obstaculoClase) {
		case "AutoEstatico":
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				AutoEstatico auto = new AutoEstatico(pos);
				this.instanciar(auto);
			}
			break;

		case "PowerUp":
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				PowerUp power = new PowerUp(pos);
				this.instanciar(power);
			}
			break;
		}
	}

	public float getLimiteDerecho() {
		return limiteDerecho;
	}

	public float getLimiteIzquierdo() {
		return limiteIzquierdo;
	}

	public float getLongitud() {
		return longitud;
	}

}
