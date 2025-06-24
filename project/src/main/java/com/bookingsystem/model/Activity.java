package com.bookingsystem.model;

import java.math.BigDecimal;
import java.util.List;

public class Activity {
    private String activityId;
    private String name;
    private String category;
    private String city;
    private String description;
    private BigDecimal price;
    private String currency;
    private int availableSpots;
    private String duration;
    private int rating;
    private List<String> highlights;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String difficulty;
    private int minAge;

    // Constructors
    public Activity() {}

    public Activity(String activityId, String name, String category, String city, String description,
                    BigDecimal price, String currency, int availableSpots, String duration,
                    int rating, List<String> highlights, String imageUrl, double latitude,
                    double longitude, String difficulty, int minAge) {
        this.activityId = activityId;
        this.name = name;
        this.category = category;
        this.city = city;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.availableSpots = availableSpots;
        this.duration = duration;
        this.rating = rating;
        this.highlights = highlights;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.difficulty = difficulty;
        this.minAge = minAge;
    }

    // Getters and Setters
    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getAvailableSpots() { return availableSpots; }
    public void setAvailableSpots(int availableSpots) { this.availableSpots = availableSpots; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getMinAge() { return minAge; }
    public void setMinAge(int minAge) { this.minAge = minAge; }
}