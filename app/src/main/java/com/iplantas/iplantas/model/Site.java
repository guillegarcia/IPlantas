package com.iplantas.iplantas.model;

/**
 * Created by vicch on 13/11/2017.
 */

public class Site {

    private long id;
    private String name;
    private double lat;
    private double lng;

    public Site(long id, String name) {
        this.id=id;
        this.name=name;
        this.lat=-1;
        this.lng=-1;
    }

    public Site(long id, String name, double lat, double lng) {
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

    public static class SiteBuilder{

        private long id=0;
        private String name="";
        private double lat=0;
        private double lng=0;

        public static SiteBuilder site(){
            return new SiteBuilder();
        }

        public SiteBuilder withId(long id){
            this.id=id;
            return this;
        }

        public SiteBuilder withName(String name){
            this.name=name;
            return this;
        }

        public SiteBuilder withLatLng(double lat, double lng){
            this.lat=lat;
            this.lng=lng;
            return this;
        }

        public Site build(){
            return new Site(this.id,this.name,this.lat,this.lng);
        }

    }

}
