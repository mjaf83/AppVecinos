package com.example.app_nanonino;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Choreographer.FrameCallback;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends ActionBarActivity implements
		ActionBar.TabListener, Fragment_Lista.OnBaseGetContact, Fragment_Lista.Callbacks  {

	
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	 private ArrayList<ObjVecinos> listadoVecinos;
	 public final static String EXTRA_TOKEN = "Guardados";

	 
		public final static String EXTRA_NOMBRE = "ObjVecinos.NOMBRE";
		public final static String EXTRA_APELLIDO = "ObjVecinos.APELLIDO";
		public final static String EXTRA_TELEFONO = "ObjVecinos.TELEFONO";
		public final static String EXTRA_EMAIL = "ObjVecinos.EMAIL";
		public final static String EXTRA_DIRECCION = "ObjVecinos.DIRECCION";
		public final static String EXTRA_URL = "ObjVecinos.URL";
		public final static String EXTRA_LATITUD = "ObjVecinos.LATITUD";
		public final static String EXTRA_LONGITUD = "ObjVecinos.LONGITUD";

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});


		//Conecto con el servidor mediante una clase asyntask auxiliar y hago una peticion mediante post
		listadoVecinos=new ArrayList<ObjVecinos>(); 
		ConexionServidor conexion= new ConexionServidor();
		conexion.execute();
		
		
		//Creo y añado los iconos a los tabs del actionBar
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.contactos).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.guardados).setTabListener(this));		
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.alertas).setTabListener(this));		
		

		
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
		 if (tab.getPosition() == 0) {
					 Bundle arguments = new Bundle();
					  arguments.putString(EXTRA_TOKEN, "guardado");
					  Fragment_Lista ListFragment = new Fragment_Lista();
					  ListFragment.setArguments(arguments);
					  getSupportFragmentManager().beginTransaction().replace(R.id.container, ListFragment).commit();
		   	 }/*
		     else if (tab.getPosition() == 1) {
		    	 
		    	      Bundle arguments = new Bundle();
					  arguments.putString(EXTRA_TOKEN, "guardado");
					  Fragment_Lista ListFragment = new Fragment_Lista();
					  ListFragment.setArguments(arguments);
		    	      getSupportFragmentManager().beginTransaction().replace(R.id.container, ListFragment).commit();
	}   
		     else if (tab.getPosition() == 2) {
			    	 	Bundle arguments = new Bundle();
			    	 	arguments.putString(EXTRA_TOKEN, "guardado");
			    	 	Fragment_Lista ListFragment = new Fragment_Lista();
			    	 	ListFragment.setArguments(arguments);
			    	 	getSupportFragmentManager().beginTransaction().replace(R.id.container, ListFragment).commit(); }
			      
		      
		      
		      
		     else {
		       
		    	 Bundle arguments = new Bundle();
				  arguments.putString(EXTRA_TOKEN, "guardado");
				  Fragment_Lista ListFragment = new Fragment_Lista();
				  ListFragment.setArguments(arguments);
	    	      getSupportFragmentManager().beginTransaction().replace(R.id.container, ListFragment).commit();
	    	      }*/
		
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_contact_list,
					container, false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
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
							ObjVecinos aux=new ObjVecinos(json.getString("nombre"), json.getString("apellido"), json.getString("telephonenumber"),  json.getString("email"),  json.getString("calle"),  json.getString("image"), Double.parseDouble(json.getString("")),  Double.parseDouble(json.getString("")));
							listadoVecinos.add(aux);
							
						}
						catch(JSONException e){
							e.printStackTrace();
						}

					
						
					}
					} catch (JSONException e) {
						e.printStackTrace();
					}
	
				
		}
	}

public  ArrayList<ObjVecinos> getContacts(){
	
	return listadoVecinos; 
}



@Override
public void onEntradaSelecionada(String id) {
	// TODO Auto-generated method stub
	
}
}


