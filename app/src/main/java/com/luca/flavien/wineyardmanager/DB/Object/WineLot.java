package com.luca.flavien.wineyardmanager.DB.Object;

import java.io.Serializable;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WineLot implements Serializable{
    private int id;
    private String name ;
    private float surface;
    private int numberWineStock;
    private String picture;
    private WineVariety wineVariety;
    private int orientationId;

    public WineVariety getWineVariety() {
        return wineVariety;
    }

    public void setWineVariety(WineVariety wineVariety) {
        this.wineVariety = wineVariety;
    }

    public int getOrientationid() {
        return orientationId;
    }

    public void setOrientationid(int orientationid) {
        this.orientationId = orientationid;
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

    @Override
    public String toString() {
        return name;
    }
}
