package br.android.funcoes;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Classe que cria uma conexao WebService para utilizar funcoes da internet
 * 
 * @author Alpha
 */
public class WebService {
	
	private HttpClient cliente;
	private HttpGet get;
	
	/**
	 * Contrutor que cria um cliente qualquer e define o
	 * Http para acessar a internet
	 * 
	 * @param String
	 */
	public WebService(String url){
		this.cliente = new DefaultHttpClient();
		this.get = new HttpGet(url);
	}
	
	/**
	 * Metodo que conecta com a internet e retorna a entidade que foi executada por ela.
	 * 
	 * @return HttpEntity
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpEntity conectar() throws ClientProtocolException, IOException{
		
		return cliente.execute(get).getEntity();
	} 
	
	/**
	 * Metodo que encerra a conexao com a internet
	 */
	public void encerrar(){
		this.get.abort();
	}
}