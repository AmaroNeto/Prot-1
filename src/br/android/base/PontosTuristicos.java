package br.android.base;

import br.android.GPS.ImagensOverlay;

/**
 * Classe que define um ponto turistico
 * @author Lab-i9
 *
 */
public class PontosTuristicos {
	
	private String nome;
	private String mensagem;
	private String endereco;
	private String categoria;
	private ImagensOverlay coordenada;
	private String texto;
	private String idFoto;
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getIdFoto() {
		return idFoto;
	}
	public void setIdFoto(String idFoto) {
		this.idFoto = idFoto;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public ImagensOverlay getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(ImagensOverlay coordenada) {
		this.coordenada = coordenada;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
}
