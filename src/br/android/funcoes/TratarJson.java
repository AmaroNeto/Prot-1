package br.android.funcoes;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
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
		
		Usuario usuario = new Usuario(id, pNome, uNome,genero, localidade);
		
		return usuario;
	}
}
