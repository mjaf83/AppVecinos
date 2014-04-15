package com.example.app_nanonino;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DetalleActivity extends ActionBarActivity {

	private FragmentDetalle fragment;
	static private ObjVecinos contact=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		Intent intent=getIntent();
		Bundle arguments;
		if (savedInstanceState == null) {
			//Creo un objeto contact para poder mostrar el mapa y detalles
			 		contact= new ObjVecinos(intent.getIntExtra(ContactListActivity.EXTRA_ID,0),
					intent.getStringExtra(ContactListActivity.EXTRA_NOMBRE), 
					intent.getStringExtra(ContactListActivity.EXTRA_APELLIDO), 
					intent.getStringExtra(ContactListActivity.EXTRA_TELEFONO),
					intent.getStringExtra(ContactListActivity.EXTRA_EMAIL), 
 					intent.getStringExtra(ContactListActivity.EXTRA_DIRECCION),
					intent.getStringExtra(ContactListActivity.EXTRA_URL), 
					intent.getDoubleExtra(ContactListActivity.EXTRA_LATITUD,0),
					intent.getDoubleExtra(ContactListActivity.EXTRA_LONGITUD,0));
			fragment=new FragmentDetalle();
			arguments=new Bundle();
			arguments.putString(ContactListActivity.EXTRA_NOMBRE, contact.getNombre());
			arguments.putString(ContactListActivity.EXTRA_TELEFONO, contact.getTelefono());
			arguments.putString(ContactListActivity.EXTRA_EMAIL, contact.getEmail());
			arguments.putString(ContactListActivity.EXTRA_DIRECCION, contact.getDireccion());
			fragment.setArguments(arguments);
			fragment.setContact(contact);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment,"DetalleFragment").commit();
		}
	
		 //Carga la imagen en segundo plano
		 CargarImagen conexionImage=new  CargarImagen( );
		 conexionImage.execute(new String[]{intent.getStringExtra(ContactListActivity.EXTRA_URL)});
		 

	
	}

	private class CargarImagen extends AsyncTask<String, Void, Bitmap> {
	
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }
 
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
        	ChangeViewImage(result);
        }
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
		

public void ChangeViewImage(Bitmap result){
	try{
	FragmentDetalle fragment=(FragmentDetalle)getSupportFragmentManager().findFragmentByTag("DetalleFragment");
	fragment.changeView(result);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		
	}
}

public void GuardarContacto(View v){
    		CreateDb conection;
    		conection = new CreateDb(DetalleActivity.this);
    		//en caso de producirse algun error en la inserccion, se muestra un error con un Toast
    		if(conection.insertContact(contact)) {
    				Toast.makeText(getApplicationContext(), R.string.conexionbd, Toast.LENGTH_LONG).show();
    		
    		}else {
				Toast.makeText(getApplicationContext(), R.string.error_conexion_bd, Toast.LENGTH_LONG).show();
    		}        		   	
}

}
