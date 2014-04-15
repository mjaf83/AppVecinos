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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;




public  class Fragment_Lista extends Fragment{
	CallbackMostrarDetalle mCallback;
	

	public final static String EXTRA_ID = "ObjVecinos.ID"; 
	public final static String EXTRA_NOMBRE = "ObjVecinos.NOMBRE";
	public final static String EXTRA_APELLIDO = "ObjVecinos.APELLIDO";
	public final static String EXTRA_TELEFONO = "ObjVecinos.TELEFONO";
	public final static String EXTRA_EMAIL = "ObjVecinos.EMAIL";
	public final static String EXTRA_DIRECCION = "ObjVecinos.DIRECCION";
	public final static String EXTRA_URL = "ObjVecinos.URL";
	public final static String EXTRA_LATITUD = "ObjVecinos.LATITUD";
	public final static String EXTRA_LONGITUD = "ObjVecinos.LONGITUD";
	
	 private ObjVecinosAdapter adapter;
	 private ArrayList<ObjVecinos> listadoVecinos;
	 private ListView viewVecinos;
	 private ConexionServidor myasynctask;
	 private static Activity context;

	 //private CallbackMostrarDetalle mCallbacks;
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

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.lista_vecinos,container, false);
	viewVecinos = (ListView)rootView.findViewById(android.R.id.list);
	
	// Se bindea la listView con la lista de contactos
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
	viewVecinos.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> p, View view, int posicion, long id) {
				mCallback.CallBackDetalle(listadoVecinos.get(posicion));
			}

      });
		
	return rootView;
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
							ObjVecinos aux=new ObjVecinos(json.getInt("number"),json.getString("nombre"), json.getString("apellido"), json.getString("telephonenumber"),  json.getString("email"),  json.getString("calle"),  json.getString("image"),0,0);
							listadoVecinos.add(aux);
							
						}
						catch(JSONException e){
							e.printStackTrace();
						}

					
						
					}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			 try{
			 adapter.notifyDataSetChanged();
			 }
			 catch(Exception e){
				 e.printStackTrace();
				 
			 }
		}
		
		

	}

public interface CallbackMostrarDetalle{
	public  void CallBackDetalle(ObjVecinos detalle);
}
	
@Override
public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
        mCallback = (CallbackMostrarDetalle) activity;
    } 
    catch (ClassCastException e) {
        throw new ClassCastException(activity.toString()
                + " must implement CallbackMostrarDetalle");
    }
}

}



