package road_fighter.networking;

import road_fighter.logica.Dificultad;

public class Mensaje {

	private static final String SEPARADOR = ":";
	private String msg;

	public Mensaje(Comando comando) {
		msg = comando.name();
	}

	public <T> void agregar(T valor) {
		msg += SEPARADOR + String.valueOf(valor);
	}

	public static String[] decodificar(String msg) {
		return msg.split(SEPARADOR);
	}

	public static Sala paresarSala(String nombre, String maxima, String dificultad, String owner, String actual) {
		Sala sala = new Sala(nombre, Integer.parseInt(maxima), Dificultad.valueOf(dificultad), owner);
		sala.setCantidadActual(Integer.parseInt(actual));
		return sala;
	}

	@Override
	public String toString() {
		return msg;
	}

}
