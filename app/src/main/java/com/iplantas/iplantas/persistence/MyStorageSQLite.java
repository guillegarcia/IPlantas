package com.iplantas.iplantas.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.iplantas.iplantas.R;
import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.Site;

import java.util.Date;
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
                "idPlant INTEGER PRIMARY KEY, " +
                "idSpecies INTEGER, " +
                "plantName TEXT, " +
                "plantLastWatered DATE, " +
                "plantDataUrl TEXT, " +
                "plantImageUrl TEXT, " +
                "plantDateOfAddition DATE)");

        db.execSQL("CREATE TABLE plants (" +
                "id INTEGER PRIMARY KEY ASC, " +
                "plantName TEXT, " +
                "plantImage INTEGER, " +
                "plantDataUrl TEXT )");

        initializePlants(db);

    }

    private void initializePlants(SQLiteDatabase db){
        ContentValues insertPlantValues = new ContentValues();
        insertPlantValues.put("plantName","Geranio");
        insertPlantValues.put("plantImage", R.drawable.img_geranio);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Lavanda");
        insertPlantValues.put("plantImage", R.drawable.img_lavanda);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Hortensia");
        insertPlantValues.put("plantImage", R.drawable.img_hortensia);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Tulipan");
        insertPlantValues.put("plantImage", R.drawable.img_tulipan);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Hiedra");
        insertPlantValues.put("plantImage", R.drawable.img_hiedra);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Petunia");
        insertPlantValues.put("plantImage", R.drawable.img_petunia);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Cintas");
        insertPlantValues.put("plantImage", R.drawable.img_cintas);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Dracena");
        insertPlantValues.put("plantImage", R.drawable.img_dracena);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Areca");
        insertPlantValues.put("plantImage", R.drawable.img_areca);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Azalea");
        insertPlantValues.put("plantImage", R.drawable.img_azalea);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Potos");
        insertPlantValues.put("plantImage", R.drawable.img_potos);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Bambu");
        insertPlantValues.put("plantImage", R.drawable.img_bambu);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Shefflera");
        insertPlantValues.put("plantImage", R.drawable.img_schefflera);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
        db.insert("plants", null, insertPlantValues);

        insertPlantValues.put("plantName","Clavel");
        insertPlantValues.put("plantImage", R.drawable.img_clavel);
        insertPlantValues.put("plantDataUrl","https://es.wikipedia.org/wiki/Geranium");
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
        long idPlace = plant.getIdPlace();
        long idPlant = plant.getIdPlant();
        int idSpecies = plant.getIdSpecies();
        String plantName = plant.getPlantName();
        Date plantLastWatered = plant.getPlantLastWatered();
        String plantDataUrl = plant.getPlantDataUrl();
        String plantImageUrl = plant.getPlantImageUrl();
        Date plantDateOfAddition = new Date(System.currentTimeMillis());

        db.execSQL("INSERT INTO myplants (" +
                        "idPlace, " +
//                        "idPlant, " +
                        "idSpecies, " +
                        "plantName, " +
                        "plantLastWatered, " +
                        "plantDataUrl, " +
                        "plantImageUrl, " +
                        "plantDateOfAddition)"+
                "VALUES ('"
                + idPlace + "', '"
//                + idPlant + "', '"
                + idSpecies + "', '"
                + plantName + "', '"
                + plantLastWatered + "', '"
                + plantDataUrl + "', '"
                + plantImageUrl + "', '"
                + plantDateOfAddition + "')");
        String inserted = "VALUES ('"
                + idPlace + "', '"
//                + idPlant + "', '"
                + idSpecies + "', '"
                + plantName + "', '"
                + plantLastWatered + "', '"
                + plantDataUrl + "', '"
                + plantImageUrl + "', '"
                + plantDateOfAddition + "')";
        db.close();
        //String text = "Planta " + plantName + " guardada";
        //Toast.makeText(context,inserted,Toast.LENGTH_LONG).show();
        Log.i("Planta insertada: ",inserted,null);
    }

    @Override
    public void updateLastWatered(Plant plant) {
        SQLiteDatabase db = getWritableDatabase();
        long idPlace = plant.getIdPlace();
        long idPlant = plant.getIdPlant();
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
        long idPlace = plant.getIdPlace();
        long idPlant = plant.getIdPlant();

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
            long idPlace = cursor.getLong(0);
            long idPlant = cursor.getLong(1);
            int idSpecies = cursor.getInt(2);
            String plantName = cursor.getString(3);
            Date plantLastWatered = new java.sql.Date(java.util.Date.parse(cursor.getString(4)));
            String plantDataUrl = cursor.getString(5);
            String plantImageUrl = cursor.getString(6);
            Date plantDateOfAddition = new java.sql.Date(java.util.Date.parse(cursor.getString(7)));
            Plant plantInList = new Plant(idPlace, idPlant,idSpecies,plantName,
                    plantLastWatered, plantDataUrl, plantImageUrl,plantDateOfAddition);
            Log.d("Comprobar listado", "listOfPlants: " + plantInList.toString());
            listMyPlants.add(plantInList);
        }
        cursor.close();
        db.close();
        return listMyPlants;
    }

    @Override
    public List<Plant> listOfPlants(long idPlace) {
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
        Cursor cursor = db.rawQuery("SELECT * FROM myplants" + " WHERE idPlace = '" + idPlace + "'", null);
        while (cursor.moveToNext()){
            //long idPlace = cursor.getInt(0);
            long idPlant = cursor.getLong(1);
            int idSpecies = cursor.getInt(2);
            String plantName = cursor.getString(3);
            Date plantLastWatered = new java.sql.Date(java.util.Date.parse(cursor.getString(4)));
            String plantDataUrl = cursor.getString(5);
            String plantImageUrl = cursor.getString(6);
            Date plantDateOfAddition = new java.sql.Date(java.util.Date.parse(cursor.getString(7)));
            Plant plantInList = new Plant(idPlace, idPlant,idSpecies,plantName,
                    plantLastWatered, plantDataUrl, plantImageUrl,plantDateOfAddition);
            Log.d("Listado con id", "listOfPlants: " + plantInList.toString());
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
        String sql="SELECT id,plantName,plantImage,plantDataUrl FROM plants WHERE plantName LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+searchString+"%"});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int image = cursor.getInt(2);
            String url = cursor.getString(3);

            Plant plant = new Plant();
            plant.setIdSpecies(id);
            plant.setPlantName(name);
            plant.setPlantImage(image);
            plant.setPlantDataUrl(url);

            plants.add(plant);
        }
        cursor.close();
        db.close();

        return plants;
    }

}
