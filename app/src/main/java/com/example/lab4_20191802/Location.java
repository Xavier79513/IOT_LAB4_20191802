package com.example.lab4_20191802;

public class Location {
    private String id;
    private String name;
    private String region;
    private String country;
    private String lat;
    private String lon;

    public Location(String id, String name, String region, String country, String lat, String lon) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}