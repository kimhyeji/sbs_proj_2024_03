package org.example.dao;

public class Dao {
    protected int lastId;

    public Dao() {
        lastId = 0;
    }
    public int getNewId() {
        return lastId + 1;
    }

    public int getLastId() {
        return lastId;
    }
}
