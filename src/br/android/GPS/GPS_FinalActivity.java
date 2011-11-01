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
 * Classe responsável por controlar toda a parte de GPS do aplicativo. Nela estão inseridos os métodos de busca de lugares
 * chamada dos tipos de visão (Satélite e Street), centralizar overlays, Uso do GPS e criação dos overlays (Pontos 
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
			//Criação da tela
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
			
			//Criação do mapa
			map = (MapView) findViewById(R.id.mapa);
			map.setStreetView(true);
			map.setClickable(true); 
			map.setBuiltInZoomControls(true);
			
			//Criação do controlador
			controlador = map.getController();
			//Zoom inicial do maps (Pode ser alterado de 1 até 21)
			controlador.setZoom(7);

			//Criação da variável responsável pela tradução da String para aúdio
			tts = new TextToSpeech(this, this);
			
			//ondeEstou : Overlay do usuário
			ondeEstou = new MyLocationOverlay(this, map);
			ondeEstou.enableMyLocation();
			map.getOverlays().add(ondeEstou);
			
			/**
			 * Criação dos overlays. Aqui é necessário pegar a coordenada (O JSON oferece a coordenada como double e apenas retirar
			 * o ponto para que o GEOPOINT reconhecça como int e faça o cálculo
			 * 
			 * A imagem "ponto1" pode ser alterada por outro ícone
			 * O 3º parâmetro da criação do overlay será utilizado para um breve resumo do ponto turístico. Esse parâmetro
			 * será o utilizado pelo tts (Mencionado acima)
			 */
			imagem = getResources().getDrawable(R.drawable.ponto1);			
			
			//Pega todos os overlays e joga no mapa
			for(PontosTuristicos x : controle.getRepositorioPontoTuristicos().listar()){
				pontos.add(x.getOverlayItem());
			}
			ImagensOverlay pontosOverlay = new ImagensOverlay(GPS_FinalActivity.this,pontos,imagem);
			map.getOverlays().add(pontosOverlay);
			
			//Pega a localização inicial do usuário através do GPS
			Location location = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			//A localização sendo diferente de null o mapa é centralizado na coordenada passada pelo GPS
			try {
				if(location != null){
					Ponto ponto = new Ponto(location);
					controlador.setCenter(ponto);
				}

			} catch (NullPointerException e) {
				//
			}
			
			/**
			 * Botões utilizados no mapa
			 * Respectivamente: 
			 * Visão de satélite
			 * Visão de rua; (Visão inicial)
			 * Pesquisar (Pega a string colocada no edit text iniciado mais abaixo)
			 * Centralizar na coordenada do usuário caso ele não queira diminuir o zoom para encontrar seu overlay 
			 */
			Button buttonSatView = (Button) findViewById(R.id.buttonSateliteView);
			Button buttonStreetView = (Button) findViewById(R.id.buttonStreetView);
			Button bntSearch = (Button) findViewById(R.id.pesquisar);
			Button meuGPSLocal = (Button) findViewById(R.id.voltarAoCentro);
			
			
			/*
			 * Este trecho do código é chamado ao clicar o botão pesquisar.
			 * A string colocada no edittex será pesquisada e retornará a coordenada.
			 */
			gc = new Geocoder(this);
			bntSearch.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					EditText adress = (EditText) findViewById(R.id.campoTexto);
					String addressInput = adress.getText().toString(); 

					try {

						List<Address> foundAdresses = gc.getFromLocationName(addressInput, 5);
						
						//Caso a string não seja encontrada
						if (foundAdresses.size() == 0) {
							Toast.makeText(map.getContext(), "Lugar não encontrado.\nVerifique o endereço digitado", Toast.LENGTH_LONG).show();

						}
						/*
						 * Conversão da latitude e longitude e navegação para o ponto (Caso encontrado)
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
				 * Função que levará o usuário para o ponto informado no mapa
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
			
			//Botão que altera a visão para o tipo satélite
			buttonSatView.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					map.setSatellite(true);
					map.setStreetView(false);
				}
			});
			
			//Botão que altera a visão para o modo street
			buttonStreetView.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					map.setStreetView(true);
					map.setSatellite(false);
				}
			});
			
			try {
				//Botão que centraliza o mapa no overlay do usuário caso o GPS esteja ativo
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
	 * Função que verifica se o GPS está ligado. Caso o GPS esteja ativo ele irá verificar se a coordenada
	 * do usuário está próxima do ponto informado dentro da função. O ponto foi fixado porém pode ser alterado
	 * @throws NullPointerException
	 */
	private void setupProximityAlert() throws NullPointerException {
		
		/**
		 * Thread criada para otimizar a pesquisa entre overlay do usuário e o ponto informado
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
						 * Função que recebe a latitude, longitude, raio de busca e a variável proximityIntent
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
	 * Classe que é chamada no caso do overlay estar dentro do raio do overlay passado para teste.
	 * Na classe abaixo é visualizado na tela uma string e é chamado o tts
	 * @author Glauber
	 *
	 */
	private class ProximityAlertReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent) {
			tts.speak("Maranguape 1", TextToSpeech.QUEUE_ADD, null);
			Toast.makeText(context, "Ponto dentro do raio estabelecido\nCasa - 50m", Toast.LENGTH_LONG).show();
			Log.d(TAG, "Você está próximo ao centro do bairro de dois irmãos");
		}
	}

	/**
	 * Método responsável pelas repostas quando é usada alguma tecla na aplicação
	 * @param keyCode
	 * @param event
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//Caso o botão de voltar do aparelho seja utilizado o programa é finalizado
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
	 * Método chamado quando é verificado que a posição do usuário sofreu alguma mudança
	 * Obs: a linha "controlador.animateTo(geoPoint)" foi comentada pois utilizamos a pesquisa de lugares e sempre que o usuário
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
	 * Método criado para destruir os updates da localização do usuário e melhorar a performance
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
	 * Método chamado caso o usuário pressione a tecla HOME do aparelho ou ocorra qualquer outra atividade
	 * que não necessite que o app seja fechado (Apenas colocado em segundo plano)
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
	 * Os métodos abaixo são complementares ao programa e serão utilizados conforme o projeto for crescendo
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