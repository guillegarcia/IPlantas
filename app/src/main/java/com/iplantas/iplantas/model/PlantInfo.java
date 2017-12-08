package com.iplantas.iplantas.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static final Date EMPTY_DATE=new GregorianCalendar(2000,0,1,0,0,0).getTime();

    private int id;
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
    private String img;

    public PlantInfo(String csvLine){
        this.id=0;
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
        this.img="";
        this.loadPlantInfo(csvLine);
    }

    public int getId(){
        return this.id;
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

    public String getImg(){
        return this.img;
    }

    public void setImg(String img){
        this.img=img;
    }

    public int getImgResourceId(Context context){
        Resources resources=context.getResources();
        return resources.getIdentifier(this.img,"drawable",context.getPackageName());
    }

    @Override
    public int compareTo(@NonNull PlantInfo o) {
        return (this.name.compareTo(o.name));
    }

    private void loadPlantInfo(String csvLine){
        String[] columns=csvLine.split(";");
        this.id=Integer.parseInt(columns[0]);
        this.type=columns[1];
        this.name=columns[2];
        this.family=columns[3];

        this.watering[PlantInfo.COLD]=Integer.parseInt(columns[4]);
        this.watering[PlantInfo.HEAT]=Integer.parseInt(columns[5]);

        this.sun[PlantInfo.COLD]=Integer.parseInt(columns[6]);
        this.sun[PlantInfo.HEAT]=Integer.parseInt(columns[7]);

        this.soil[PlantInfo.SPRING]=Integer.parseInt(columns[8]);
        this.soil[PlantInfo.SUMMER]=Integer.parseInt(columns[9]);
        this.soil[PlantInfo.AUTUMN]=Integer.parseInt(columns[10]);
        this.soil[PlantInfo.WINTER]=Integer.parseInt(columns[11]);

        this.soilType=columns[12];

        this.prune[PlantInfo.SPRING]=columns[13];
        this.prune[PlantInfo.SUMMER]=columns[14];
        this.prune[PlantInfo.AUTUMN]=columns[15];
        this.prune[PlantInfo.WINTER]=columns[16];

        this.flowering[PlantInfo.SPRING]=columns[17];
        this.flowering[PlantInfo.SUMMER]=columns[18];
        this.flowering[PlantInfo.AUTUMN]=columns[19];
        this.flowering[PlantInfo.WINTER]=columns[20];

        this.optimalTemp[PlantInfo.MIN]=Integer.parseInt(columns[21]);
        this.optimalTemp[PlantInfo.MAX]=Integer.parseInt(columns[22]);

        this.location=columns[23];

        this.url=columns[24];

        this.img=columns[25];
    }

    //Obtiene la estacion del año con la fecha actual.
    private int getCurrentSeason(){
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
        else if (month_day <= 1220) { // Diciembre
            return PlantInfo.AUTUMN;
        }
        else {
            return PlantInfo.WINTER;
        }
    }

    //Obtiene la temperatura media de ciertos paralelos
    private int getTemperature(float lat){
        if(lat>=0){
            return getTemperatureNorth(lat);
        }
        else{
            return getTemperatureSouth(lat);
        }
    }

    //Paraleos al norte del ecuador
    private int getTemperatureNorth(float lat){
        int season=getCurrentSeason();
        if(lat<=30){
            return PlantInfo.HEAT;
        }
        else if(lat<=42){
            if(season==PlantInfo.WINTER){
                return PlantInfo.COLD;
            }
            return PlantInfo.HEAT;
        }
        else if(lat<=55){
            if(season==PlantInfo.SUMMER){
                return PlantInfo.HEAT;
            }
            return PlantInfo.COLD;
        }
        else{
            return PlantInfo.COLD;
        }
    }

    //Paralelos al sud del ecuador
    private int getTemperatureSouth(float lat){
        int season=getCurrentSeason();
        if(lat>=-30){
            return PlantInfo.HEAT;
        }
        else if(lat>=-42){
            if(season==PlantInfo.SUMMER){
                return PlantInfo.COLD;
            }
            return PlantInfo.HEAT;
        }
        else if(lat>=-55){
            if(season==PlantInfo.WINTER){
                return PlantInfo.HEAT;
            }
            return PlantInfo.COLD;
        }
        else{
            return PlantInfo.COLD;
        }
    }

    private Date addDateToCurrent(int days){
        Calendar cal = Calendar.getInstance();
        if(days>0) {
            Date now = new Date();
            cal.setTime(now);
            cal.add(Calendar.DATE, days);
            return cal.getTime();
        }
        return EMPTY_DATE;
    }

    //Obtener fecha proximo riego
    public Date getNextWateringDate(float lat){
        return addDateToCurrent(getRecomendedWatering(lat));
    }

    public String getNextWateringDateFormat(float lat){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(getNextWateringDate(lat));
    }

    //Obtener fecha proximo abono
    public Date getNextSoilDate(float lat){
        return addDateToCurrent(getRecomendedSoil());
    }

    public String getNextSoilDateFormat(float lat){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(getNextSoilDate(lat));
    }

    //Devuelve los dias de riego
    public int getRecomendedWatering(float lat){
        return this.watering[getTemperature(lat)];
    }

    //Devuelve un valor de Sol
    public int getRecomendedSun(float lat){
        return this.sun[getTemperature(lat)];
    }

    //Devuelve los dias de abono
    public int getRecomendedSoil(){
        return this.soil[getCurrentSeason()];
    }

    //Devuelve si se debe podar en esta estacion
    public String getRecomendedPrune(){
        return this.prune[getCurrentSeason()];
    }

    //Devuelve si florecera esta estacion
    public String getRecomendedFlowering(){
        return this.flowering[getCurrentSeason()];
    }



}
