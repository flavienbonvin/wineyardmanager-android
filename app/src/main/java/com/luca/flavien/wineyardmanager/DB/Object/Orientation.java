package com.luca.flavien.wineyardmanager.DB.Object;

import java.io.Serializable;

/**
 * Created by Flavien on 24.04.2017.
 */

public class Orientation implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
