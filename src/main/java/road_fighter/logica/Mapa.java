package road_fighter.logica;

import java.util.Random;

import road_fighter.entidades.AutoEstatico;
import road_fighter.entidades.Borde;
import road_fighter.entidades.Entidad;
import road_fighter.entidades.Jugador;
import road_fighter.entidades.Meta;
import road_fighter.entidades.Obstaculo;
import road_fighter.entidades.PowerUp;
import road_fighter.fisica.Vector2D;

/**
 * La clase {@code Mapa} es el encargado de definir los limites, meta, generar
 * obstaculos, jugadores y asignarles una propia ubicacion en el mapa.
 */
public class Mapa {

	private double longitud;
	private double limiteDerecho;
	private double limiteIzquierdo;
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
	public Mapa(double longitud, double limiteIzquierdo, double limiteDerecho) {
		super();
		this.longitud = longitud;
		this.limiteDerecho = limiteDerecho;
		this.limiteIzquierdo = limiteIzquierdo;

		// -1 porque Borde crea una colision 1f de ancho, de esta forma desplazamos el
		// limite izquierdo propiamente
		Borde ld = new Borde(limiteDerecho, longitud);
		Borde li = new Borde(limiteIzquierdo - 1, longitud);

		this.invocador = Invocador.getInstancia();
		this.invocador.add(ld);
		this.invocador.add(li);

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
		double espaciado = 5;
		double ld = limiteDerecho + espaciado;
		double li = limiteIzquierdo - espaciado;
		double l = longitud - espaciado;

		double x = ld + rand.nextFloat() * (li - ld);
		double y = espaciado + rand.nextFloat() * (l - espaciado);

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
	public void agregarObstaculos(Entidad obstaculoClase, int cantidad) {
		switch (obstaculoClase) {
		case AUTO_ESTATICO:
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				AutoEstatico auto = new AutoEstatico(pos);
				this.invocador.add(auto);
			}
			break;

		case POWERUP:
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				PowerUp power = new PowerUp(pos);
				this.invocador.add(power);
			}
			break;

		case OBSTACULO:
			for (int i = 0; i < cantidad; i++) {
				Vector2D pos = generarCoordenadas();
				Obstaculo obstaculo = new Obstaculo(pos);
				this.invocador.add(obstaculo);
			}
			break;

		default:
			break;
		}
	}

	public void crearMeta(Partida partidaActual) {
		Meta meta = new Meta(this.longitud, this.limiteDerecho, partidaActual);
		this.invocador.add(meta);
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
		double incrementoPosicion = this.limiteIzquierdo
				+ ((this.limiteDerecho - this.limiteIzquierdo) / (cantJugadores + 1));
		double posicionEjeXAuto = 0;

		for (int i = 0; i < cantJugadores; i++) {
			posicionEjeXAuto += incrementoPosicion;
			Jugador jugador = new Jugador(new Vector2D(posicionEjeXAuto, this.longitud), "Jugador Nro: " + (i + 1));
			partidaActual.agregarJugador(jugador);
			// TODO: Usar un nombre especificado por cada jugador.
		}
	}
}
