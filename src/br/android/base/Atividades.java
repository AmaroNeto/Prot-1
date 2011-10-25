package br.android.base;

public class Atividades extends Entretenimento{
	
	private String dataCriacao;
	
	public Atividades(String nome, String id,String dataCriacao){
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
