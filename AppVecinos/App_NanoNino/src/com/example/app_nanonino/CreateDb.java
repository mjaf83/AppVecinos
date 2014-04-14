package com.example.app_nanonino;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDb extends SQLiteOpenHelper{

	
	private static int VERSION_DB = 1;
	private static String NAME_DB = "VecindarioDB" ;
	
	private static final String TABLE_CONTACT = "CONTACTOS";
	
	private static final String[] COLUMNS_CONTACT = {"_id","nombre", "apellidos", "telefono","email","direccion", "URL", "latitud", "longitud"};	
	
	
	public CreateDb(Context context) {
		super(context, NAME_DB, null, VERSION_DB);
	}
		
	public ArrayList<ObjVecinos> getListContact() {
		SQLiteDatabase db = this.getReadableDatabase();		
		ArrayList<ObjVecinos> datos = new ArrayList<ObjVecinos>();
		
		//SELECT * FROM CONTACTOS
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);	              
		
		if (cursor.moveToFirst()) {
		     //recorremos el cursor hasta que no existan mas resgistros
		     do {
		    	 datos.add(new ObjVecinos(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7), cursor.getDouble(8)));
		     } while(cursor.moveToNext());
		}
        cursor.close();
        return datos;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);		
        onCreate(db);		
	}
	
	public double[] getPosition(int contact_id) {		
		SQLiteDatabase db = this.getReadableDatabase();
		double pos[] = new double[2];
		
		String[] param = new String[1];
		param[0] = Integer.toString(contact_id);
		
		//SELECT latitud, longitud FROM CONTACTOS WHERE _id=contact_id
		Cursor cursor = db.rawQuery("SELECT "+ COLUMNS_CONTACT[7] + ", " + COLUMNS_CONTACT[8] +" FROM " + TABLE_CONTACT + "WHERE " + COLUMNS_CONTACT[0] + "=?", param);				
		if(cursor.moveToFirst()) {					
			pos[0] = cursor.getDouble(0);
			pos[1] = cursor.getDouble(1);			
		}
		return pos;
	}
		
	public boolean insertContact(ObjVecinos c) {
		
		if(!Existe(c.getId())) {
			SQLiteDatabase db = this.getWritableDatabase();		
			ContentValues values = new ContentValues();
			values.put(COLUMNS_CONTACT[0], c.getId());
			values.put(COLUMNS_CONTACT[1], c.getNombre());
			values.put(COLUMNS_CONTACT[2], c.getApellido());
			values.put(COLUMNS_CONTACT[3], c.getTelefono());
			values.put(COLUMNS_CONTACT[4], c.getEmail());
			values.put(COLUMNS_CONTACT[5], c.getDireccion());
			values.put(COLUMNS_CONTACT[6],  c.getUrl());
			values.put(COLUMNS_CONTACT[7],c.getLat());
			values.put(COLUMNS_CONTACT[8], c.getLongt());
			
			long id_row;
			id_row = db.insert(TABLE_CONTACT, null, values);
			
			if(id_row == -1)
				return false;
			else
				return true;			
		}
		else
			return false;		
	}
	
	public boolean Existe(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String[] param = new String[1];
		param[0] = Integer.toString(id);;
		
		Cursor cursor = db.rawQuery("SELECT "+ COLUMNS_CONTACT[0]+ " FROM " + TABLE_CONTACT + " WHERE "+ COLUMNS_CONTACT[0] +
				"= ?", param);
		
		if (cursor.moveToFirst()){
			return true;
		}
		else
			return false;				
	}
		
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DB_CONTACT = "CREATE TABLE " + TABLE_CONTACT + 
				 "("+COLUMNS_CONTACT[0]+" INTEGER PRIMARY KEY, "+COLUMNS_CONTACT[1]+" TEXT, "+COLUMNS_CONTACT[2]+" TEXT, "+ 
		COLUMNS_CONTACT[3]+" TEXT, "+COLUMNS_CONTACT[4]+" TEXT, "+COLUMNS_CONTACT[5]+" TEXT, "+ COLUMNS_CONTACT[6]+" TEXT,"
		+COLUMNS_CONTACT[7]+" REAL, "+COLUMNS_CONTACT[8]+" REAL); ";
		
		db.execSQL(CREATE_DB_CONTACT);		
	}


  
}
