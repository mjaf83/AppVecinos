package com.example.app_nanonino;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class ContactActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
		}
		Intent intent=getIntent();
		//String message = intent.getStringExtra(MainActivity.Token);
	
	}

@Override
protected void onPostCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	TextView txtCambiado = ((TextView)findViewById(R.id.button1));
	
    txtCambiado.setText("atope");
	super.onPostCreate(savedInstanceState);
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
