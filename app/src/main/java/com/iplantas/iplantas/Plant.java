package com.iplantas.iplantas;

import java.sql.Date;

public class Plant {
    private String plantPlace = "";
    private String plantName = "";
    private Date plantLastWatered = new Date(0);
    private String plantDataUrl = "";
    private Date plantDateOfAddition = new Date(0);
    private final static Date baseDate = new Date(0);
    public static final Plant Plant_EMPTY =
            new Plant("","",baseDate,"",baseDate);

    public Plant() {
    }

    public Plant(String plantPlace, String plantName, Date plantLastWatered, String plantDataUrl, Date plantDateOfAddition) {
        this.plantPlace = plantPlace;
        this.plantName = plantName;
        this.plantLastWatered = plantLastWatered;
        this.plantDataUrl = plantDataUrl;
        this.plantDateOfAddition = plantDateOfAddition;
    }

    public String getPlantPlace() {
        return plantPlace;
    }

    public void setPlantPlace(String plantPlace) {
        this.plantPlace = plantPlace;
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

    public Date getPlantDateOfAddition() {
        return plantDateOfAddition;
    }

    public void setPlantDateOfAddition(Date plantDateOfAddition) {
        this.plantDateOfAddition = plantDateOfAddition;
    }

    public static class PlantBuilder{
        private String plantPlace = "";
        private String plantName = "";
        private Date plantLastWatered = new Date(0);
        private String plantDataUrl = "";
        private Date plantDateOfAddition = new Date(0);

        public PlantBuilder withPlantPlace(String plantPlace){
            this.plantPlace = plantPlace;
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

        public PlantBuilder withPlantDateOfAddition(Date plantDateOfAddition){
            this.plantDateOfAddition = plantDateOfAddition;
            return this;
        }

        public Plant buildPlant(){
            return new Plant(plantPlace, plantName, plantLastWatered, plantDataUrl, plantDateOfAddition);
        }
    }
}