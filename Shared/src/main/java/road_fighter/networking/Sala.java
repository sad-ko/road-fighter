package road_fighter.networking;

import java.util.Objects;

import road_fighter.logica.Dificultad;

public class Sala {

	protected String nombre;
	protected String owner;
	protected Dificultad dificultad;
	protected int cantidadMaxima;
	protected int cantidadActual;

	public Sala(String nombre, int cantidadMaxima, Dificultad dificultad, String owner) {
		this.nombre = nombre.toUpperCase();
		this.cantidadMaxima = cantidadMaxima;
		this.dificultad = dificultad;
		this.owner = owner;
	}

	public Sala(String nombre, int cantidadMaxima, int dificultad, String owner) {
		this(nombre, cantidadMaxima, Dificultad.values()[dificultad], owner);
	}

	public Sala(String nombre, String owner) {
		this.nombre = nombre;
		this.owner = owner;
	}

	public void setCantidadActual(int cantidadActual) {
		this.cantidadActual = cantidadActual;
	}

	public String getOwner() {
		return owner;
	}

	public String getDificultadString() {
		return dificultad.toString();
	}
	
	public Dificultad getDificultad() {
		return dificultad;
	}

	public int getCantidadMaxima() {
		return cantidadMaxima;
	}

	public int getCantidadActual() {
		return cantidadActual;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		return nombre.equals(other.nombre) && owner.equals(other.owner);
	}

	@Override
	public String toString() {
		return nombre + " - [" + cantidadActual + "/" + cantidadMaxima + "] - " + dificultad;
	}

}
