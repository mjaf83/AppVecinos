package com.example.app_nanonino;

import java.io.IOException;
import java.io.InputStream;
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
import org.json.JSONArray;
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
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class ContactActivity extends ActionBarActivity {

	public  static String EXTRA_NOMBRE = "ObjVecinos.NOMBRE";
	public  static String EXTRA_APELLIDO = "ObjVecinos.APELLIDO";
	public  static String EXTRA_TELEFONO = "ObjVecinos.TELEFONO";
	public  static String EXTRA_EMAIL = "ObjVecinos.EMAIL";
	public  static String EXTRA_DIRECCION = "ObjVecinos.DIRECCION";
	public  static String EXTRA_URL = "ObjVecinos.URL";
	public  static String EXTRA_LATITUD = "ObjVecinos.LATITUD";
	public  static String EXTRA_LONGITUD = "ObjVecinos.LONGITUD";

	
	
	 private ArrayList<ObjVecinos> listadoVecinos;
	 private ObjVecinosAdapter adaptador;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			listadoVecinos=new ArrayList<ObjVecinos>(); 
			ConexionServidor conexion= new ConexionServidor();
			
			conexion.execute();
			
		}
	}
/*
@Override
protected void onPostCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	Intent intent=getIntent();
	String message = intent.getStringExtra(MainActivity.EXTRA_TOKEN);
	TextView txtCambiado = ((TextView)findViewById(R.id.button1));
	
    txtCambiado.setText(message);
	super.onPostCreate(savedInstanceState);
}*/


private class ConexionServidor extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String respuesta = "";
	    	
	        // Inicializar, creando HttpClient y Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://appadia.com/prueba/list");

	        try {
	        	
	            // Agregar parámetros
	        	Intent intent=getIntent();
	        	String token= intent.getStringExtra(LoginActivity.EXTRA_TOKEN);
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("token", token));
	            nameValuePairs.add(new BasicNameValuePair("limit", "20"));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	            // Ejecutar la petición HTTP Post
	            HttpResponse response = httpclient.execute(httppost);
	            
	            // Obtener respuesta del servidor
	            InputStream is=response.getEntity().getContent();
	            
	            //Colocar datos en un String
	            String datos = LoginActivity.convertStreamToString(is);
	            
	            respuesta = datos;

	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return respuesta;
		}

		@Override
		protected void onPostExecute(String result) {
			 try {
			    	JSONObject jObject= new JSONObject(result);
			    	JSONArray jsonArray = jObject.getJSONArray("results");//.getJSONObject(1).getString("nombre");
					for(int i=0; i< jsonArray.length();i++)
					{
						try{
							JSONObject json = jsonArray.getJSONObject(i);
							ObjVecinos aux=new ObjVecinos(json.getString("nombre"), json.getString("apellido"), json.getString("telephonenumber"),  json.getString("email"),  json.getString("calle"),  json.getString("image"), 1,  1);
							listadoVecinos.add(aux);
							
						}
						catch(JSONException e){
							e.printStackTrace();
						}

					
						
					}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			 //	ListView lista = (ListView)findViewById(R.id.list);
			//	adaptador=new  ObjVecinosAdapter(ContactActivity.this,listadoVecinos);
			//	lista.setAdapter(adaptador);
				
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
			View rootView = inflater.inflate(R.layout.fragment_contact,
					container, false);
		
			return rootView;
		}
	}

}
