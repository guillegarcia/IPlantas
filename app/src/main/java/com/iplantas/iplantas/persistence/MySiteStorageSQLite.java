package com.iplantas.iplantas.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iplantas.iplantas.Params;
import com.iplantas.iplantas.model.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicch on 13/11/2017.
 */

public class MySiteStorageSQLite extends SQLiteOpenHelper implements MySiteStorage {

    public static final String TABLE_NAME="SITIO";

    public static final String CREATE_TABLE=""+
            " CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
            " ( id INTEGER PRIMARY KEY,"+
            " name TEXT NOT NULL UNIQUE, "+
            " lat REAL, "+
            " lng REAL )";

    public MySiteStorageSQLite(Context context) {
        super(context, Params.DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Site> getSites(){
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, lat, lng FROM "+TABLE_NAME+" order by name";
        Cursor cursor=db.rawQuery(SQL,null);
        return this.prepareList(cursor);
    }

    public List<Site> searchSites(String text){
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, lat, lng FROM "+TABLE_NAME+" WHERE LOWER(name) LIKE ? order by name";
        Cursor cursor=db.rawQuery(SQL,new String[]{"'%"+text+"%'"});
        return this.prepareList(cursor);
    }

    public long insertSite(String name, double lat, double lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("lat",lat);
        cv.put("lng",lng);
        return db.insert(TABLE_NAME,null,cv);
    }

    public int updateSite(long id, String name, double lat, double lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("lat",lat);
        cv.put("lng",lng);
        return db.update(TABLE_NAME,cv,"id=?", new String[]{id+""});
    }

    public int deleteSite(long id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"id=?",new String[]{id+""});
    }

    private List<Site> prepareList(Cursor cursor){
        List<Site> sites=new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            sites.add(this.createSite(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return sites;
    }

    private Site createSite(Cursor cursor){
        long id=cursor.getLong(0);
        String name=cursor.getString(1);
        double lat=cursor.getDouble(2);
        double lng=cursor.getDouble(3);
        return new Site(id,name,lat,lng);
    }

}
