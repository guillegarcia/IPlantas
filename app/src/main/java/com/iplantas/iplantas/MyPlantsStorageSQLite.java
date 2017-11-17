package com.iplantas.iplantas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.ref.PhantomReference;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MyPlantsStorageSQLite extends SQLiteOpenHelper implements MyPlantsStorage {

    private Context context;

    public MyPlantsStorageSQLite(Context context) {
        super(context, "iplantas", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE myplants ("+
                "idPlace INTEGER, "+
                "idPlant INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "idSpecies INTEGER, "+
                "plantName TEXT, " +
                "plantLastWatered DATE, " +
                "plantDataUrl TEXT, " +
                "plantImageUrl TEXT, " +
                "plantDateOfAddition DATE)" );
                //"PRIMARY KEY (idPlace ASC,idPlant ASC))");

        //Available plants
        //TODO: add fields with more information
        db.execSQL("CREATE TABLE plants ("+
                "id INTEGER PRIMARY KEY ASC, " +
                "plantName TEXT, " +
                "plantDataUrl TEXT " +
                ")");

        //INITIAL DATA
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
    public void addPlant(Plant plant) {
        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
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
        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
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
        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
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

        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
        //TODO: make no casesensitive
        Cursor cursor = db.rawQuery("SELECT id,plantName,plantDataUrl FROM plants WHERE plantName LIKE ?",
                new String[]{"%"+searchString+"%"});

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