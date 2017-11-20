package com.iplantas.iplantas.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.Site;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicch on 20/11/2017.
 */

public class MyStorageSQLite extends SQLiteOpenHelper implements MyStorage {

    private static final String DB_NAME="iplantas.db";

    private Context context;

    public MyStorageSQLite(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS SITIO"+
                " ( id INTEGER PRIMARY KEY,"+
                " name TEXT NOT NULL UNIQUE, "+
                " type INTEGER, "+
                " lat REAL, "+
                " lng REAL )");

        db.execSQL("CREATE TABLE myplants (" +
                "idPlace INTEGER, " +
                "idPlant INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idSpecies INTEGER, " +
                "plantName TEXT, " +
                "plantLastWatered DATE, " +
                "plantDataUrl TEXT, " +
                "plantImageUrl TEXT, " +
                "plantDateOfAddition DATE)");

        db.execSQL("CREATE TABLE plants (" +
                "id INTEGER PRIMARY KEY ASC, " +
                "plantName TEXT, " +
                "plantDataUrl TEXT )");

        initializePlants(db);

    }

    private void initializePlants(SQLiteDatabase db){
        ContentValues insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Geranio");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Petunia");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Petunia");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Tulipan");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Tulipan");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Rosa");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Rosa");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Begonia");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Begonia");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Camelia");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Camelia");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Hortensia");
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Hortensia");
        db.insert("plants", null, insertPlantValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public List<Site> getSites() {
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, type, lat, lng FROM SITIO order by name";
        Cursor cursor=db.rawQuery(SQL,null);
        return this.prepareList(cursor);
    }

    @Override
    public Site getSiteById(long id) {
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, type, lat, lng FROM SITIO WHERE id=?";
        Cursor cursor=db.rawQuery(SQL,new String[]{id+""});
        cursor.moveToFirst();
        return createSite(cursor);
    }

    @Override
    public List<Site> searchSites(String text) {
        SQLiteDatabase db=this.getReadableDatabase();
        String SQL="SELECT id, name, type, lat, lng FROM SITIO WHERE LOWER(name) LIKE ? order by name";
        Cursor cursor=db.rawQuery(SQL,new String[]{"'%"+text+"%'"});
        return this.prepareList(cursor);
    }

    @Override
    public long insertSite(Site site) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",site.getName());
        cv.put("type",site.getType());
        cv.put("lat",site.getLat());
        cv.put("lng",site.getLng());
        return db.insert("SITIO",null,cv);
    }

    @Override
    public int updateSite(Site site) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",site.getName());
        cv.put("type",site.getType());
        cv.put("lat",site.getLat());
        cv.put("lng",site.getLng());
        return db.update("SITIO",cv,"id=?", new String[]{site.getId()+""});
    }

    @Override
    public int deleteSite(long id) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("SITIO","id=?",new String[]{id+""});
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
        int type=cursor.getInt(2);
        double lat=cursor.getDouble(3);
        double lng=cursor.getDouble(4);
        return new Site(id,name,type,lat,lng);
    }

    @Override
    public void addPlant(Plant plant) {
        SQLiteDatabase db = getWritableDatabase();
        int idPlace = plant.getIdPlace();
        int idPlant = plant.getIdPlant();
        int idSpecies = plant.getIdSpecies();
        String plantName = plant.getPlantName();
        Date plantLastWatered = plant.getPlantLastWatered();
        String plantDataUrl = plant.getPlantDataUrl();
        String plantImageUrl = plant.getPlantImageUrl();
        Date plantDateOfAddition = plant.getPlantDateOfAddition();

        db.execSQL("INSERT INTO myplants VALUES ('"
                + idPlace + "', '"
                + idPlant + "', '"
                + idSpecies + "', '"
                + plantName + "', '"
                + plantLastWatered + "', '"
                + plantDataUrl + "', '"
                + plantImageUrl + "', '"
                + plantDateOfAddition + "')");
        db.close();
        String text = "Planta " + plantName + " guardada";
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateLastWatered(Plant plant) {
        SQLiteDatabase db = getWritableDatabase();
        int idPlace = plant.getIdPlace();
        int idPlant = plant.getIdPlant();
        Date plantLastWatered = plant.getPlantLastWatered();

        db.execSQL("UPDATE myplants SET plantLastWatered = '" + plantLastWatered
                + "' WHERE idPlace = '" + idPlace
                + "' AND idPlant = '" + idPlant + "'"
        );
        db.close();
    }

    @Override
    public void deletePlant(Plant plant) {
        SQLiteDatabase db = getWritableDatabase();
        int idPlace = plant.getIdPlace();
        int idPlant = plant.getIdPlant();

        db.execSQL("DELETE FROM myplants WHERE plantPlace = '" + idPlace
                + "' AND plantName = '" + idPlant + "'"
        );
        db.close();
    }

    @Override
    public List<Plant> listOfPlants() {
        List<Plant> listMyPlants = new ArrayList<Plant>();
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();
        String[] CAMPOS = {"idPlace",
                "idPlant",
                "idSpecies",
                "plantName",
                "plantLastWatered",
                "plantDataUrl",
                "plantImageUrl",
                "plantDateOfAddition"};

        Cursor cursor=db.query("myplants", CAMPOS, null, null,
                null, null, "plantPlace ASC, plantName ASC, plantLastWatered ASC", null);
        while (cursor.moveToNext()){
            int idPlace = cursor.getInt(0);
            int idPlant = cursor.getInt(1);
            int idSpecies = cursor.getInt(2);
            String plantName = cursor.getString(3);
            Date plantLastWatered = new java.sql.Date(java.util.Date.parse(cursor.getString(4)));
            String plantDataUrl = cursor.getString(5);
            String plantImageUrl = cursor.getString(6);
            Date plantDateOfAddition = new java.sql.Date(java.util.Date.parse(cursor.getString(7)));
            Plant plantInList = new Plant(idPlace, idPlant,idSpecies,plantName,
                    plantLastWatered, plantDataUrl, plantImageUrl,plantDateOfAddition);
            listMyPlants.add(plantInList);
        }
        cursor.close();
        db.close();
        return listMyPlants;
    }

    @Override
    public List<Plant> listOfPlants(String plantPlace) {
        List<Plant> listMyPlants = new ArrayList<Plant>();
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();
        String[] CAMPOS = {"idPlace",
                "idPlant",
                "idSpecies",
                "plantName",
                "plantLastWatered",
                "plantDataUrl",
                "plantImageUrl",
                "plantDateOfAddition"};
        Cursor cursor = db.rawQuery("SELECT * FROM myplants" + " WHERE idPlace = '" + plantPlace + "'", null);
        while (cursor.moveToNext()){
            int idPlace = cursor.getInt(0);
            int idPlant = cursor.getInt(1);
            int idSpecies = cursor.getInt(2);
            String plantName = cursor.getString(3);
            Date plantLastWatered = new java.sql.Date(java.util.Date.parse(cursor.getString(4)));
            String plantDataUrl = cursor.getString(5);
            String plantImageUrl = cursor.getString(6);
            Date plantDateOfAddition = new java.sql.Date(java.util.Date.parse(cursor.getString(7)));
            Plant plantInList = new Plant(idPlace, idPlant,idSpecies,plantName,
                    plantLastWatered, plantDataUrl, plantImageUrl,plantDateOfAddition);
            listMyPlants.add(plantInList);
        }
        cursor.close();
        db.close();
        return listMyPlants;
    }


    @Override
    public List<Plant> searchPlants(String searchString) {
        List<Plant> plants = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String sql="SELECT id,plantName,plantDataUrl FROM plants WHERE plantName LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+searchString+"%"});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String url = cursor.getString(2);

            Plant plant = new Plant();
            plant.setIdSpecies(id);
            plant.setPlantName(name);
            plant.setPlantDataUrl(url);

            plants.add(plant);
        }
        cursor.close();
        db.close();

        return plants;
    }

}
