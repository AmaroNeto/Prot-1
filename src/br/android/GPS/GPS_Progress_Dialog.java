package br.android.GPS;

import br.android.labi9.Principal;
import br.android.labi9.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Classe responsável por verificar se o GPS está ativo ou não. Caso não esteja ativa o programa fica aguardando o
 * usuário ativá-lo
 * @author Omega
 *
 */

public class GPS_Progress_Dialog extends Activity{
	
	private ProgressDialog dialog;
	
	/**
	 * Criando a barra de progresso (Espera)
	 * @param icicle
	 */
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.aguardando_gps);
		
		dialog = ProgressDialog.show(this, "GPS", "Aguardando ativar GPS", false, true);
		CarregarGPS();
	}

	/**
	 * Mantém a barra de progresso até que o GPS seja ativado pelo usuário. O intervalo entre cada thread é de 5 segundos
	 */
	private void CarregarGPS() {
		new Thread(){
			@Override
			public void run(){
				try {
					LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
					if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
						Intent intent = new Intent(GPS_Progress_Dialog.this, GPS_FinalActivity.class);
						dialog.dismiss();
						startActivity(intent);
					}
					else{
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
	 * Mensagem passada em caso de erro por NullPoint
	 */
	public void mensagem(){
		Toast.makeText(this, "Problema ao centralizar o mapa", Toast.LENGTH_SHORT).show();
	}
}
