package com.iplantas.iplantas.persistence;

import com.iplantas.iplantas.model.Site;

import java.util.List;

/**
 * Created by vicch on 14/11/2017.
 */

public interface MySiteStorage {

    public List<Site> getSites();

    public List<Site> searchSites(String text);

    public long insertSite(String name, double lat, double lng);

    public int updateSite(long id, String name, double lat, double lng);

    public int deleteSite(long id);

}
