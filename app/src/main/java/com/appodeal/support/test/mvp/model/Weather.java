package com.appodeal.support.test.mvp.model;

public class Weather {//for list_activity
    private long id;
    private String name;
    private int idImage;

    public Weather() {
    }

    public Weather(long id, String name, int idImage) {
        this.id = id;
        this.name = name;
        this.idImage = idImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }
}
