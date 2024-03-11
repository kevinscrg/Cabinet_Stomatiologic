package com.example.lab4.Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected final int id;
    public Entity(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

}
