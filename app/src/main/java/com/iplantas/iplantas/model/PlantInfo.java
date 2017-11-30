package com.iplantas.iplantas.model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by vicch on 29/11/2017.
 */

public class PlantInfo implements Comparable<PlantInfo>{

    public static final int COLD=0;
    public static final int HEAT=1;

    public static final int SPRING=0;
    public static final int SUMMER=1;
    public static final int AUTUMN=2;
    public static final int WINTER=3;

    public static final int MIN=0;
    public static final int MAX=1;

    private String name;
    private String type;
    private String family;
    private int[] watering;
    private int[] sun;
    private int[] soil;
    private String soilType;
    private String[] prune;
    private String[] flowering;
    private float[] optimalTemp;
    private String location;
    private String url;

    public PlantInfo(String csvLine){
        this.name = "";
        this.type = "";
        this.family = "";
        this.watering=new int[2];
        this.sun=new int[2];
        this.soil=new int[4];
        this.soilType="";
        this.prune=new String[4];
        this.flowering=new String[4];
        this.optimalTemp=new float[2];
        this.location="";
        this.url="";
        this.loadPlantInfo(csvLine);
    }

    public PlantInfo(String name, String type, String family){
        this.name = name;
        this.type = type;
        this.family = family;
        this.watering=new int[2];
        this.sun=new int[2];
        this.soil=new int[4];
        this.soilType="";
        this.prune=new String[4];
        this.flowering=new String[4];
        this.optimalTemp=new float[2];
        this.location="";
        this.url="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getWatering(int mode) {
        return watering[mode];
    }

    public void setWatering(int watering, int mode) {
        this.watering[mode] = watering;
    }

    public int getSun(int mode) {
        return sun[mode];
    }

    public void setSun(int sun, int mode) {
        this.sun[mode] = sun;
    }

    public int getSoil(int season) {
        return soil[season];
    }

    public void setSoil(int soil, int season) {
        this.soil[season] = soil;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getPrune(int season) {
        return prune[season];
    }

    public void setPrune(String prune, int season) {
        this.prune[season] = prune;
    }

    public String getFlowering(int season) {
        return flowering[season];
    }

    public void setFlowering(String flowering, int season) {
        this.flowering[season] = flowering;
    }

    public float getOptimalTemp(int mode) {
        return optimalTemp[mode];
    }

    public void setOptimalTemp(float optimalTemp, int mode) {
        this.optimalTemp[mode] = optimalTemp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(@NonNull PlantInfo o) {
        return (this.name.compareTo(o.name));
    }

    private void loadPlantInfo(String csvLine){
        String[] columns=csvLine.split(";");
        this.type=columns[0];
        this.name=columns[1];
        this.family=columns[2];

        this.watering[PlantInfo.COLD]=Integer.parseInt(columns[3]);
        this.watering[PlantInfo.HEAT]=Integer.parseInt(columns[4]);

        this.sun[PlantInfo.COLD]=Integer.parseInt(columns[5]);
        this.sun[PlantInfo.HEAT]=Integer.parseInt(columns[6]);

        this.soil[PlantInfo.SPRING]=Integer.parseInt(columns[7]);
        this.soil[PlantInfo.SUMMER]=Integer.parseInt(columns[8]);
        this.soil[PlantInfo.AUTUMN]=Integer.parseInt(columns[9]);
        this.soil[PlantInfo.WINTER]=Integer.parseInt(columns[10]);

        this.soilType=columns[11];

        this.prune[PlantInfo.SPRING]=columns[12];
        this.prune[PlantInfo.SUMMER]=columns[13];
        this.prune[PlantInfo.AUTUMN]=columns[14];
        this.prune[PlantInfo.WINTER]=columns[15];

        this.flowering[PlantInfo.SPRING]=columns[16];
        this.flowering[PlantInfo.SUMMER]=columns[17];
        this.flowering[PlantInfo.AUTUMN]=columns[18];
        this.flowering[PlantInfo.WINTER]=columns[19];

        this.optimalTemp[PlantInfo.MIN]=Integer.parseInt(columns[20]);
        this.optimalTemp[PlantInfo.MAX]=Integer.parseInt(columns[21]);

        this.location=columns[22];

        this.url=columns[23];
    }

    private int nowSeason(){
        Date now=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month_day = month * 100 + day;
        if (month_day <= 319) { //19 Marzo
            return PlantInfo.WINTER;
        }
        else if (month_day <= 620) { //21 Junio
            return PlantInfo.SPRING;
        }
        else if (month_day <= 921) { //22 Septiembre
            return PlantInfo.SUMMER;
        }
        else if (month_day <= 1220) {
            return PlantInfo.AUTUMN;
        }
        else {
            return PlantInfo.WINTER;
        }
    }

    private int coldHeat(int temp){
        if(temp<=22){
            return PlantInfo.COLD;
        }
        return PlantInfo.HEAT;
    }

    public int getRecomendedWatering(int temp){
        return this.watering[coldHeat(temp)];
    }

    public int getRecomendedSun(int temp){
        return this.sun[coldHeat(temp)];
    }

    public int getRecomendedSoil(){
        return this.soil[nowSeason()];
    }

    public String getRecomendedPrune(){
        return this.prune[nowSeason()];
    }

    public String getRecomendedFlowering(){
        return this.flowering[nowSeason()];
    }

}
