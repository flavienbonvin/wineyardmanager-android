package com.luca.flavien.wineyardmanager.DB.Object;

import java.io.Serializable;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WineVariety implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
