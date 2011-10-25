package br.android.base;

public class Filmes extends Entretenimento{
	
	private String dataCriacao;
	
	public Filmes(String nome, String id,String dataCriacao){
		super(nome, id);
		this.setDataCriacao(dataCriacao);
	}
	
	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
