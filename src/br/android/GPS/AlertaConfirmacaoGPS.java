package br.android.GPS;

import br.android.labi9.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * Classe respons�vel por verificar se o GPS est� ativo no aparelho do usu�rio. Em caso negativo o usu�rio recebe uma mensagem o informando do fato e pergunta se
 * deseja continuar com o GPS inativo ou ativ�-lo. Se o usu�rio desejar ativar o GPS ele ser� encaminhado para outra tela que vai passar as informa��es de como
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
		
		//Verifica se o GPS est� inativo
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			AlertDialog.Builder alerta = new AlertDialog.Builder(AlertaConfirmacaoGPS.this);
			alerta.setIcon(R.drawable.mapa);
			alerta.setTitle("GPS inativo");
			alerta.setMessage("Deseja ativar o GPS?");
			
			//Configura as op��es que ser�o exibidas ao usu�rio
			alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//chamar a tela em que o usu�rio pode ativar o GPS
					Intent intent = new Intent(AlertaConfirmacaoGPS.this, GPS_Progress_Dialog.class);
					startActivity(intent);
				}
			});
			//Chama a tela do mapa e n�o exibe o overlay do usu�rio
			alerta.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(AlertaConfirmacaoGPS.this, GPS_FinalActivity.class);
					startActivity(intent);
				}
			});
			
			//Exibe o alerta
			alerta.show();
		}
		
		//Caso o GPS j� esteja ativo, o programa segue para a tela que exibir� o MAPA
		else{
			Intent intent = new Intent(AlertaConfirmacaoGPS.this, GPS_FinalActivity.class);
			startActivity(intent);
		}
	}
}
