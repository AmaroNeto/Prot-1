package br.android.GPS;


/**
 * Classe principal. Aqui são cadastrados os pontos turísticos. Também é onde atualizamos a posição do usuário conforme ele vai se
 * movimentando. Ao clicar nos pontos turísticos é mostrado uma mensagem.
 * @author Glauber
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.android.labi9.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GPS_FinalActivity extends MapActivity implements LocationListener, OnInitListener{

	private MapController controlador;
	private MapView map;
	private MyLocationOverlay ondeEstou;
	private Geocoder gc;
	private double lat;
	private double lon;
	private Drawable imagem;
	private List<OverlayItem> pontos;
	private TextToSpeech tts;

	
	private static final String TAG = "ProximityTest";
	private final String POI_REACHED = "com.example.proximitytest.POI_REACHED";
	private PendingIntent proximityIntent;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) throws NullPointerException{
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.maingps);
			setupProximityAlert();			
			
			map = (MapView) findViewById(R.id.mapa);
			map.setStreetView(true);
			map.setClickable(true); 
			map.setBuiltInZoomControls(true);
			
			controlador = map.getController();
			controlador.setZoom(15);
			
			tts = new TextToSpeech(this, this);
			 
			imagem = getResources().getDrawable(R.drawable.seta1);
			pontos = new ArrayList<OverlayItem>();
			pontos.add(new OverlayItem(new GeoPoint(-7998950, -34931492), "Ponto 1", "Centro do Dois irmãos. Visite o Horto e fique maravilhado com os animais"));
			pontos.add(new OverlayItem(new GeoPoint(-8126270, -34903793), "Ponto 2", "Centro de Boa Viagem. Praias lindas para visitar"));
			pontos.add(new OverlayItem(new GeoPoint(-8016911,-34848361), "Ponto 3", "Praça do carmo em Olinda. Carnaval bom demais"));
			pontos.add(new OverlayItem(new GeoPoint(-8055535,-34870693), "Ponto 4", "Centro do Recife. Carnaval bom demais"));
			
			ImagensOverlay pontosOverlay = new ImagensOverlay(GPS_FinalActivity.this,pontos,imagem);
			map.getOverlays().add(pontosOverlay);
			
			Location location = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			try {
				if(location != null){
					Ponto ponto = new Ponto(location);
					controlador.setCenter(ponto);
				}

			} catch (NullPointerException e) {

			}

			ondeEstou = new MyLocationOverlay(this, map);
			ondeEstou.enableMyLocation();
			map.getOverlays().add(ondeEstou);
		
			Button buttonSatView = (Button) findViewById(R.id.buttonSateliteView);
			Button buttonStreetView = (Button) findViewById(R.id.buttonStreetView);
			Button bntSearch = (Button) findViewById(R.id.pesquisar);
			Button meuGPSLocal = (Button) findViewById(R.id.voltarAoCentro);
			
			gc = new Geocoder(this);

			bntSearch.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					EditText adress = (EditText) findViewById(R.id.campoTexto);
					String addressInput = adress.getText().toString(); 

					try {

						List<Address> foundAdresses = gc.getFromLocationName(addressInput, 5);

						if (foundAdresses.size() == 0) {
							Toast.makeText(map.getContext(), "Lugar não encontrado.\nVerifique o endereço digitado", Toast.LENGTH_LONG).show();

						}

						else {
							for (int i = 0; i < foundAdresses.size(); ++i) {
								Address x = foundAdresses.get(i);
								lat = x.getLatitude();
								lon = x.getLongitude();
							}
							navigateToLocation((lat * 1000000), (lon * 1000000), map);
						}
					}
					catch (Exception e) {
						
					}
				}


				/**
				 * Navigates a given MapView to the specified Longitude and Latitude
				 */
				public void navigateToLocation (double latitude, double longitude, MapView mv) {
					GeoPoint p = new GeoPoint((int) latitude, (int) longitude);
					mv.displayZoomControls(true);
					MapController mc = mv.getController();
					mc.animateTo(p);
				}
			});

			buttonSatView.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					map.setSatellite(true);
					map.setStreetView(false);
				}
			});

			buttonStreetView.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					map.setStreetView(true);
					map.setSatellite(false);
				}
			});

			try {
				meuGPSLocal.setOnClickListener(new OnClickListener(){
					public void onClick(View v) {
						controlador.setCenter(ondeEstou.getMyLocation());
					}
				});
			} catch (NullPointerException e) {
				Toast.makeText(this, "Problema ao centralizar o mapa", Toast.LENGTH_SHORT).show();
			}
			
			try {
				getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			} catch (NullPointerException e) {
				Toast.makeText(this, "Erro Nulpoint", Toast.LENGTH_SHORT).show();
			}
		}

		catch (NullPointerException e) {
			Toast.makeText(this, "Erro Nulpoint", Toast.LENGTH_SHORT).show();
		}

	}
	
	private void setupProximityAlert() throws NullPointerException {
		new Thread(){
			public void run(){
		try {
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				//Toast.makeText(this, "GPS ativo", Toast.LENGTH_LONG).show();
				Log.d(TAG, "Registering ProximityAlert");

				Intent intent = new Intent(POI_REACHED);
				proximityIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
				
				locationManager.addProximityAlert(-7.998950*1E6, -34.931492*1E6, 60000000, -1, proximityIntent);
				
				IntentFilter intentFilter = new IntentFilter(POI_REACHED);
				registerReceiver(new ProximityAlertReceiver(), intentFilter);
			} else {
				//Toast.makeText(this, "GPS desligado", Toast.LENGTH_LONG).show();
				Log.d(TAG, "GPS_PROVIDER not available");
			}
		} catch (NullPointerException e) {
			//Toast.makeText(this, "O GPS foi desativado por falta de movimento", Toast.LENGTH_LONG).show();
		}
			}
		}.start();
		
	}
	
	private class ProximityAlertReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent) {
			tts.speak("Alerta de proximidade com o ponto registrado", TextToSpeech.QUEUE_ADD, null);
			Toast.makeText(context, "Alerta de proximidade com o ponto registrado. Distância de ", Toast.LENGTH_LONG).show();
			Log.d(TAG, "Você está próximo ao centro do bairro de dois irmãos");
		}
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finishActivity(0);
		}
				
		return super.onKeyDown(keyCode, event);

		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	private LocationManager getLocationManager() throws NullPointerException {
		try {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			return locationManager;
		} catch (NullPointerException e) {
			Toast.makeText(this, "Erro nullpoint", Toast.LENGTH_SHORT).show();
		}
		return null;
	}

	
	@Override
	public void onLocationChanged(Location location) throws NullPointerException{
		try {
			GeoPoint geoPoint = new Ponto(location);
			//controlador.animateTo(geoPoint);
			map.invalidate();
			
		} catch (NullPointerException e) {
			Toast.makeText(this, "Erro nullpoint no TTS", Toast.LENGTH_SHORT).show();
		} catch (RuntimeException e) {
			Toast.makeText(this, "Erro Runtime no TTS", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		getLocationManager().removeUpdates(this);
	}

	@Override
	protected void onResume() throws NullPointerException{
		try {
			super.onResume();
			ondeEstou.enableMyLocation();
		} catch (NullPointerException e) {
			Toast.makeText(this, "Erro null", Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	protected void onPause() throws NullPointerException{
		try {
			super.onPause();
			ondeEstou.disableMyLocation();
		} catch (NullPointerException e) {
			Toast.makeText(this, "Erro null", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInit(int status) {
		
	}
}