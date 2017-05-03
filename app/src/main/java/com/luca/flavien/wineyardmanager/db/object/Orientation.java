package com.luca.flavien.wineyardmanager.db.object;

import java.io.Serializable;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: object
 *
 * Description: The Object Orientation for refelect the DB, we use in the code for recuperate informations
                we implements Serializable for use it in a intent (parameters)
 */

public class Orientation implements Serializable {
    private int id;
    private String name;

    public Orientation(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return name;
    }
}
