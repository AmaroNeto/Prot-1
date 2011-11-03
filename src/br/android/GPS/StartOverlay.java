package br.android.GPS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import br.android.base.PontosTuristicos;
import br.android.controle.Controle;
import br.android.labi9.R;

/**
 * Classe criada para cadastrar os pontos que serão utilizados no teste.
 * 
 * @author Omega
 *
 */
public class StartOverlay extends Activity{
	
	private Controle controle = Controle.getInstancia();
	private ProgressDialog dialog;

	
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.aguardando_gps);
		Resources resources = getResources();
		dialog = ProgressDialog.show(this, "Overlays", resources.getString(R.string.carregando_overlay_no_mapa), false, true);
		CadastrarPontos();
	}
	
	/**
	 * Método responsável pelo cadastro dos pontos no repositório.
	 */
	private void CadastrarPontos() {
		new Thread(){
			@Override
			public void run(){
				try {
					Resources resources = getResources();
					
					PontosTuristicos biblioteca_publica_de_olinda = new PontosTuristicos(resources.getString(R.string.biblioteca_publica_de_olinda), null, null, null, null, null, new OverlayItem(new GeoPoint(-8016419,-34849047),resources.getString(R.string.biblioteca_publica_de_olinda), ""));
					PontosTuristicos farol_de_olinda = new PontosTuristicos(resources.getString(R.string.farol_de_olinda), null, null, null, null, null, new OverlayItem(new GeoPoint(-8012702,-34850814), resources.getString(R.string.farol_de_olinda), ""));
					PontosTuristicos mercado_eufrasio_barbosa = new PontosTuristicos(resources.getString(R.string.mercado_eufrasio_barbosa), null, null, null, null, null, new OverlayItem(new GeoPoint(-8020029,-34853322), resources.getString(R.string.mercado_eufrasio_barbosa), ""));
					PontosTuristicos teste_TTS = new PontosTuristicos("Ponto de teste TTS", null, null, null, null, null, new OverlayItem(new GeoPoint(-7948270, -34861523), "Ponto de teste TTS", ""));

					controle.getRepositorioPontoTuristicos().adicionar(biblioteca_publica_de_olinda);
					controle.getRepositorioPontoTuristicos().adicionar(farol_de_olinda);
					controle.getRepositorioPontoTuristicos().adicionar(mercado_eufrasio_barbosa);
					controle.getRepositorioPontoTuristicos().adicionar(teste_TTS);
					
					chamarNovaTela();
					
				} catch (NullPointerException e) {
					
				}
			}
		}.start();	
	}
	
	public void chamarNovaTela(){
		dialog.dismiss();
		Intent intent = new Intent(this, GPS_FinalActivity.class);
		startActivity(intent);
	}
	

}
