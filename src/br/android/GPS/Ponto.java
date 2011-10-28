package br.android.GPS;


import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * Classe respons�vel por editar as coordenadas geogr�ficas
 * @author Omega
 *
 */
public class Ponto extends GeoPoint{
	
	public Ponto(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
	} 
	
	/**
	 * M�todo que converte os valores recebidos como double para uma coordenada geogr�fica
	 * @param latitude
	 * @param longitude
	 */
	public Ponto (double latitude, double longitude) {
		this((int)(latitude*1E6), (int)(longitude*1E6));
	}

	public Ponto (Location location) {
		this(location.getLatitude(), location.getLongitude());		
	}
}
