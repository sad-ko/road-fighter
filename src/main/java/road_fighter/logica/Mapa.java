package road_fighter.logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import road_fighter.Config;
import road_fighter.entidades.Entidad;
import road_fighter.entidades.Escenario;
import road_fighter.entidades.EscenarioFin;
import road_fighter.entidades.EscenarioInicio;
import road_fighter.entidades.cuerpos.AutoEstatico;
import road_fighter.entidades.cuerpos.Borde;
import road_fighter.entidades.cuerpos.Competidor;
import road_fighter.entidades.cuerpos.Jugador;
import road_fighter.entidades.cuerpos.Meta;
import road_fighter.entidades.cuerpos.Obstaculo;
import road_fighter.entidades.cuerpos.PowerUp;
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
	private List<Vector2D> spawnPoints;
	private int obs = 0;

	/**
	 * @param longitud        :{@code float} - Largo del mapa, tambien cuenta como
	 *                        ubicacion de la {@code Meta}.
	 * @param limiteIzquierdo :{@code float} - Posicion del limite izquierdo del
	 *                        mapa en el eje X.
	 * @param limiteDerecho   :{@code float} - Posicion del limite derecho del mapa
	 *                        en el eje X.
	 */
	public Mapa(double longitud, double limiteIzquierdo, double limiteDerecho) {
		this.longitud = longitud;
		this.limiteDerecho = limiteDerecho;
		this.limiteIzquierdo = limiteIzquierdo;

		Borde ld = new Borde(limiteDerecho);
		Borde li = new Borde(limiteIzquierdo - 10);

		this.invocador = Invocador.getInstancia();
		this.invocador.add(ld);
		this.invocador.add(li);

		this.invocador.add(new EscenarioInicio());
		this.invocador.add(new Escenario(longitud));
		this.invocador.add(new EscenarioFin(longitud));

		this.rand = new Random();
	}

	/**
	 * Genera una posicion (x,y) de forma alazar dentro del mapa, tiene en cuenta el
	 * espaciado para evitar generar una coordenada sobre uno de los bordes o la
	 * meta.
	 * 
	 * @return Vector2D generado aleatoriamente dentro de los limites del mapa.
	 */
	private Vector2D generarCoordenadas(double espaciado, double desde, double hasta) {
		// Para que el Cuerpo no sea instanciado exactamente en los bordes ni en la meta
		double ld = limiteDerecho - espaciado;
		double li = limiteIzquierdo + espaciado;

		// rangeMin + (rangeMax - rangeMin) * r.nextDouble()
		double x = li + (ld - li) * rand.nextDouble();
		double y = desde + (hasta - desde) * rand.nextDouble();

		return new Vector2D(x, -y);
	}

	private Vector2D generarCoordenadasObstaculo(double espaciado, double desde, double hasta) {
		// Para que el Cuerpo no sea instanciado exactamente en los bordes ni en la meta
		double ld = limiteDerecho - espaciado;

		// rangeMin + (rangeMax - rangeMin) * r.nextDouble()
		double x = (obs++ % 2 == 0) ? ld : limiteIzquierdo;
		double y = desde + (hasta - desde) * rand.nextDouble();

		return new Vector2D(x, -y);
	}

	/**
	 * Genera esparcidamente por el mapa {@code Cuerpo}s de la clase especificada.
	 * 
	 * @param obstaculoClase :{@code String} - Clase de los {@code Cuerpo}s a
	 *                       agregar en el mapa.
	 * @param cantidad       :{@code int} - Cantidad de {@code Cuerpo}s a agregar en
	 *                       el mapa.
	 */
	private void agregarObstaculos(Entidad obstaculoClase, int cantidad, double desde, double hasta) {
		Vector2D pos;

		switch (obstaculoClase) {
		case AUTO_ESTATICO:
			for (int i = 0; i < cantidad; i++) {
				do {
					pos = generarCoordenadas(AutoEstatico.getAncho(), desde, hasta);
				} while (this.spawnPoints.contains(pos));

				AutoEstatico auto = new AutoEstatico(pos);
				this.invocador.add(auto);
				this.spawnPoints.add(pos);
			}
			break;

		case POWERUP:
			for (int i = 0; i < cantidad; i++) {
				do {
					pos = generarCoordenadas(PowerUp.getAncho(), desde, hasta);
				} while (this.spawnPoints.contains(pos));

				PowerUp power = new PowerUp(pos);
				this.invocador.add(power);
				this.spawnPoints.add(pos);
			}
			break;

		case OBSTACULO:
			for (int i = 0; i < cantidad; i++) {
				do {
					pos = generarCoordenadasObstaculo(Obstaculo.getAncho(), desde, hasta);
				} while (this.spawnPoints.contains(pos));

				Obstaculo obstaculo = new Obstaculo(pos);
				this.invocador.add(obstaculo);
				this.spawnPoints.add(pos);
			}
			break;

		default:
			break;
		}
	}

	public void generarMapa(Dificultad dificultad) {
		int autosEstaticos = 0;
		int obstaculos = 0;
		int powerUps = 0;

		switch (dificultad) {
		case FACIL:
			autosEstaticos = 40;
			obstaculos = 5;
			powerUps = 20;
			Config.acceleration = 0.25;
			break;

		case NORMAL:
			autosEstaticos = 50;
			obstaculos = 10;
			powerUps = 10;
			Config.acceleration = 0.2;
			break;

		case DIFICIL:
			autosEstaticos = 75;
			obstaculos = 25;
			powerUps = 5;
			Config.acceleration = 0.1;
			break;

		default:
			break;
		}

		int chunks = (int) ((this.longitud - Config.height * 2) / Config.height);
		for (int i = 0; i < chunks; i++) {
			if (this.spawnPoints != null) {
				this.spawnPoints.clear();
			}

			this.spawnPoints = new ArrayList<>((autosEstaticos + obstaculos + powerUps) / chunks);

			double desde = Config.height * (i + 1);
			double hasta = desde + Config.height;

			int cantAutos = autosEstaticos / chunks;
			cantAutos = (cantAutos == 0) ? 1 : cantAutos;

			int cantObst = obstaculos / chunks;
			cantObst = (cantObst == 0) ? 1 : cantObst;

			int cantPowers = powerUps / chunks;
			cantPowers = (cantPowers == 0) ? 1 : cantPowers;

			this.agregarObstaculos(Entidad.AUTO_ESTATICO, cantAutos, desde, hasta);
			this.agregarObstaculos(Entidad.OBSTACULO, cantObst, desde, hasta);
			this.agregarObstaculos(Entidad.POWERUP, cantPowers, desde, hasta);
		}
	}

	public void crearMeta(Partida partidaActual) {
		Meta meta = new Meta(this.longitud, this.limiteIzquierdo, this.limiteDerecho, partidaActual);
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
	public Jugador posicionarCompetidores(Partida partidaActual, int cantJugadores) {
		// Distancias equidistantes entre los autos y los bordes.
		double pad = (this.limiteDerecho - this.limiteIzquierdo) / (cantJugadores + 1);
		double x = pad;

		Vector2D pos = new Vector2D(x + this.limiteIzquierdo, Config.height - Config.height / 4);

		Jugador jugador = new Jugador(pos, "Jugador Nro: 1");
		this.invocador.add(jugador);
		partidaActual.agregarCompetidor(jugador);

		for (int i = 1; i < cantJugadores; i++) {
			x = x + pad;
			pos.setX(x + this.limiteIzquierdo);

			Competidor comp = new Competidor(pos, "Competidor Nro: " + (i + 1));
			this.invocador.add(comp);
			partidaActual.agregarCompetidor(comp);

			// TODO: Usar un nombre especificado por cada jugador.
		}

		return jugador;
	}
}
