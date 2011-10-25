package br.android.base;

public abstract class Entretenimento {
	
	private String nome;
	private String ID;
	
	public Entretenimento(String nome, String id){

		this.setID(id);
		this.setNome(nome);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
}
