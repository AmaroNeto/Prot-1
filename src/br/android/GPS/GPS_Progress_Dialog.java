package br.android.GPS;

import br.android.labi9.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

/**
 * Classe responsável por verificar se o GPS está ativo ou não. Caso não esteja ativa o programa fica aguardando o
 * usuário ativá-lo
 * @author Omega
 *
 */

public class GPS_Progress_Dialog extends Activity{
	
	private ProgressDialog dialog;
	private Location location;
	private Handler handler = new Handler();
	
	public ProgressDialog getDialog() {
		return dialog;
	}

	public void setDialog(ProgressDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * Criando a barra de progresso (Espera) que aguarda até o usuário ativar o GPS
	 * @param icicle
	 */
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.aguardando_gps);
		Resources resources = getResources();
		dialog = ProgressDialog.show(this, "GPS", resources.getString(R.string.como_ativar_GPS), false, true);
		CarregarGPS();
	}

	/**
	 * Informa como o usuário pode ativar o GPS e aguarda até que o procedimento seja concluído
	 * 
	 */
	private void CarregarGPS() {
		new Thread(){
			@Override
			public void run(){
				try {
					LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
					if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
						BuscandoCoordenadas();
					}
					else{
						//evita o fechamento do programa de forma inesperada
						Thread.sleep(5000);
						run();
					}
					
				} catch (NullPointerException e) {
					mensagem();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	
	/**
	 * Procura as coordenadas do usuário. Enquanto se realizando a busca, uma mensagem pede que o usuário aguarde
	 */
	public void BuscandoCoordenadas() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Resources resources = getResources();
				dialog.setMessage(resources.getString(R.string.buscando_coordenadas_usuario));
				dialog.onStart();
				location = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if(location.getAltitude() != 0){
					chamarNovaTela();
				}
				else{
					BuscandoCoordenadas();
				}
			}
		});
	}
	
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
	 * Mensagem passada em caso de erro por NullPoint
	 */
	public void mensagem(){
		Toast.makeText(this, "Problema ao centralizar o mapa", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Chama o mapa com o overlay do usuário centralizado nas coordenadas encontradas pelo GPS
	 */
	public void chamarNovaTela(){
		dialog.dismiss();
		Intent intent = new Intent(this, StartOverlay.class);
		startActivity(intent);
	}
	
}
