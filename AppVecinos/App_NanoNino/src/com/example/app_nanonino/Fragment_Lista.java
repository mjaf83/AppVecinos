package com.example.app_nanonino;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import java.util.List;
import java.util.zip.Inflater;

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

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public  class Fragment_Lista extends ListFragment{
	 private ObjVecinosAdapter adapter;
	 private ArrayList<ObjVecinos> listadoVecinos;
	 private ListView viewVecinos;
	 private LayoutInflater inflater; 
	 ViewGroup container;
	 ConexionServidor myasynctask;

	//El contructor debe ir vacio en un fragment
	public Fragment_Lista(){
		
	}
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			listadoVecinos=new ArrayList<ObjVecinos>(); 
			//Conecto con el servidor mediante una clase asyntask auxiliar y hago una peticion mediante post
			inicializate();
			
			
	}

public void inicializate(){
	myasynctask=new ConexionServidor();
	myasynctask.execute("");
	
}
public void inicializate2(){
	View rootView = inflater.inflate(R.layout.lista_vecinos,container, false);
	viewVecinos = (ListView)rootView.findViewById(android.R.id.list);
	adapter=new ObjVecinosAdapter(inflater,listadoVecinos) {
		
		@Override
		public void onEntrada(Object entrada, View view) {
			if (entrada!=null)
			{
			TextView text = (TextView)view.findViewById(R.id.nombre_contacto);	
			text.setText( ((ObjVecinos) entrada).getApellido()+", "+((ObjVecinos) entrada).getNombre()); 
            
			}

		
		}
		
	};
	viewVecinos.setAdapter(adapter);	
}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	this.inflater=inflater;
	View rootView = inflater.inflate(R.layout.lista_vecinos,container, false);
	viewVecinos = (ListView)rootView.findViewById(android.R.id.list);
	adapter=new ObjVecinosAdapter(inflater,listadoVecinos) {
		
		@Override
		public void onEntrada(Object entrada, View view) {
			if (entrada!=null)
			{
			TextView text = (TextView)view.findViewById(R.id.nombre_contacto);	
			text.setText( ((ObjVecinos) entrada).getApellido()+", "+((ObjVecinos) entrada).getNombre()); 
            
			}

		
		}
		
	};
	viewVecinos.setAdapter(adapter);	
	return rootView;
}


	@Override
	public void onListItemClick(ListView listView, View view, int posicion, long id) {
		super.onListItemClick(listView, view, posicion, id);
		
		// Notificar a la actividad, por medio de la interfaz del callback, que un elemento ha sido seleccionado
		//mCallbacks.onEntradaSelecionada(mContactos.get(posicion).id);
	}

private class ConexionServidor extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String respuesta = "";
	    	
	        // Inicializar, creando HttpClient y Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://appadia.com/prueba/list");

	        try {
	        	
	            // Agregar parámetros
	        	String token= getArguments().getString(ContactListActivity.EXTRA_TOKEN);
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
			    	JSONArray jsonArray = jObject.getJSONArray("results");
					for(int i=0; i< jsonArray.length();i++)
					{
						try{
							JSONObject json = jsonArray.getJSONObject(i);
							ObjVecinos aux=new ObjVecinos(json.getString("nombre"), json.getString("apellido"), json.getString("telephonenumber"),  json.getString("email"),  json.getString("calle"),  json.getString("image"),0,0);
							listadoVecinos.add(aux);
							
						}
						catch(JSONException e){
							e.printStackTrace();
						}

					
						
					}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			// inicializate2();
			 adapter.notifyDataSetChanged();
		
			// inicializate();
			
		}
		
		

	}


		
}
