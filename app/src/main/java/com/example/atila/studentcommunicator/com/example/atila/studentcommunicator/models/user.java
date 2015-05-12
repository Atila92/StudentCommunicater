package com.example.atila.studentcommunicator.com.example.atila.studentcommunicator.models;

/**
 * Created by Atila on 29-04-2015.
 */
public class user {

    private String email;
    private String name;
    private double longitude;
    private double latitude;

    public user(String email, String name, double longitude, double latitude) {
        this.email = email;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
