package br.android.GPS;

import br.android.labi9.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * Classe responsável por verificar se o GPS está ativo no aparelho do usuário. Em caso negativo o usuário recebe uma mensagem o informando do fato e pergunta se
 * deseja continuar com o GPS inativo ou ativá-lo. Se o usuário desejar ativar o GPS ele será encaminhado para outra tela que vai passar as informações de como
 * ativar o seu GPS
 * 
 * @author Omega
 *
 */
public class AlertaConfirmacaoGPS extends Activity{
	
	protected void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.aguardando_gps);
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		//Verifica se o GPS está inativo
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			AlertDialog.Builder alerta = new AlertDialog.Builder(AlertaConfirmacaoGPS.this);
			alerta.setIcon(R.drawable.mapa);
			alerta.setTitle("GPS inativo");
			alerta.setMessage("Deseja ativar o GPS?");
			
			//Configura as opções que serão exibidas ao usuário
			alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//chamar a tela em que o usuário pode ativar o GPS
					Intent intent = new Intent(AlertaConfirmacaoGPS.this, GPS_Progress_Dialog.class);
					startActivity(intent);
				}
			});
			//Chama a tela do mapa e não exibe o overlay do usuário
			alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(AlertaConfirmacaoGPS.this, GPS_FinalActivity.class);
					startActivity(intent);
				}
			});
			
			//Exibe o alerta
			alerta.show();
		}
		
		//Caso o GPS já esteja ativo, o programa segue para a tela que exibirá o MAPA
		else{
			Intent intent = new Intent(AlertaConfirmacaoGPS.this, GPS_FinalActivity.class);
			startActivity(intent);
		}
	}
}
