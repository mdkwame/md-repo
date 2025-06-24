package com.bookingsystem.model;

import java.math.BigDecimal;
import java.util.List;

public class Car {
    private String carId;
    private String brand;
    private String model;
    private String category;
    private BigDecimal pricePerDay;
    private String currency;
    private int availableCount;
    private String fuelType;
    private String transmission;
    private int seats;
    private int doors;
    private boolean airConditioning;
    private String location;
    private List<String> features;
    private String imageUrl;

    // Constructors
    public Car() {}

    public Car(String carId, String brand, String model, String category, BigDecimal pricePerDay,
               String currency, int availableCount, String fuelType, String transmission,
               int seats, int doors, boolean airConditioning, String location,
               List<String> features, String imageUrl) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.category = category;
        this.pricePerDay = pricePerDay;
        this.currency = currency;
        this.availableCount = availableCount;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.seats = seats;
        this.doors = doors;
        this.airConditioning = airConditioning;
        this.location = location;
        this.features = features;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getAvailableCount() { return availableCount; }
    public void setAvailableCount(int availableCount) { this.availableCount = availableCount; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public int getDoors() { return doors; }
    public void setDoors(int doors) { this.doors = doors; }

    public boolean isAirConditioning() { return airConditioning; }
    public void setAirConditioning(boolean airConditioning) { this.airConditioning = airConditioning; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}