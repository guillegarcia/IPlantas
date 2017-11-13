package com.iplantas.iplantas;

import java.util.List;

public interface MyPlantsStorage {
    //public Plant selectPlant(Plant plant);
    public void addPlant(Plant plant);
    public void updatePlant(Plant plant);
    public void updateLastWatered(Plant plant);
    public void deletePlant(Plant plant);
    public List<Plant> listOfPlants();
    public List<Plant> listOfPlants(String PlantPlace);
}