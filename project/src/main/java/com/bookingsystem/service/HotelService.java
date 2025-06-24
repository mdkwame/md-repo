package com.bookingsystem.service;

import com.bookingsystem.model.Hotel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private List<Hotel> hotels;

    public HotelService() {
        initializeMockData();
    }

    private void initializeMockData() {
        hotels = new ArrayList<>();
        
        hotels.add(new Hotel("HT001", "Grand Plaza Hotel", "New York", "123 Broadway, New York, NY 10001",
                5, new BigDecimal("299.99"), "USD", 12,
                Arrays.asList("WiFi", "Pool", "Gym", "Spa", "Restaurant", "Room Service"),
                "https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg",
                "Luxury hotel in the heart of Manhattan", 40.7128, -74.0060));
        
        hotels.add(new Hotel("HT002", "Seaside Resort", "Miami", "456 Ocean Drive, Miami Beach, FL 33139",
                4, new BigDecimal("189.99"), "USD", 8,
                Arrays.asList("WiFi", "Beach Access", "Pool", "Restaurant", "Bar"),
                "https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg",
                "Beautiful beachfront resort with ocean views", 25.7617, -80.1918));
        
        hotels.add(new Hotel("HT003", "Mountain Lodge", "Denver", "789 Mountain View Rd, Denver, CO 80202",
                3, new BigDecimal("129.99"), "USD", 15,
                Arrays.asList("WiFi", "Parking", "Restaurant", "Hiking Trails"),
                "https://images.pexels.com/photos/338504/pexels-photo-338504.jpeg",
                "Cozy lodge with mountain views and outdoor activities", 39.7392, -104.9903));
        
        hotels.add(new Hotel("HT004", "Business Center Hotel", "Chicago", "321 Michigan Ave, Chicago, IL 60601",
                4, new BigDecimal("219.99"), "USD", 20,
                Arrays.asList("WiFi", "Business Center", "Gym", "Restaurant", "Conference Rooms"),
                "https://images.pexels.com/photos/189296/pexels-photo-189296.jpeg",
                "Modern business hotel in downtown Chicago", 41.8781, -87.6298));
        
        hotels.add(new Hotel("HT005", "Boutique Inn", "San Francisco", "654 Union Square, San Francisco, CA 94108",
                3, new BigDecimal("249.99"), "USD", 6,
                Arrays.asList("WiFi", "Restaurant", "Bar", "Concierge"),
                "https://images.pexels.com/photos/258154/pexels-photo-258154.jpeg",
                "Charming boutique hotel near Union Square", 37.7749, -122.4194));
    }

    public List<Hotel> searchHotels(String city, String checkIn, String checkOut, 
                                   BigDecimal maxPrice, Integer minRating) {
        return hotels.stream()
                .filter(hotel -> city == null || hotel.getCity().toLowerCase().contains(city.toLowerCase()))
                .filter(hotel -> maxPrice == null || hotel.getPricePerNight().compareTo(maxPrice) <= 0)
                .filter(hotel -> minRating == null || hotel.getRating() >= minRating)
                .collect(Collectors.toList());
    }

    public Hotel getHotelById(String hotelId) {
        return hotels.stream()
                .filter(hotel -> hotel.getHotelId().equals(hotelId))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAvailability(String hotelId, int rooms) {
        Hotel hotel = getHotelById(hotelId);
        return hotel != null && hotel.getAvailableRooms() >= rooms;
    }

    public String reserveHotel(String hotelId, int rooms) {
        Hotel hotel = getHotelById(hotelId);
        if (hotel != null && hotel.getAvailableRooms() >= rooms) {
            hotel.setAvailableRooms(hotel.getAvailableRooms() - rooms);
            return UUID.randomUUID().toString();
        }
        return null;
    }
}