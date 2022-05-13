package logica;

import java.util.Random;
import entidades.AutoEstatico;
import entidades.Borde;
import entidades.Cuerpo;
import entidades.Meta;
import entidades.Obstaculo;
import entidades.PowerUp;
import fisica.Vector2D;

/**
 * La clase {@code Mapa} es el encargado de definir los limites, meta, generar
 * obstaculos, jugadores y asignarles una propia ubicacion en el mapa.
 */
public class Mapa {

	private float longitud;
	private float limiteDerecho;
	private float limiteIzquierdo;
	private Invocador invocador;
	private Random rand;

	/**
	 * @param longitud        :{@code float} - Largo del mapa, tambien cuenta como
	 *                        ubicacion de la {@code Meta}.
	 * @param limiteIzquierdo :{@code float} - Posicion del limite izquierdo del
	 *                        mapa en el eje X.
	 * @param limiteDerecho   :{@code float} - Posicion del limite derecho del mapa
	 *                        en el eje X.
	 */
	public Mapa(float longitud, float limiteIzquierdo, float limiteDerecho) {
		super();
		this.longitud = longitud;
		this.limiteDerecho = limiteDerecho;
		this.limiteIzquierdo = limiteIzquierdo;

		// -1 porque Borde crea una colision 1f de ancho, de esta forma desplazamos el
		// limite izquierdo propiamente
		Borde ld = new Borde(limiteDerecho, longitud);
		Borde li = new Borde(limiteIzquierdo - 1f, longitud);

		this.invocador = new Invocador();
		this.invocador.instanciar(ld);
		this.invocador.instanciar(li);

		this.rand = new Random();
	}

	/**
	 * Genera una posicion (x,y) de forma alazar dentro del mapa, tiene en cuenta el
	 * espaciado para evitar generar una coordenada sobre uno de los bordes o la
	 * meta.
	 * 
	 * @return Vector2D generado aleatoriamente dentro de los limites del mapa.
	 */
	private Vector2D generarCoordenadas() {
		// Para que el Cuerpo no sea instanciado exactamente en los bordes ni en la meta
		float espaciado = 5f;
		float ld = limiteDerecho + espaciado;
		float li = limiteIzquierdo - espaciado;
		float l = longitud - espaciado;

		float x = ld + rand.nextFloat() * (li - ld);
		float y = espaciado + rand.nextFloat() * (l - espaciado);

		return new Vector2D(x, y);
	}

	/**
	 * Genera esparcidamente por el mapa {@code Cuerpo}s de la clase especificada.
	 * 
	 * @param obstaculoClase :{@code String} - Clase de los {@code Cuerpo}s a
	 *                       agregar en el mapa.
	 * @param cantidad       :{@code int} - Cantidad de {@code Cuerpo}s a agregar en
	 *                       el mapa.
	 */
	public void agregarObstaculos(String obstaculoClase, int cantidad) {
		switch (obstaculoClase) {
		case "AutoEstatico":
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				AutoEstatico auto = new AutoEstatico(pos);
				this.invocador.instanciar(auto);
			}
			break;

		case "PowerUp":
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				PowerUp power = new PowerUp(pos);
				this.invocador.instanciar(power);
			}
			break;

		case "Obstaculo":
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				Obstaculo obstaculo = new Obstaculo(pos);
				this.invocador.instanciar(obstaculo);
			}
			break;
		}
	}

	public void crearMeta(Partida partidaActual) {
		Meta meta = new Meta(this.longitud, this.limiteDerecho, partidaActual);
		this.invocador.instanciar(meta);
	}

	/**
	 * Genera la cantidad especificada de {@code Jugador}es para la partida, de
	 * forma equidistante en el eje X.
	 * <p>
	 * Agregandolos tanto en la lista de {@code Cuerpo}s instanciados como en la
	 * lista de {@code Posicion}es perteneciente a la partida actual.
	 * 
	 * @param partidaActual :{@code Partida} - Partida actual donde se generan los
	 *                      jugadores.
	 * @param cantJugadores :{@code int} - Cantidad de jugadores a generar.
	 */
	public void agregarJugadores(Partida partidaActual, int cantJugadores) {
		// Me aseguro que las distancias entre los autos y tambien con el borde del
		// mapa, sean siempre equidistantes entre si.
		float incrementoPosicion = (this.limiteDerecho - this.limiteIzquierdo) / (cantJugadores + 1);
		float posicionEjeXAuto = 0;

		for (int i = 0; i < cantJugadores; i++) {
			posicionEjeXAuto += incrementoPosicion;
			partidaActual.agregarJugador(posicionEjeXAuto, "Jugador Nro: " + (i + 1));
			// TODO: Usar un nombre especificado por cada jugador.
		}
	}

	public void instanciar(Cuerpo cuerpo) {
		this.invocador.instanciar(cuerpo);
	}

	public Invocador getInvocador() {
		return invocador;
	}

}
