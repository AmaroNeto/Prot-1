package br.android.GPS;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.android.base.PontosTuristicos;
import br.android.controle.Controle;
import br.android.labi9.R;
import br.android.repositorio.Repositorio;

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
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Classe respons�vel por controlar toda a parte de GPS do aplicativo. Nela est�o inseridos os m�todos de busca de lugares
 * chamada dos tipos de vis�o (Sat�lite e Street), centralizar overlays, Uso do GPS e cria��o dos overlays (Pontos 
 * criados e "setados" no mapa.
 * 
 * @author Omega
 *
 */
public class GPS_FinalActivity extends MapActivity implements LocationListener, OnInitListener{

	private MapController controlador;
	private MapView map;
	private MyLocationOverlay ondeEstou;
	private Geocoder gc;
	private double lat;
	private double lon;
	private Drawable imagem;
	private Vector<OverlayItem> pontos;
	private TextToSpeech tts;
	private static final String TAG = "ProximityTest";
	private final String POI_REACHED = "com.example.proximitytest.POI_REACHED";
	private PendingIntent proximityIntent;
	private LocationManager locationManager;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) throws NullPointerException{
		try{
			//Cria��o da tela
			super.onCreate(savedInstanceState);
			setContentView(R.layout.maingps); 
			Controle controle = Controle.getInstancia();
			StartOverlay startBD = new StartOverlay();
			startBD.CadastrarPontos();
			
			pontos = new Vector<OverlayItem>();
			
			new Thread(){
				public void run(){
					try {
						setupProximityAlert();
					} catch (NullPointerException e) {
					}
				}
			}.start();
			
			//Cria��o do mapa
			map = (MapView) findViewById(R.id.mapa);
			map.setStreetView(true);
			map.setClickable(true); 
			map.setBuiltInZoomControls(true);
			
			//Cria��o do controlador
			controlador = map.getController();
			//Zoom inicial do maps (Pode ser alterado de 1 at� 21)
			controlador.setZoom(7);

			//Cria��o da vari�vel respons�vel pela tradu��o da String para a�dio
			tts = new TextToSpeech(this, this);
			
			//ondeEstou : Overlay do usu�rio
			ondeEstou = new MyLocationOverlay(this, map);
			ondeEstou.enableMyLocation();
			map.getOverlays().add(ondeEstou);
			
			/**
			 * Cria��o dos overlays. Aqui � necess�rio pegar a coordenada (O JSON oferece a coordenada como double e apenas retirar
			 * o ponto para que o GEOPOINT reconhec�a como int e fa�a o c�lculo
			 * 
			 * A imagem "ponto1" pode ser alterada por outro �cone
			 * O 3� par�metro da cria��o do overlay ser� utilizado para um breve resumo do ponto tur�stico. Esse par�metro
			 * ser� o utilizado pelo tts (Mencionado acima)
			 */
			imagem = getResources().getDrawable(R.drawable.ponto1);			
			
			//Pega todos os overlays e joga no mapa
			for(PontosTuristicos x : controle.getRepositorioPontoTuristicos().listar()){
				pontos.add(x.getOverlayItem());
			}
			ImagensOverlay pontosOverlay = new ImagensOverlay(GPS_FinalActivity.this,pontos,imagem);
			map.getOverlays().add(pontosOverlay);
			
			//Pega a localiza��o inicial do usu�rio atrav�s do GPS
			Location location = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			//A localiza��o sendo diferente de null o mapa � centralizado na coordenada passada pelo GPS
			try {
				if(location != null){
					Ponto ponto = new Ponto(location);
					controlador.setCenter(ponto);
				}

			} catch (NullPointerException e) {
				//
			}
			
			/**
			 * Bot�es utilizados no mapa
			 * Respectivamente: 
			 * Vis�o de sat�lite
			 * Vis�o de rua; (Vis�o inicial)
			 * Pesquisar (Pega a string colocada no edit text iniciado mais abaixo)
			 * Centralizar na coordenada do usu�rio caso ele n�o queira diminuir o zoom para encontrar seu overlay 
			 */
			Button buttonSatView = (Button) findViewById(R.id.buttonSateliteView);
			Button buttonStreetView = (Button) findViewById(R.id.buttonStreetView);
			Button bntSearch = (Button) findViewById(R.id.pesquisar);
			Button meuGPSLocal = (Button) findViewById(R.id.voltarAoCentro);
			
			
			/*
			 * Este trecho do c�digo � chamado ao clicar o bot�o pesquisar.
			 * A string colocada no edittex ser� pesquisada e retornar� a coordenada.
			 */
			gc = new Geocoder(this);
			bntSearch.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					EditText adress = (EditText) findViewById(R.id.campoTexto);
					String addressInput = adress.getText().toString(); 

					try {

						List<Address> foundAdresses = gc.getFromLocationName(addressInput, 5);
						
						//Caso a string n�o seja encontrada
						if (foundAdresses.size() == 0) {
							Toast.makeText(map.getContext(), "Lugar n�o encontrado.\nVerifique o endere�o digitado", Toast.LENGTH_LONG).show();

						}
						/*
						 * Convers�o da latitude e longitude e navega��o para o ponto (Caso encontrado)
						 */
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
				 * Fun��o que levar� o usu�rio para o ponto informado no mapa
				 * @param latitude
				 * @param longitude
				 * @param mv
				 */
				public void navigateToLocation (double latitude, double longitude, MapView mv) {
					GeoPoint p = new GeoPoint((int) latitude, (int) longitude);
					mv.displayZoomControls(true);
					MapController mc = mv.getController();
					mc.animateTo(p);
				}
			});
			
			//Bot�o que altera a vis�o para o tipo sat�lite
			buttonSatView.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					map.setSatellite(true);
					map.setStreetView(false);
				}
			});
			
			//Bot�o que altera a vis�o para o modo street
			buttonStreetView.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					map.setStreetView(true);
					map.setSatellite(false);
				}
			});
			
			try {
				//Bot�o que centraliza o mapa no overlay do usu�rio caso o GPS esteja ativo
					meuGPSLocal.setOnClickListener(new OnClickListener(){
						public void onClick(View v) {
							locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
							if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && ondeEstou.getMyLocation().getLatitudeE6() != 0) {
								controlador.setCenter(ondeEstou.getMyLocation());
							}
							else{
								mensagemGPS(0);
							}
						}
					});
				} catch (NullPointerException e) {
					Toast.makeText(this, "Problema ao centralizar o mapa", Toast.LENGTH_SHORT).show();
				}
			
			try {
				getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			} catch (NullPointerException e) {
				Toast.makeText(this, "Erro Nulpoint requestLocationUpdates", Toast.LENGTH_SHORT).show();
			}
		}

		catch (NullPointerException e) {
			Toast.makeText(this, "Erro Nulpoint classe GPS_FinalActivity", Toast.LENGTH_SHORT).show();
		}

	}
	
	public void mensagemGPS(int numero){
		if(numero == 0){
			Toast.makeText(this, "GPS inativo. Ative-o", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Fun��o que verifica se o GPS est� ligado. Caso o GPS esteja ativo ele ir� verificar se a coordenada
	 * do usu�rio est� pr�xima do ponto informado dentro da fun��o. O ponto foi fixado por�m pode ser alterado
	 * @throws NullPointerException
	 */
	private void setupProximityAlert() throws NullPointerException {
		
		/**
		 * Thread criada para otimizar a pesquisa entre overlay do usu�rio e o ponto informado
		 */
		new Thread(){
			public void run(){
				try {
					LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
					if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
						//Toast.makeText(this, "GPS ativo", Toast.LENGTH_LONG).show();
						Log.d(TAG, "Registering ProximityAlert");

						Intent intent = new Intent(POI_REACHED);
						proximityIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

						/**
						 * Fun��o que recebe a latitude, longitude, raio de busca e a vari�vel proximityIntent
						 * Ao passar as coordenadas dividir o valor por 1E6
						 * Raio definido = 50 metros
						 * Coordenadas do NTI
						 */
						locationManager.addProximityAlert(-7.948270, -34.861523, 50, -1, proximityIntent);
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
	
	/**
	 * Classe que � chamada no caso do overlay estar dentro do raio do overlay passado para teste.
	 * Na classe abaixo � visualizado na tela uma string e � chamado o tts
	 * @author Glauber
	 *
	 */
	private class ProximityAlertReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent) {
			tts.speak("Maranguape 1", TextToSpeech.QUEUE_ADD, null);
			Toast.makeText(context, "Ponto dentro do raio estabelecido\nCasa - 50m", Toast.LENGTH_LONG).show();
			Log.d(TAG, "Voc� est� pr�ximo ao centro do bairro de dois irm�os");
		}
	}

	/**
	 * M�todo respons�vel pelas repostas quando � usada alguma tecla na aplica��o
	 * @param keyCode
	 * @param event
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//Caso o bot�o de voltar do aparelho seja utilizado o programa � finalizado
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
				
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 
	 * @return
	 * @throws NullPointerException
	 */
	private LocationManager getLocationManager() throws NullPointerException {
		try {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			return locationManager;
		} catch (NullPointerException e) {
			Toast.makeText(this, "Erro nullpoint LocationManager", Toast.LENGTH_SHORT).show();
		}
		return null;
	}

	/**
	 * M�todo chamado quando � verificado que a posi��o do usu�rio sofreu alguma mudan�a
	 * Obs: a linha "controlador.animateTo(geoPoint)" foi comentada pois utilizamos a pesquisa de lugares e sempre que o usu�rio
	 * se move ela centralizada no seu overlay
	 *  
	 * @param location
	 */
	@Override
	public void onLocationChanged(Location location) throws NullPointerException{
		try {
			map.invalidate();
			} 
		catch (NullPointerException e) {
		}
	}
	
	/**
	 * M�todo criado para destruir os updates da localiza��o do usu�rio e melhorar a performance
	 */
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
		}
	}

	
	/**
	 * M�todo chamado caso o usu�rio pressione a tecla HOME do aparelho ou ocorra qualquer outra atividade
	 * que n�o necessite que o app seja fechado (Apenas colocado em segundo plano)
	 */
	@Override
	protected void onPause() throws NullPointerException{
		try {
			super.onPause();
			ondeEstou.disableMyLocation();
		} catch (NullPointerException e) {
		}
	}
	
	/**
	 * Os m�todos abaixo s�o complementares ao programa e ser�o utilizados conforme o projeto for crescendo
	 */
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

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}