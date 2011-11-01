package br.android.repositorio;

import java.util.Vector;

import br.android.base.*;

public class RepositorioPontoTuristicos {
	
	private Vector<PontosTuristicos> pontosTuristicos;
	
	public RepositorioPontoTuristicos(){
		
		this.pontosTuristicos = new Vector<PontosTuristicos>();
	}
	
	public void adicionar(PontosTuristicos pontoTuristico){
		this.pontosTuristicos.add(pontoTuristico);
	}
	
	public void remover(PontosTuristicos pontoTuristico){
		this.pontosTuristicos.remove(pontoTuristico);
	}
	
	public Vector<PontosTuristicos> listar(){
		return this.pontosTuristicos;
	}
	
}
