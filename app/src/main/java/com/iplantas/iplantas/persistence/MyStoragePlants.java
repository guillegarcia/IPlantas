package com.iplantas.iplantas.persistence;

import com.iplantas.iplantas.model.PlantInfo;

import java.util.List;

/**
 * Created by vicch on 01/12/2017.
 */

public interface MyStoragePlants {

    public List<PlantInfo> getPlantsInfo();

    public PlantInfo getPlantInfoByName(String name);

    public PlantInfo getPlantInfoByType(String type);

}
