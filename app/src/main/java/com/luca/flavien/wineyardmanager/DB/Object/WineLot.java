package com.luca.flavien.wineyardmanager.DB.Object;

import java.io.Serializable;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WineLot implements Serializable {
    private int id;
    private String name ;
    private float surface;
    private int numberWineStock;
    private String picture;
    private int wineVarietyId;
    private int orientationId;

    public int getWineVarietyId() {
        return wineVarietyId;
    }

    public void setWineVarietyId(int wineVarietyId) {
        this.wineVarietyId = wineVarietyId;
    }

    public int getOrientationId() {
        return orientationId;
    }

    public void setOrientationId(int orientationId) {
        this.orientationId = orientationId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSurface() {
        return surface;
    }

    public int getNumberWineStock() {
        return numberWineStock;
    }

    public String getPicture() {
        return picture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public void setNumberWineStock(int numberWineStock) {
        this.numberWineStock = numberWineStock;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
