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
 * Classe responsável pela criação e edição dos pontos turísticos que serão utilizados no protótipo
 * @author Glauber
 *
 */
public class ImagensOverlay extends ItemizedOverlay implements OnInitListener{
	
	private final List<OverlayItem> imagens;
	private final Context context;
	private TextToSpeech tts;

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
