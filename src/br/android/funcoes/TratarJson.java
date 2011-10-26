package br.android.funcoes;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import br.android.base.Atividades;
import br.android.base.Eventos;
import br.android.base.Filmes;
import br.android.base.Likes;
import br.android.base.Musicas;
import br.android.base.TV;
import br.android.base.Usuario;

/**
 * Classe que filtra um arquivo Json, retornando so oque e necessario
 * 
 * @author Lab-i9
 *
 */
public class TratarJson {
	
	/**
	 * metodo que pega as informacoes basicas do usuario
	 * 
	 */
	public Usuario tratarUsuario(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject faceUsuario = new JSONObject(EntityUtils.toString(url));
		String pNome = faceUsuario.getString("first_name");
		String uNome = faceUsuario.getString("last_name");
		String id = faceUsuario.getString("id");
		String genero = faceUsuario.getString("gender");
		
		JSONObject local = faceUsuario.getJSONObject("location");
		String localidade = local.getString("name");
		
		Usuario usuario = new Usuario(id,pNome, uNome,genero, localidade);
		
		return usuario;
	}
	/**
	 * 
	 * Metodo que pega as informações do likes do usuario
	 * 
	 */
	public ArrayList<Likes> tratarLikes(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject likes = new JSONObject(EntityUtils.toString(url));
		JSONArray data = likes.getJSONArray("data");
		
		ArrayList<Likes>  listaLikes = new ArrayList<Likes>(data.length());
		
		for(int i = 0;i < data.length(); i++){
			
			JSONObject objeto = data.getJSONObject(i);
			String nome = objeto.getString("name");
			String categoria = objeto.getString("category");
			String id = objeto.getString("id");
			String dataCriacao = objeto.getString("created_time");
			
			Likes x = new Likes(nome,id,categoria,dataCriacao);
			listaLikes.add(x);
		}
		
		return listaLikes;
	}
	/**
	 * 
	 * Metodo que pega as informações dos Eventos do usuario
	 * 
	 */
	public ArrayList<Eventos> tratarEventos(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject eventos = new JSONObject(EntityUtils.toString(url));
		JSONArray data = eventos.getJSONArray("data");
		
		ArrayList<Eventos>  listaEventos = new ArrayList<Eventos>(data.length());
		
		for(int i = 0;i < data.length(); i++){
			
			JSONObject objeto = data.getJSONObject(i);
			String nome = objeto.getString("name");
			String dataInicio = objeto.getString("start_time");
			String dataFim = objeto.getString("end_time");
			String id = objeto.getString("id");
			String local = objeto.getString("location");
			String status = objeto.getString("rsvp_status");
			
			Eventos x = new Eventos(nome,id,local,status,dataInicio,dataFim);
			listaEventos.add(x);
		}
		
		return listaEventos;
	}
	/**
	 * 
	 * Metodo que pega as informações dos gostos musicais do usuario
	 * 
	 */
	public ArrayList<Musicas> tratarMusicas(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject musicas = new JSONObject(EntityUtils.toString(url));
		JSONArray data = musicas.getJSONArray("data");
		
		ArrayList<Musicas>  listaMusicas = new ArrayList<Musicas>(data.length());
		
		for(int i = 0;i < data.length(); i++){
			
			JSONObject objeto = data.getJSONObject(i);
			String nome = objeto.getString("name");
			String id = objeto.getString("id");
			String dataCriacao = objeto.getString("created_time");
			
			Musicas x = new Musicas(nome,id,dataCriacao);
			listaMusicas.add(x);
		}
		
		return listaMusicas;
	}
	/**
	 * 
	 * Metodo que pega as informações dos filmes que usuario o usuario gosta
	 * 
	 */
	public ArrayList<Filmes> tratarFilmes(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject filmes = new JSONObject(EntityUtils.toString(url));
		JSONArray data = filmes.getJSONArray("data");
		
		ArrayList<Filmes>  listaFilmes = new ArrayList<Filmes>(data.length());
		
		for(int i = 0;i < data.length(); i++){
			
			JSONObject objeto = data.getJSONObject(i);
			String nome = objeto.getString("name");
			String id = objeto.getString("id");
			String dataCriacao = objeto.getString("created_time");
			
			Filmes x = new Filmes(nome,id,dataCriacao);
			listaFilmes.add(x);
		}
		
		return listaFilmes;
	}
	/**
	 * 
	 * Metodo que pega as informações dos programas de tv que usuario o usuario gosta
	 * 
	 */
	public ArrayList<TV> tratarTv(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject tv = new JSONObject(EntityUtils.toString(url));
		JSONArray data = tv.getJSONArray("data");
		
		ArrayList<TV>  listaTv = new ArrayList<TV>(data.length());
		
		for(int i = 0;i < data.length(); i++){
			
			JSONObject objeto = data.getJSONObject(i);
			String nome = objeto.getString("name");
			String id = objeto.getString("id");
			String dataCriacao = objeto.getString("created_time");
			
			TV x = new TV(nome,id,dataCriacao);
			listaTv.add(x);
		}
		
		return listaTv;
	}
	/**
	 * 
	 * Metodo que pega as informações das atividades que o usuario gosta que usuario o usuario gosta
	 * 
	 */
	public ArrayList<Atividades> tratarAtividades(HttpEntity url) throws ParseException,JSONException, IOException{
		
		JSONObject atividades = new JSONObject(EntityUtils.toString(url));
		JSONArray data = atividades.getJSONArray("data");
		
		ArrayList<Atividades>  listaAtividades = new ArrayList<Atividades>(data.length());
		
		for(int i = 0;i < data.length(); i++){
			
			JSONObject objeto = data.getJSONObject(i);
			String nome = objeto.getString("name");
			String id = objeto.getString("id");
			String dataCriacao = objeto.getString("created_time");
			
			Atividades x = new Atividades(nome,id,dataCriacao);
			listaAtividades.add(x);
		}
		
		return listaAtividades;
	}
}
