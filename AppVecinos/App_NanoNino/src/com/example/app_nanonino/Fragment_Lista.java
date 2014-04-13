package com.example.app_nanonino;

import java.util.ArrayList;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_Lista extends ListFragment{
	ArrayList<ObjVecinos> mContactos;
	private Callbacks mCallbacks;
	private OnBaseGetContact contacts;
	
	public interface Callbacks {
		
		public void onEntradaSelecionada(String id);
	}
	


	//El contructor debe ir vacio en un fragment
	public Fragment_Lista(){
		
	}
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mContactos= contacts.getContacts();
			
	        setListAdapter(new ObjVecinosAdapter(getActivity(),mContactos){
				@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            TextView texto = (TextView) view.findViewById(R.id.nombre_contacto); 
			            if (texto != null) 
			            	texto.setText( ((ObjVecinos) entrada).getApellido()+", "+((ObjVecinos) entrada).getNombre()); 
			            
			           // ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen_miniatura); 
			           // if (imagen_entrada != null)
			            //	imagen_entrada.setImageResource(((Lista_contenido.Lista_entrada) entrada).idImagen);
			        }
				}
			});
		
	}


	@Override
	public void onListItemClick(ListView listView, View view, int posicion, long id) {
		super.onListItemClick(listView, view, posicion, id);
		
		// Notificar a la actividad, por medio de la interfaz del callback, que un elemento ha sido seleccionado
		//mCallbacks.onEntradaSelecionada(mContactos.get(posicion).id);
	}

	public interface OnBaseGetContact{

		public ArrayList<ObjVecinos> getContacts();
			
}
		
}
