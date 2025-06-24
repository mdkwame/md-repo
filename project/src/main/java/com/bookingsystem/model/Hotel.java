package com.bookingsystem.model;

import java.math.BigDecimal;
import java.util.List;

public class Hotel {
    private String hotelId;
    private String name;
    private String city;
    private String address;
    private int rating;
    private BigDecimal pricePerNight;
    private String currency;
    private int availableRooms;
    private List<String> amenities;
    private String imageUrl;
    private String description;
    private double latitude;
    private double longitude;

    // Constructors
    public Hotel() {}

    public Hotel(String hotelId, String name, String city, String address, int rating, 
                 BigDecimal pricePerNight, String currency, int availableRooms, 
                 List<String> amenities, String imageUrl, String description, 
                 double latitude, double longitude) {
        this.hotelId = hotelId;
        this.name = name;
        this.city = city;
        this.address = address;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.currency = currency;
        this.availableRooms = availableRooms;
        this.amenities = amenities;
        this.imageUrl = imageUrl;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getHotelId() { return hotelId; }
    public void setHotelId(String hotelId) { this.hotelId = hotelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(int availableRooms) { this.availableRooms = availableRooms; }

    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}