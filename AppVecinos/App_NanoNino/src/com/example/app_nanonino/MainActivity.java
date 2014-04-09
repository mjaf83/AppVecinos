package com.example.app_nanonino;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	public final static String EXTRA_TOKEN = "com.example.myfirstapp.MESSAGE";
	public static String Token = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	
	}
    
    public void btnLogin(View v) {
    	ConexionServidor task = new ConexionServidor();
    	task.execute(new String[] { "" });
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(EXTRA_TOKEN, Token);
        startActivity(intent);
    
    }
    


    private class ConexionServidor extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String respuesta = "";
	    	
	        // Inicializar, creando HttpClient y Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://www.appadia.com/prueba/login");

	        try {
	        	final String usuario = ((EditText)findViewById(R.id.usuarioInput)).getText().toString();
	        	final String pass = ((EditText)findViewById(R.id.contrasenyaInput)).getText().toString();
	        	
	            // Agregar parámetros
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("user", "prueba1@test.com"));
	            nameValuePairs.add(new BasicNameValuePair("pass",getStringMessageDigest("test1h") ));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	            // Ejecutar la petición HTTP Post
	            HttpResponse response = httpclient.execute(httppost);
	            
	            // Obtener respuesta del servidor
	            InputStream is=response.getEntity().getContent();
	            
	            //Colocar datos en un String
	            String datos = convertStreamToString(is);
	            respuesta=datos;
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return respuesta;
		}
	    
		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject jObject= new JSONObject(result);
				Boolean success=jObject.getBoolean("success");
				if(success)
					{
				    Token = jObject.getString("token");
					}
				else
					{
					//TODO Mostrar mensaje error
					
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}


    /*
     * La función convertStreamToString() fue tomada de:
     * http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
     */
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    //SHA1
    private static String toHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
//Encripta un mensaje en SSH-1
    public static String getStringMessageDigest(String message){
        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
    } 
    
}
