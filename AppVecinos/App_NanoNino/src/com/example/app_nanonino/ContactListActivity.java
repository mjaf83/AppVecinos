package com.example.app_nanonino;


import java.util.Locale;












import com.example.app_nanonino.Fragment_Lista.CallbackMostrarDetalle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class ContactListActivity extends ActionBarActivity implements
		ActionBar.TabListener,OnQueryTextListener,CallbackMostrarDetalle {

	public final static String EXTRA_ID = "ObjVecinos.ID"; 
	public final static String EXTRA_NOMBRE = "ObjVecinos.NOMBRE";
	public final static String EXTRA_APELLIDO = "ObjVecinos.APELLIDO";
	public final static String EXTRA_TELEFONO = "ObjVecinos.TELEFONO";
	public final static String EXTRA_EMAIL = "ObjVecinos.EMAIL";
	public final static String EXTRA_DIRECCION = "ObjVecinos.DIRECCION";
	public final static String EXTRA_URL = "ObjVecinos.URL";
	public final static String EXTRA_LATITUD = "ObjVecinos.LATITUD";
	public final static String EXTRA_LONGITUD = "ObjVecinos.LONGITUD";
	SectionsPagerAdapter mSectionsPagerAdapter;
	

	 public final static String EXTRA_TOKEN = "TOKEN";



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



		//Creo y añado los iconos a los tabs del actionBar
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.contactos).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.guardados).setTabListener(this));		
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.alertas).setTabListener(this));	
	

 
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SearchView mSearchView;
		try{
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.contact_list, menu);
			MenuItem searchItem = (MenuItem)menu.findItem(R.id.action_search);
			
				mSearchView = (SearchView) searchItem.getActionView();
				mSearchView.setQueryHint("Search...");
				mSearchView.setOnQueryTextListener((OnQueryTextListener) this);
			
				menu.add(Menu.NONE, 0, Menu.NONE, "custom").setActionView(R.id.action_search)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
			catch(Exception e){
				e.printStackTrace();
			}
			
	return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
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
			Boolean aux=false;
			Bundle arguments;
			Intent intent;
			String token="";
			Fragment_Lista ListFragment = null;
			if(position<3)
			{
				switch(position)
				{
				case 0 :		  
					arguments = new Bundle();
					intent=getIntent();
					//Recupera la informacion del token para pasarsela al fragment para hacer la conexion
		        	
					token= intent.getStringExtra(LoginActivity.EXTRA_TOKEN);
					arguments.putString(EXTRA_TOKEN, token);
					ListFragment = new Fragment_Lista();
					ListFragment.setArguments(arguments);
			    	break;    
				case 1 :		  
					arguments = new Bundle();
					intent=getIntent();
					//Recupera la informacion del token para pasarsela al fragment para hacer la conexion
		        	
					token= intent.getStringExtra(LoginActivity.EXTRA_TOKEN);
					arguments.putString(EXTRA_TOKEN, token);
					ListFragment = new Fragment_Lista();
					ListFragment.setArguments(arguments);
			    	break;  
				case 2 :		  
					arguments = new Bundle();
					intent=getIntent();
					//Recupera la informacion del token para pasarsela al fragment para hacer la conexion
		        	
					token= intent.getStringExtra(LoginActivity.EXTRA_TOKEN);
					arguments.putString(EXTRA_TOKEN, token);
					ListFragment = new Fragment_Lista();
					ListFragment.setArguments(arguments);
			    	break;  
		
			   default:	arguments = new Bundle();
					intent=getIntent();
					//Recupera la informacion del token para pasarsela al fragment para hacer la conexion
		        	
					token= intent.getStringExtra(LoginActivity.EXTRA_TOKEN);
					arguments.putString(EXTRA_TOKEN, token);
					ListFragment = new Fragment_Lista();
					ListFragment.setArguments(arguments);
			    	break;    
				}
			
			}
			
		return ListFragment;
		}

		@Override
		public int getCount() {
			// Muestra tres pestañas
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

	@Override
	public boolean onQueryTextChange(String newText) {
		Toast.makeText(this, newText, Toast.LENGTH_SHORT).show(); 
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String text) {
		 Toast.makeText(this, "Searching for " + text, Toast.LENGTH_LONG).show();
		return false;
	}

	@Override
	public void CallBackDetalle(ObjVecinos contact) {
		if(contact!=null)
		{
		Intent intent=new Intent(this,DetalleActivity.class);
		intent.putExtra(EXTRA_ID, contact.getId());
		intent.putExtra(EXTRA_NOMBRE, contact.getNombre());
		intent.putExtra(EXTRA_APELLIDO, contact.getApellido());
		intent.putExtra(EXTRA_TELEFONO, contact.getTelefono());
		intent.putExtra(EXTRA_EMAIL, contact.getEmail());
		intent.putExtra(EXTRA_DIRECCION, contact.getDireccion());
		intent.putExtra(EXTRA_URL, contact.getUrl());
		intent.putExtra(EXTRA_LATITUD, contact.getLat());
		intent.putExtra(EXTRA_LONGITUD, contact.getLongt());
		startActivity(intent);
		}
	}



}


