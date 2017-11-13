package com.iplantas.iplantas.model;

/**
 * Created by vicch on 13/11/2017.
 */

public class Sitio {

    private long id;
    private String name;
    private double lat;
    private double lng;

    public Sitio(long id, String name) {
        this.id=id;
        this.name=name;
        this.lat=-1;
        this.lng=-1;
    }

    public Sitio(long id, String name, double lat, double lng) {
        this.id=id;
        this.name=name;
        this.lat=lat;
        this.lng=lng;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
