package com.example.app_nanonino;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ObjVecinosAdapter extends BaseAdapter{
	 private ArrayList<ObjVecinos> listadoVecinos;
	 private LayoutInflater lInflater;
	 public ObjVecinosAdapter(Context context, ArrayList <ObjVecinos>vecinos){
		
		 this.lInflater= LayoutInflater.from(context);
		 this.listadoVecinos=vecinos;
		 
	 }
	 
	 
	@Override
	public int getCount() { 
	return listadoVecinos.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		return listadoVecinos.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ContenedorView contenedor=null;
		
		if(arg1 == null)
		{
			arg1=lInflater.inflate(R.layout.lista_vecinos,null);
			contenedor= new ContenedorView();
			contenedor.nomVendedor = (TextView) arg1.findViewById(R.id.nomVecino);
			arg1.setTag(contenedor);
		}
		else
		{
			contenedor=(ContenedorView) arg1.getTag();
		}	
			ObjVecinos vecinos= (ObjVecinos) getItem(arg0);
			contenedor.nomVendedor.setText(vecinos.getApellido()+", "+vecinos.getNombre());
			
		
		return arg1;
		
	}
	
	class ContenedorView{
		 public TextView nomVendedor;
	
		
	}
	
	//OnItemClickListener
	 


}
