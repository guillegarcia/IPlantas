package com.iplantas.iplantas.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iplantas.iplantas.Params;
import com.iplantas.iplantas.model.Sitio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicch on 13/11/2017.
 */

public class SitiosDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME="SITIO";

    public static final String CREATE_TABLE=""+
            " CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
            " ( id INTEGER PRIMARY KEY,"+
            " name TEXT NOT NULL UNIQUE, "+
            " lat REAL, "+
            " lng REAL )";

    public SitiosDB(Context context) {
        super(context, Params.DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Sitio> obtenerSitios(){
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, lat, lng FROM "+TABLE_NAME+" order by name";
        Cursor cursor=db.rawQuery(SQL,null);
        return this.preprarListado(cursor);
    }

    public List<Sitio> buscarSitios(String text){
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, lat, lng FROM "+TABLE_NAME+" WHERE LOWER(name) LIKE ? order by name";
        Cursor cursor=db.rawQuery(SQL,new String[]{"'%"+text+"%'"});
        return this.preprarListado(cursor);
    }

    public long insertar(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("lat",-1);
        cv.put("lng",-1);
        long id=db.insert(TABLE_NAME,null,cv);
        return id;
    }

    public void modificar(long id, String name, double lat, double lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("lat",lat);
        cv.put("lng",lng);
        db.update(TABLE_NAME,cv,"id=?", new String[]{id+""});
    }

    public void eliminar(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{id+""});
    }

    private List<Sitio> preprarListado(Cursor cursor){
        List<Sitio> sitios=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            sitios.add(this.crearSitio(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return sitios;
    }

    private Sitio crearSitio(Cursor cursor){
        long id=cursor.getLong(0);
        String name=cursor.getString(1);
        double lat=cursor.getDouble(2);
        double lng=cursor.getDouble(3);
        return new Sitio(id,name,lat,lng);
    }

}
