package com.iplantas.iplantas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MyPlantsStorageSQLite extends SQLiteOpenHelper implements MyPlantsStorage {

    public MyPlantsStorageSQLite(Context context) {
        super(context, "iplantas", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE myplants ("+
                "plantPlace TEXT, "+
                "plantName TEXT, " +
                "plantLastWatered DATE, " +
                "plantDataUrl TEXT, " +
                "plantDateOfAddition DATE, " +
                "PRIMARY KEY (plantPlace,plantName))");

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
        String plantPlace = plant.getPlantPlace();
        String plantName = plant.getPlantName();
        Date plantLastWatered = plant.getPlantLastWatered();
        String plantDataUrl = plant.getPlantDataUrl();
        Date plantDateOfAddition = plant.getPlantDateOfAddition();

        db.execSQL("INSERT INTO myplants VALUES ('"
                + plantPlace + "', '"
                + plantName + "', '"
                + plantLastWatered + "', '"
                + plantDataUrl + "', '"
                + plantDateOfAddition + "')");
        db.close();
    }

    @Override
    public void updateLastWatered(Plant plant) {
        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
        String plantPlace = plant.getPlantPlace();
        String plantName = plant.getPlantName();
        Date plantLastWatered = plant.getPlantLastWatered();

        db.execSQL("UPDATE myplants SET plantLastWatered = '" + plantLastWatered
                + "' WHERE plantPlace = '" + plantPlace
                + "' AND plantName = '" + plantName + "'"
        );
        db.close();
    }

    @Override
    public void deletePlant(Plant plant) {
        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
        String plantPlace = plant.getPlantPlace();
        String plantName = plant.getPlantName();
        Date plantLastWatered = plant.getPlantLastWatered();
        String plantDataUrl = plant.getPlantDataUrl();
        Date plantDateOfAddition = plant.getPlantDateOfAddition();

        db.execSQL("DELETE FROM myplants WHERE plantPlace = '" + plantPlace
                + "' AND plantName = '" + plantName + "'"
        );
        db.close();
    }

    @Override
    public List<Plant> listOfPlants() {
        return null;
    }

    @Override
    public List<Plant> listOfPlants(String PlantPlace) {
        return null;
    }

    @Override
    public List<Plant> searchPlants(String searchString) {
        List<Plant> plants = new ArrayList<>();

        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
        //TODO: make no casesensitive
        Cursor cursor = db.rawQuery("SELECT id,plantName,plantDataUrl FROM plants WHERE plantName LIKE ?", new String[]{"%"+searchString+"%"});

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String url = cursor.getString(2);

            Plant plant = new Plant();
            plant.setId(id);
            plant.setPlantName(name);
            plant.setPlantDataUrl(url);

            plants.add(plant);
        }
        cursor.close();
        db.close();

        return plants;
    }
}