package com.luca.flavien.wineyardmanager.db.object;

import java.io.Serializable;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: object
 *
 * Description: The Object WineVariety for refelect the DB, we use in the code for recuperate informations
                we implements Serializable for use it in a intent (parameters)
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
