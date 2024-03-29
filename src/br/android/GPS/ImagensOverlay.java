package br.android.GPS;


import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * Classe respons�vel pela cria��o e edi��o dos pontos tur�sticos (Overlays) que ser�o utilizados no prot�tipo
 * @author Omega
 *
 */
public class ImagensOverlay extends ItemizedOverlay implements OnInitListener{
	
	private final List<OverlayItem> imagens;
	private final Context context;
	private TextToSpeech tts;
	
	/**
	 * Construtor do overlay. Recebe o mapa como context (Nesse caso), os pontos tur�sticos (Nesse caso) e a imagem utilizada para
	 * os overlays
	 *  
	 * @param context
	 * @param imagens
	 * @param drawable
	 */
	public ImagensOverlay(Context context, List<OverlayItem> imagens, Drawable drawable) {
		super(drawable);
		this.context = context;
		this.imagens = imagens;
		boundCenterBottom(drawable);
		populate();
		this.tts = new TextToSpeech(context,this);
	}
	
	

	public TextToSpeech getTts() {
		return tts;
	}



	public void setTts(TextToSpeech tts) {
		this.tts = tts;
	}



	@Override
	protected OverlayItem createItem(int i) {
		return imagens.get(i);
	}

	@Override
	public int size() {
		return imagens.size();
	}
	
	/**
	 * M�todo utilizado quando � clicado sobre um overlay. Nele chamamos a fun��o TTS.
	 * Quando chamada, ela apresenta uma mensagem na tela do usu�rio e l� essa mensagem
	 * @param i
	 */
	@Override
	protected boolean onTap(int i){
		OverlayItem overlayItem = imagens.get(i);
		String texto = overlayItem.getTitle() + " - " + overlayItem.getSnippet();
		Toast.makeText(context, texto, Toast.LENGTH_LONG).show();
		getTts().speak(overlayItem.getSnippet(), TextToSpeech.QUEUE_ADD, null);
		return (true);
	}
	
	@Override
	public void onInit(int status) {
		
		
	}
	
	public void Speach(String texto) {
    	
    }
}
