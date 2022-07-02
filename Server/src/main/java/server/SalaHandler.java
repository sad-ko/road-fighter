package server;

import java.util.ArrayList;
import java.util.List;

import road_fighter.logica.Dificultad;
import road_fighter.networking.Comando;
import road_fighter.networking.Mensaje;
import road_fighter.networking.Sala;

public class SalaHandler extends Sala {

	private List<ClientHandler> players = new ArrayList<>();
	private GameLoop partida;

	public SalaHandler(String nombre, int cantidadMaxima, Dificultad dificultad, String owner) {
		super(nombre, cantidadMaxima, dificultad, owner);
	}

	public SalaHandler(String nombre, int cantidadMaxima, int dificultad, String owner) {
		super(nombre, cantidadMaxima, dificultad, owner);
	}

	public SalaHandler(String nombre, String owner) {
		super(nombre, owner);
	}

	public void close() {
		players.forEach(ClientHandler::sendToLobby);
		players.clear();
	}

	public boolean add(ClientHandler client) {
		if (this.cantidadActual < this.cantidadMaxima) {
			players.add(client);
			this.cantidadActual++;
			return true;
		}

		return false;
	}

	public void remove(ClientHandler client) {
		if (players.remove(client)) {
			this.cantidadActual--;
			client.sendToLobby();
		}
	}

	public Mensaje join() {
		Mensaje join = new Mensaje(Comando.UNIRSE_SALA);
		join.agregar(this.nombre);
		join.agregar(this.cantidadMaxima);
		join.agregar(this.dificultad);
		join.agregar(this.owner);
		join.agregar(this.cantidadActual);
		join.agregar(this.players.size());
		for (ClientHandler player : players) {
			join.agregar(player.getUsername());
		}
		return join;
	}

	public void sendToAll(Mensaje msg) {
		for (ClientHandler player : players) {
			player.enviar(msg);
		}
	}
	
	public void startGame() {
		partida = new GameLoop(this.dificultad, this.cantidadActual, this.players, this);
		partida.run();
	}
	
	public void movePlayer(final int id, Comando comando) {
		partida.movePlayer(id, comando);
	}

}
