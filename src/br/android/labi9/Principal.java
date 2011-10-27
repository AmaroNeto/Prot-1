package br.android.labi9;

import br.android.controle.Controle;
import br.android.controle.InterfaceControle;
import br.android.repositorio.Repositorio;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Principal extends Activity{
	
	private InterfaceControle controle;
	private Repositorio repositorio;
	
	public void onCreate(Bundle icicle){
			
		super.onCreate(icicle);
		
		
		controle = Controle.getInstancia();
		repositorio = controle.getRepositorio();
		
		if(repositorio != null){
		
		System.out.println("Tem alguma coisa");
		System.out.println(repositorio.getRepositorio().getPrimeiroNome());
		}
		else{
			System.out.println("Esta vazio");
		}
		/*
		TextView identificador = (TextView) findViewById(R.id.nome);
		
		String nome = "Olá "+repositorio.getRepositorio().getPrimeiroNome()+",";
		identificador.setText(nome);
		*/
		setContentView(R.layout.principal);
	}

}
