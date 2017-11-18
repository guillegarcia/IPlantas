package com.iplantas.iplantas.persistence;

import com.iplantas.iplantas.model.Site;

import java.util.List;

/**
 * Created by vicch on 14/11/2017.
 */

public interface MySiteStorage {

    public List<Site> getSites();

    public Site getSiteById(long id);

    public List<Site> searchSites(String text);

    public long insertSite(Site site);

    public int updateSite(Site site);

    public int deleteSite(long id);

}
