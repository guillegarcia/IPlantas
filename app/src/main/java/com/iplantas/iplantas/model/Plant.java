package com.iplantas.iplantas.model;

import com.iplantas.iplantas.R;

import java.util.Date;

public class Plant {
    private long idPlace = 0;
    private long idPlant = 0;
    private int idSpecies = 0;
    private String plantName = "";
    private Date plantLastWatered = new Date(0);
    private String plantDataUrl = "";
    private String plantImageUrl = "";
    private Date plantDateOfAddition = new Date(0);
    private final static Date baseDate = new Date(0);
    private int plantImage = R.mipmap.ic_launcher;
    public static final Plant Plant_EMPTY =
            new Plant(0,0,0,"",baseDate,"","",baseDate);

    public Plant(){}
    public Plant(long idPlace, long idPlant, int idSpecies, String plantName,
                 Date plantLastWatered, String plantDataUrl, String plantImageUrl, Date plantDateOfAddition) {
        this.idPlace = idPlace;
        this.idPlant = idPlant;
        this.idSpecies = idSpecies;
        this.plantName = plantName;
        this.plantLastWatered = plantLastWatered;
        this.plantDataUrl = plantDataUrl;
        this.plantImageUrl = plantImageUrl;
        this.plantDateOfAddition = plantDateOfAddition;
    }

    public long getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(long idPlace) {
        this.idPlace = idPlace;
    }


    public long getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(long idPlant) {
        this.idPlant = idPlant;
    }

    public int getIdSpecies() {
        return idSpecies;
    }

    public void setIdSpecies(int idSpecies) {
        this.idSpecies = idSpecies;
    }


    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Date getPlantLastWatered() {
        return plantLastWatered;
    }

    public void setPlantLastWatered(Date plantLastWatered) {
        this.plantLastWatered = plantLastWatered;
    }

    public String getPlantDataUrl() {
        return plantDataUrl;
    }

    public void setPlantDataUrl(String plantDataUrl) {
        this.plantDataUrl = plantDataUrl;
    }

    public String getPlantImageUrl() {
        return plantImageUrl;
    }

    public void setPlantImageUrl(String plantImageUrl) {
        this.plantImageUrl = plantImageUrl;
    }

    public Date getPlantDateOfAddition() {
        return plantDateOfAddition;
    }

    public void setPlantDateOfAddition(Date plantDateOfAddition) {
        this.plantDateOfAddition = plantDateOfAddition;
    }

    public int getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(int plantImage) {
        this.plantImage = plantImage;
    }

    /*
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    */
    public static class PlantBuilder{
        private long idPlace = 0;
        private long idPlant = 0 ;
        private int idSpecies = 0;
        private String plantName = "";
        private Date plantLastWatered = new Date(0);
        private String plantDataUrl = "";
        private String plantImageUrl = "";
        private Date plantDateOfAddition = new Date(0);
        private int plantImage = R.mipmap.ic_launcher;

        public PlantBuilder withIdPlace(long idPlace){
            this.idPlace = idPlace;
            return this;
        }

        public PlantBuilder withIdPlant(long idPlant){
            this.idPlant = idPlant;
            return this;
        }

        public PlantBuilder withIdSpecies (int idSpecies){
            this.idSpecies = idSpecies;
            return this;
        }

        public PlantBuilder withPlantName(String plantName){
            this.plantName = plantName;
            return this;
        }

        public PlantBuilder withPlantLastWatered(Date plantLastWatered){
            this.plantLastWatered = plantLastWatered;
            return this;
        }

        public PlantBuilder withPlantDataUrl(String plantDataUrl){
            this.plantDataUrl = plantDataUrl;
            return this;
        }

        public PlantBuilder withPlantImageUrl(String plantImageUrl){
            this.plantImageUrl = plantImageUrl;
            return this;
        }

        public PlantBuilder withPlantDateOfAddition(Date plantDateOfAddition){
            this.plantDateOfAddition = plantDateOfAddition;
            return this;
        }

        public Plant buildPlant(){
            return new Plant(idPlace, idPlant,idSpecies, plantName, plantLastWatered,
                    plantDataUrl, plantImageUrl, plantDateOfAddition);
        }
    }
}