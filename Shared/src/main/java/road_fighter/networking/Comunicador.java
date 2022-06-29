package road_fighter.networking;

public interface Comunicador {

	void accion(String[] comandos);

	void enviar(Mensaje msg);

}
