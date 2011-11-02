package br.android.GPS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import br.android.base.PontosTuristicos;
import br.android.controle.Controle;

/**
 * Classe criada para cadastrar os pontos que ser�o utilizados no teste.
 * 
 * @author Omega
 *
 */
public class StartOverlay {
	
	private Controle controle;
	
	public StartOverlay() {
		controle = Controle.getInstancia();
	}
	
	/**
	 * M�todo respons�vel pelo cadastro dos pontos no reposit�rio.
	 */
	public void CadastrarPontos(){
		
		/*
		PontosTuristicos praca_do_carmo = new PontosTuristicos("Pra�a do Carmo", null, null, null, null, null, new OverlayItem(new GeoPoint(-8016829,-34848365), "Pra�a do Carmo", ""));
		PontosTuristicos igreja_nossa_senhora_do_carmo = new PontosTuristicos("Igreja Nossa Senhora do Carmo", null, null, null, null, null, new OverlayItem(new GeoPoint(-8016465,-34850186), "Igreja Nossa Senhora do Carmo", ""));
		PontosTuristicos estacao_4_cantos = new PontosTuristicos("Esta��o Quatro Cantos", null, null, null, null, null, new OverlayItem(new GeoPoint(-8016465,-34850926), "Esta��o Quatro Cantos", ""));
		PontosTuristicos convento_de_sao_francisco = new PontosTuristicos("Convento De S�o Francisco", null, null, null, null, null, new OverlayItem(new GeoPoint(-8015031,-34847994), "Convento De S�o Francisco", ""));
		PontosTuristicos igreja_da_se = new PontosTuristicos("Igreja da S�", null, null, null, null, null, new OverlayItem(new GeoPoint(-8013538,-34849111), "Igreja da S�", ""));
		PontosTuristicos artesanato_pretafulo = new PontosTuristicos("Artesanato Pretafulo", null, null, null, null, null, new OverlayItem(new GeoPoint(-8013485,-34852491), "Artesanato Pretafulo", ""));
		PontosTuristicos museu_regional_de_olinda = new PontosTuristicos("Museu Regional de Olinda", null, null, null, null, null, new OverlayItem(new GeoPoint(-8013395,-34853059), "Museu Regional de Olinda", ""));
		PontosTuristicos praca_da_abolicao = new PontosTuristicos("Pra�a da Aboli��o", null, null, null, null, null, new OverlayItem(new GeoPoint(-8016874,-34849658), "Pra�a da Aboli��o", ""));
		
		controle.getRepositorioPontoTuristicos().adicionar(estacao_4_cantos);
		controle.getRepositorioPontoTuristicos().adicionar(igreja_nossa_senhora_do_carmo);
		controle.getRepositorioPontoTuristicos().adicionar(praca_do_carmo);
		controle.getRepositorioPontoTuristicos().adicionar(convento_de_sao_francisco);
		controle.getRepositorioPontoTuristicos().adicionar(igreja_da_se);
		controle.getRepositorioPontoTuristicos().adicionar(artesanato_pretafulo);
		controle.getRepositorioPontoTuristicos().adicionar(museu_regional_de_olinda);
		controle.getRepositorioPontoTuristicos().adicionar(praca_da_abolicao);
		*/
		
		PontosTuristicos biblioteca_publica_de_olinda = new PontosTuristicos("Biblioteca P�blica de Olinda", null, null, null, null, null, new OverlayItem(new GeoPoint(-8016419,-34849047), "Biblioteca P�blica de Olinda", ""));
		PontosTuristicos farol_de_olinda = new PontosTuristicos("Farol de Olinda", null, null, null, null, null, new OverlayItem(new GeoPoint(-8012702,-34850814), "Farol de Olinda", ""));
		PontosTuristicos mercado_eufrasio_barbosa = new PontosTuristicos("Mercado Eufr�sio Barbosa", null, null, null, null, null, new OverlayItem(new GeoPoint(-8020029,-34853322), "Mercado Eufr�sio Barbosa", ""));
		PontosTuristicos teste_TTS = new PontosTuristicos("Ponto de teste TTS", null, null, null, null, null, new OverlayItem(new GeoPoint(-7948270, -34861523), "Ponto de teste TTS", ""));
		
		controle.getRepositorioPontoTuristicos().adicionar(biblioteca_publica_de_olinda);
		controle.getRepositorioPontoTuristicos().adicionar(farol_de_olinda);
		controle.getRepositorioPontoTuristicos().adicionar(mercado_eufrasio_barbosa);
		controle.getRepositorioPontoTuristicos().adicionar(teste_TTS);
		
	}

}
