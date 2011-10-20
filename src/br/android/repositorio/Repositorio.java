package br.android.repositorio;

import java.util.Vector;

import br.android.base.Usuario;

/**
 * Classe que cria um repositorio dos usuario
 * 
 * @author Lab-i9
 *
 */
public class Repositorio {
	
	private Usuario usuarios;
	
	/**
	 * Construtor do objeto repositorio
	 */
	public Repositorio(Usuario usuario){
		this.usuarios = usuario;
	}

	
	public Usuario getRepositorio(){
		return usuarios;
	}
}
