package br.android.base;

import java.util.ArrayList;

/**
 * Classe que define um usuario
 * 
 * @author Lab-i9
 *
 */
public class Usuario {
	
	//Atributos da classe
	private String ID;
	private String PrimeiroNome;
	private String UltimoNome;
	private String genero;
	private String localidade;
	private ArrayList<Likes> likes;
	private ArrayList<Eventos> eventos;
	private ArrayList<Musicas> musicas;
	private ArrayList<Filmes> filmes;
	private ArrayList<TV> tv;
	private ArrayList<Atividades> atividades;
	
	/**
	 * Construtor da classe usuario
	 * 
	 * @param id
	 * @param Primeironome
	 * @param UltimoNome
	 * @param usuario
	 * @param genero
	 * @param localidade
	 */
	public Usuario(String id,String primeiroNome,String ultimoNome,String genero,String localidade){
		
		this.setID(id);
		this.setPrimeiroNome(primeiroNome);
		this.setUltimoNome(ultimoNome);
		this.setGenero(genero);
		this.setLocalidade(localidade);
		
	}

	public String getPrimeiroNome() {
		return PrimeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		PrimeiroNome = primeiroNome;
	}

	public String getUltimoNome() {
		return UltimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		UltimoNome = ultimoNome;
	}

	//Getts e Setts
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public ArrayList<Likes> getLikes() {
		return likes;
	}
	public void setLikes(ArrayList<Likes> likes) {
		this.likes = likes;
	}
	public ArrayList<Eventos> getEventos() {
		return eventos;
	}
	public void setEventos(ArrayList<Eventos> eventos) {
		this.eventos = eventos;
	}
	public ArrayList<Musicas> getMusicas() {
		return musicas;
	}
	public void setMusicas(ArrayList<Musicas> musicas) {
		this.musicas = musicas;
	}
	public ArrayList<Filmes> getFilmes() {
		return filmes;
	}
	public void setFilmes(ArrayList<Filmes> filmes) {
		this.filmes = filmes;
	}
	public ArrayList<TV> getTv() {
		return tv;
	}
	public void setTv(ArrayList<TV> tv) {
		this.tv = tv;
	}
	public ArrayList<Atividades> getAtividades() {
		return atividades;
	}
	public void setAtividades(ArrayList<Atividades> atividades) {
		this.atividades = atividades;
	}
}
