package com.iplantas.iplantas;

import java.sql.Date;

public class Plant {
    private int idPlace = 0;
    private int idPlant = 0 ;
    private int idSpecies = 0;
    private String plantName = "";
    private Date plantLastWatered = new Date(0);
    private String plantDataUrl = "";
    private String plantImageUrl = "";
    private Date plantDateOfAddition = new Date(0);
    private final static Date baseDate = new Date(0);
    public static final Plant Plant_EMPTY =
            new Plant(0,0,0,"",baseDate,"","",baseDate);

    public Plant(){}
    public Plant(int idPlace, int idPlant, int idSpecies, String plantName,
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

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }


    public int getIdPlant() {
        return idPlant;
    }

    public void setIdPlant(int idPlant) {
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
    /*
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    */
    public static class PlantBuilder{
        private int idPlace = 0;
        private int idPlant = 0 ;
        private int idSpecies = 0;
        private String plantName = "";
        private Date plantLastWatered = new Date(0);
        private String plantDataUrl = "";
        private String plantImageUrl = "";
        private Date plantDateOfAddition = new Date(0);

        public PlantBuilder withIdPlace(int idPlace){
            this.idPlace = idPlace;
            return this;
        }

        public PlantBuilder withIdPlant(int idPlant){
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