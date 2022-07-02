package server;

import java.util.ArrayList;
import java.util.List;

import road_fighter.Config;
import road_fighter.entidades.cuerpos.Competidor;
import road_fighter.logica.Dificultad;
import road_fighter.logica.Invocador;
import road_fighter.logica.Mapa;
import road_fighter.logica.Partida;
import road_fighter.networking.Comando;
import road_fighter.networking.Mensaje;

public class GameLoop {

	private static final double MS_PER_FRAME = 1_000_000_000.0 / 60;

	private Thread gameThread;
	private boolean status = false;
	private long previousNanoFrame;
	private long elapsedTime;
	private Dificultad dificultad;
	private int cantJugadores;
	private Partida partida;
	private List<ClientHandler> players;
	private List<Competidor> competidores;
	private Comando[] compandoPendiente;
	private SalaHandler sala;

	public GameLoop(Dificultad dificultad, int cantJugadores, List<ClientHandler> players, SalaHandler sala) {
		this.dificultad = dificultad;
		this.cantJugadores = cantJugadores;
		this.players = players;
		this.compandoPendiente = new Comando[cantJugadores];
		this.sala = sala;

		for (int i = 0; i < compandoPendiente.length; i++) {
			compandoPendiente[i] = Comando.HACER_NADA;
		}
	}

	public void run() {
		status = true;
		gameThread = new Thread(this::processGameLoop);
		gameThread.start();
	}

	public void stop() {
		status = false;
		if (gameThread != null) {
			gameThread.interrupt();
		}
	}

	private void processGameLoop() {
		init();
		previousNanoFrame = System.nanoTime();
		double lag = 0.0;
		while (this.status) {
			long currentTime = System.nanoTime();
			elapsedTime = currentTime - previousNanoFrame;
			lag += elapsedTime;
			previousNanoFrame = currentTime;

			while (lag >= MS_PER_FRAME) {
				update(elapsedTime * 0.001);
				lag -= MS_PER_FRAME;
			}

		}
	}

	private void update(double delta) {
		Invocador.getInstancia().update(delta);
//		Invocador.getInstancia().calcularColisiones();
//
//		if (!Invocador.getInstancia().cuerposPendientes.isEmpty()) {
//			Mensaje msg = new Mensaje(Comando.COLISION);
//			for (Integer cuerpoID : Invocador.getInstancia().cuerposPendientes) {
//				msg.agregar(cuerpoID);
//			}
//			sala.sendToAll(msg);
//			Invocador.getInstancia().cuerposPendientes.clear();
//		}

		for (int i = 0; i < compandoPendiente.length; i++) {
			switch (compandoPendiente[i]) {
			case HACER_NADA:
				break;

			case ACELERAR:
				Competidor cmp = this.partida.getCompetidor(i);
				cmp.acelerar();

				Mensaje msg = new Mensaje(Comando.ACELERAR);
				msg.agregar(i);

				sala.sendToAll(msg);

				compandoPendiente[i] = Comando.HACER_NADA;
				break;

			case DESACELERAR:
				cmp = this.partida.getCompetidor(i);
				cmp.acelerar();

				msg = new Mensaje(Comando.DESACELERAR);
				msg.agregar(i);
				msg.agregar(cmp.getPosicion());
				sala.sendToAll(msg);

				compandoPendiente[i] = Comando.HACER_NADA;
				break;

			case DESPLAZAR_IZQUIERDA:
				this.partida.getCompetidor(i).desplazar(Comando.DESPLAZAR_IZQUIERDA);

				msg = new Mensaje(Comando.DESPLAZAR_IZQUIERDA);
				msg.agregar(i);
				sala.sendToAll(msg);

				compandoPendiente[i] = Comando.HACER_NADA;
				break;

			case DESPLAZAR_DERECHA:
				this.partida.getCompetidor(i).desplazar(Comando.DESPLAZAR_DERECHA);

				msg = new Mensaje(Comando.DESPLAZAR_DERECHA);
				msg.agregar(i);
				sala.sendToAll(msg);

				compandoPendiente[i] = Comando.HACER_NADA;
				break;

			default:
				break;
			}
		}

		partida.debug();
	}

	private void init() {
		List<String> playersNames = new ArrayList<>();
		for (ClientHandler player : players) {
			playersNames.add(player.getUsername());
		}

		Mapa mapa = new Mapa(Config.mapaLength, Config.mapLeft, Config.mapRight, System.currentTimeMillis(), false);
		mapa.generarMapa(this.dificultad);

		this.partida = new Partida(mapa, 2000L);
		this.partida.comenzar(this.cantJugadores, playersNames);

		competidores = this.partida.getCompetidores();

		for (int i = 0; i < players.size(); i++) {
			Competidor competidor = competidores.get(i);
			Mensaje msg = new Mensaje(Comando.COMENZAR_PARTIDA);
			msg.agregar(competidor.getId());
			msg.agregar(mapa.getSeed());
			players.get(i).enviar(msg);
		}
	}

	public void movePlayer(final int id, Comando comando) {
		this.compandoPendiente[id] = comando;
	}

}
