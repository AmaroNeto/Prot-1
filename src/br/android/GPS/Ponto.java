package br.android.GPS;


import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * Classe responsável por editar as coordenadas geográficas
 * @author Glauber
 *
 */
public class Ponto extends GeoPoint{

	public Ponto(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
	} 
	
	public Ponto (double latitude, double longitude) {
		this((int)(latitude*1E6), (int)(longitude*1E6));
	}
	
	public Ponto (Location location) {
		this(location.getLatitude(), location.getLongitude());		
	}
}
