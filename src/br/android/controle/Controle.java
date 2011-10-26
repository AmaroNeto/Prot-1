package br.android.controle;

import br.android.repositorio.Repositorio;

/**
 * Classe de controle, serve para direcionar as paginas do android.
 * @author Lab-i9
 *
 */
public final class Controle implements InterfaceControle{
	
	private static Repositorio repositorio;
	private static Controle instancia;
	
	private Controle(){
	}
	
	/**
	 * Pega a instacia do controle, se o controle ja existir, ele o retorna, se nao, cria um novo
	 * @return
	 */
	public static Controle getInstancia(){
		if(instancia == null){
			instancia = new Controle();
		}
		return instancia;
	}
	
	public void setRepositorio(Repositorio repositorio){
		this.repositorio = repositorio;
	}
	
	public Repositorio getRepositorio(){
		return repositorio;
	}
	
	
	
}
