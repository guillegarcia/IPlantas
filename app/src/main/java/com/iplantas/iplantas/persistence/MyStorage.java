package com.iplantas.iplantas.persistence;

import com.iplantas.iplantas.model.Plant;
import com.iplantas.iplantas.model.Site;

import java.util.List;

/**
 * Created by vicch on 20/11/2017.
 */

public interface MyStorage {

    public List<Site> getSites();

    public Site getSiteById(long id);

    public List<Site> searchSites(String text);

    public long insertSite(Site site);

    public int updateSite(Site site);

    public int deleteSite(long id);

    /************************************************/

    public void addPlant(Plant plant);

    public void updateLastWatered(Plant plant);

    public void deletePlant(Plant plant);

    public List<Plant> listOfPlants();

    public List<Plant> listOfPlants(long idPlace);

    public List<Plant> searchPlants(String searchString);



}
