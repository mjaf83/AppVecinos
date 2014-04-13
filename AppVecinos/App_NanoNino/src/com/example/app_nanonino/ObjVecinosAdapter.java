package com.example.app_nanonino;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public abstract class ObjVecinosAdapter extends BaseAdapter{
	 private ArrayList<ObjVecinos> listadoVecinos;
	 private LayoutInflater lInflater;
	 private Context context;
	
	 public ObjVecinosAdapter(Context contexto, ArrayList <ObjVecinos>vecinos){
		 context=contexto;
		 this.lInflater= LayoutInflater.from(context);
		 this.listadoVecinos=vecinos;
		 
	 }
	 
	 
	@Override
	public int getCount() { 
	return listadoVecinos.size();
	}
	
	@Override
	public Object getItem(int posicion) {
		return listadoVecinos.get(posicion);
	}
	@Override
	public long getItemId(int posicion) {
		return posicion;
	}
	
	@Override
	public View getView(int posicion, View view, ViewGroup pariente) {

		
		if(view == null)
		{
			view=lInflater.inflate(R.layout.lista_vecinos,null);
		
		}
		onEntrada (listadoVecinos.get(posicion), view);
		return view;
		
	}
	
	


	//Mediante la implementacion de este metodo podremos 
	public abstract void onEntrada (Object entrada, View view);

}
