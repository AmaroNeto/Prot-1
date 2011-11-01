package br.android.base;

import com.google.android.maps.OverlayItem;

/**
 * Classe que define um ponto turistico
 * @author Lab-i9
 * @author Omega
 *
 */
public class PontosTuristicos {
	
	private String nome;
	private String mensagem;
	private String endereco;
	private String categoria;
	private String texto;
	private String idFoto;
	private OverlayItem overlayItem;
	
	/**
	 * Ao criar um ponto turístico observar que o objeto overlayItem tem que seguir o formato abaixo: 
	 * new OverlayItem(new GeoPoint(-8015031,-34847994), "Convento De São Francisco", "Mensagem curta sobre o local lida pelo TTS")
	 * 
	 * @param nome
	 * @param mensagem
	 * @param endereco
	 * @param categoria
	 * @param texto
	 * @param idFoto
	 * @param overlayItem
	 */
	public PontosTuristicos(String nome, String mensagem, String endereco,
			String categoria, String texto, String idFoto,
			OverlayItem overlayItem) {
		super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.endereco = endereco;
		this.categoria = categoria;
		this.texto = texto;
		this.idFoto = idFoto;
		this.overlayItem = overlayItem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(String idFoto) {
		this.idFoto = idFoto;
	}

	public OverlayItem getOverlayItem() {
		return overlayItem;
	}

	public void setOverlayItem(OverlayItem overlayItem) {
		this.overlayItem = overlayItem;
	}

}
