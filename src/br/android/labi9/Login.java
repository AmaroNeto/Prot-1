package br.android.labi9;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import br.android.funcoes.TratarJson;
import br.android.funcoes.WebService;
import br.android.repositorio.Repositorio;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity implements OnClickListener{
    
	Facebook facebook = new Facebook("245556445494965");
    String FILENAME = "Recife_Plus";
    private SharedPreferences mPrefs;
	private static ProgressDialog dialogo;
	private Repositorio repositorio;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        
        Button entrar = (Button) findViewById(R.id.entrar);
        entrar.setOnClickListener(this);
        
        
        /*
         * Get existing access_token if any
         */
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);
        
        
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        /*
         * Only call authorize if the access_token has expired.
        */ 
        if(!facebook.isSessionValid()) {
        	
            facebook.authorize(this, new String[] {"user_about_me","user_events","user_groups","user_interests","user_likes","user_religion_politics"}, new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                          
                }
    
                @Override
                public void onFacebookError(FacebookError error) {}
    
                @Override
                public void onError(DialogError e) {}
    
                @Override
                public void onCancel() {}
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

	@Override
	public void onClick(View v) {
		
		dialogo = ProgressDialog.show(this, "Analisando Usuario", "Aguarde enquanto buscamos suas informacoes...", false, true);
		this.criarUsuario();
		
		Intent it = new Intent(this,br.android.labi9.Principal.class);
		Bundle params = new Bundle();
		
		startActivity(it);
		
	}
	//Metodo STOP para a thread acima.
	public static void stopJanela(){
		dialogo.dismiss();
	}
	
	/**
	 * Metodo que cria um usuario a partir da conexao com a internet
	 */
	public void criarUsuario(){
		new Thread(){
			public void run() {
				String url = "https://graph.facebook.com/me/?access_token=";
		
				WebService con = null;
				
				try {
					
					con = new WebService(url);
					
					repositorio = new Repositorio(new TratarJson().tratarUsuario(con.conectar()));
		
					//excecoes
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}finally{
					con.encerrar();
					//con =null;
					stopJanela();
				}
			}
		}.start();
	}
	
}