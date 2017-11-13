package com.iplantas.iplantas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.List;

public class MyPlantsStorageSQLite extends SQLiteOpenHelper implements MyPlantsStorage {

    public MyPlantsStorageSQLite(Context context) {
        super(context, "myplants", null, 1);
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
    public void updatePlant(Plant plant) {

    }

    @Override
    public void updateLastWatered(Plant plant) {

    }

    @Override
    public void deletePlant(Plant plant) {

    }

    @Override
    public List<Plant> listOfPlants() {
        return null;
    }

    @Override
    public List<Plant> listOfPlants(String PlantPlace) {
        return null;
    }
}