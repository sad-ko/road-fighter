package road_fighter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import road_fighter.escenas.SceneHandler;
import road_fighter.networking.Comando;
import road_fighter.networking.Comunicador;
import road_fighter.networking.Mensaje;
import road_fighter.networking.Sala;

public class Client implements Comunicador {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String username;
	@SuppressWarnings("unused")
	private SceneHandler currentScene;
	private List<Sala> salas;
	private Sala currentSala;
	private List<String> playersInSala;

	private boolean z;
	private boolean right;
	private boolean left;

	public int id;
	public long seed;

	public Comando comandoPendiente;
	public String[] dataPendiente;
	public int[] colisionesPendientes;

	public Client(String ip, int port, String username, SceneHandler currentScene) throws IOException {
		this.socket = new Socket(ip, port);
		this.username = username;
		this.currentScene = currentScene;
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		this.out.writeUTF(username);
	}

	public void listen() {
		Thread listener = new Thread(() -> {
			String msg;
			while (socket.isConnected()) {
				try {
					msg = in.readUTF();
					accion(Mensaje.decodificar(msg));
				} catch (IOException e) {
					close();
				}
			}
		});

		listener.start();
	}

	@Override
	public void enviar(Mensaje msg) {
		try {
			this.out.writeUTF(msg.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void accion(String[] comandos) {
		Comando comando = Comando.valueOf(comandos[0]);

		switch (comando) {
		case COLISION:
			colisionesPendientes = new int[comandos.length - 1];

			for (int i = 0; i < colisionesPendientes.length; i++) {
				colisionesPendientes[i] = Integer.parseInt(comandos[1+i]);
			}

			break;

		case OBTENER_SALAS:
			int cantSalas = Integer.parseInt(comandos[1]);
			this.salas = new ArrayList<>(cantSalas);

			for (int i = 0, j = 0; j < cantSalas; i += 5, j++) {
				Sala sala = Mensaje.paresarSala(comandos[2 + i], comandos[3 + i], comandos[4 + i], comandos[5 + i],
						comandos[6 + i]);
				this.salas.add(sala);
			}
			break;

		case AL_LOBBY:
			break;

		case UNIRSE_SALA:
			this.currentSala = Mensaje.paresarSala(comandos[1], comandos[2], comandos[3], comandos[4], comandos[5]);

			int size = Integer.parseInt(comandos[6]);
			this.playersInSala = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				this.playersInSala.add(comandos[7 + i]);
			}

			break;

		case COMENZAR_PARTIDA:
			this.id = Integer.parseInt(comandos[1]);
			this.seed = Long.parseLong(comandos[2]);
			break;

		case ACELERAR:
		case DESACELERAR:
		case DESPLAZAR_IZQUIERDA:
		case DESPLAZAR_DERECHA:
			this.dataPendiente = comandos;
			break;

		default:
			break;
		}

		comandoPendiente = comando;
		notifyAll();
	}

	public synchronized List<String> getSalasNames() {
		Mensaje msg = new Mensaje(Comando.OBTENER_SALAS);
		enviar(msg);

		try {
			wait();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		List<String> salasNames = new ArrayList<>();
		for (Sala sala : salas) {
			salasNames.add(sala.toString());
		}

		return salasNames;
	}

	public boolean isOwnerOf(String salaName) {
		for (Sala sala : salas) {
			if (sala.getNombre().equalsIgnoreCase(salaName) && sala.getOwner().equals(username)) {
				return true;
			}
		}
		return false;
	}

	private void acelerar() {
		if (!z) {
			return;
		}

		Mensaje msg = new Mensaje(Comando.ACELERAR);
		msg.agregar(this.id);
		enviar(msg);
	}

	private void desacelerar() {
		if (z) {
			return;
		}

		Mensaje msg = new Mensaje(Comando.DESACELERAR);
		msg.agregar(this.id);
		enviar(msg);
	}

	private void desplazar() {
		if (!right && !left) {
			return;
		}

		Comando comando = (right) ? Comando.DESPLAZAR_DERECHA : Comando.DESPLAZAR_IZQUIERDA;
		Mensaje msg = new Mensaje(comando);
		msg.agregar(this.id);
		enviar(msg);
	}

	public void mover() {
		if (z) {
			acelerar();
		} else if (Config.currentVelocity > 0.0) {
			desacelerar();
		}

		if (right || left) {
			desplazar();
		}
	}

	public void close() {
		try {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCurrentScene(SceneHandler currentScene) {
		this.currentScene = currentScene;
	}

	public Sala getCurrentSala() {
		return currentSala;
	}

	public List<String> getPlayersInSala() {
		return playersInSala;
	}

	public Sala getSala(int index) {
		if (index < 0 || index >= salas.size()) {
			return null;
		}

		return this.salas.get(index);
	}

	public void setZ(boolean z) {
		this.z = z;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

}
