package com.example.hikingmanagement.model;

public class Hike {

    // Fields (types consistent with database)
    private long id;              // long for id
    private String name;
    private String location;
    private String date;
    private double length;        // double for length/distance
    private String description;
    private String difficulty;
    private String weather;
    private String parking;

    // Constructors
    public Hike() {
        // Empty constructor for database operations
    }

    // Constructor with ID
    public Hike(long id, String name, String location, String date, double length,
                String description, String difficulty, String weather, String parking) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.description = description;
        this.difficulty = difficulty;
        this.weather = weather;
        this.parking = parking;
    }

    // Constructor without ID (for new hikes)
    public Hike(String name, String location, String date, double length,
                String description, String difficulty, String weather, String parking) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.description = description;
        this.difficulty = difficulty;
        this.weather = weather;
        this.parking = parking;
    }

    // Getters and Setters with consistent types

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    // Optional terrain method, keep or remove depending on your use case
    public boolean getTerrain() {
        return false;
    }
}
