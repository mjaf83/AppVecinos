package com.example.app_nanonino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentDetalle extends Fragment{
	private ObjVecinos contact;
	public FragmentDetalle(){
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detalle,
				container, false);

		TextView nombre=(TextView) rootView.findViewById(R.id.NombreDetalle);
		nombre.setText(getArguments().getString(ContactListActivity.EXTRA_NOMBRE));
		TextView telefono=(TextView) rootView.findViewById(R.id.TelefonoDetalle);
		telefono.setText(getArguments().getString(ContactListActivity.EXTRA_TELEFONO));
		TextView email=(TextView) rootView.findViewById(R.id.EmailDetalle);
		email.setText(getArguments().getString(ContactListActivity.EXTRA_EMAIL));
		TextView direccion=(TextView) rootView.findViewById(R.id.DireccionDetalle);
		direccion.setText(getArguments().getString(ContactListActivity.EXTRA_DIRECCION));
		

		Thread thread=  new Thread(){
	        @Override
	        public void run(){
	            try {
	                synchronized(this){
	                    wait(3000);
	                }
	            }
	            catch(InterruptedException ex){                    
	            }

	            // TODO              
	        }
	    };

	    thread.start();     
		return rootView;
	}
public void changueView(Bitmap bipmap){
		ImageView img= (ImageView)getView().findViewById(R.id.imageContact2);
		img.setImageBitmap(bipmap);
	}
public void setContact(ObjVecinos contact)
{
this.contact=contact;
}


}
