package br.android.base;

public class Likes extends Entretenimento{
	
	private String categoria;
	private String DataCriacao;
	
	public Likes(String nome,String id,String categoria,String dataCriacao){
		super(nome,id);
		setCategoria(categoria);
		setDataCriacao(dataCriacao);
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDataCriacao() {
		return DataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
		DataCriacao = dataCriacao;
	}
	
	
	
}
