package entidades;

import fisica.Vector2D;

public class Mapa {

	private float limiteDerecho;
	private float limiteIzquierdo;
	private float longitud;
	private float ancho;
	private float meta;
	
	public Mapa() {
		this.ancho = 500;
		this.limiteIzquierdo=100;
		this.limiteDerecho=400;
		this.longitud= 10000;
		this.meta=9800;
	}
	
	public void generarCoordenadas() {
		
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

	public float getAncho() {
		return ancho;
	}

	public float getMeta() {
		return meta;
	}
	
	
}
