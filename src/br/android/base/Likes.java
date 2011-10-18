package br.android.base;

public class Likes {
	
	private String nome;
	private String categoria;
	private String ID;
	private String DataCriacao;
	
	public Likes(String id,String nome,String categoria,String dataCriacao){
		setID(id);
		setNome(nome);
		setCategoria(categoria);
		setDataCriacao(dataCriacao);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDataCriacao() {
		return DataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
		DataCriacao = dataCriacao;
	}
	
	
	
}
