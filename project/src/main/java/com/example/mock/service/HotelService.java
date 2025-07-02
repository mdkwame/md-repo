package com.example.mock.service;

import com.example.mock.dto.Hotel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class HotelService {
    
    private List<Hotel> hotels;
    private final ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public HotelService() {
        loadHotels();
    }
    
    private void loadHotels() {
        try {
            ClassPathResource resource = new ClassPathResource("static/hotels.json");
            hotels = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Hotel>>() {});
            
            // Initialize inventory
            hotels.forEach(hotel -> inventory.put(hotel.getId(), hotel.getAvailableRooms()));
        } catch (IOException e) {
            System.err.println("Error loading hotels: " + e.getMessage());
        }
    }
    
    public List<Hotel> getAllHotels() {
        return hotels;
    }
    
    public List<Hotel> searchHotels(String location, Integer minStars, Double minPrice, Double maxPrice, String sort) {
        return hotels.stream()
                .filter(hotel -> location == null || hotel.getLocation().equalsIgnoreCase(location))
                .filter(hotel -> minStars == null || hotel.getStars() >= minStars)
                .filter(hotel -> minPrice == null || hotel.getPricePerNight() >= minPrice)
                .filter(hotel -> maxPrice == null || hotel.getPricePerNight() <= maxPrice)
                .sorted((h1, h2) -> {
                    if ("price_asc".equals(sort)) return Double.compare(h1.getPricePerNight(), h2.getPricePerNight());
                    if ("price_desc".equals(sort)) return Double.compare(h2.getPricePerNight(), h1.getPricePerNight());
                    if ("rating_desc".equals(sort)) return Double.compare(h2.getRating(), h1.getRating());
                    return 0;
                })
                .collect(Collectors.toList());
    }
    
    public boolean isAvailable(String id) {
        return inventory.getOrDefault(id, 0) > 0;
    }
    
    public int getAvailableCount(String id) {
        return inventory.getOrDefault(id, 0);
    }
    
    public boolean bookHotel(String id) {
        int current = inventory.getOrDefault(id, 0);
        if (current > 0) {
            inventory.put(id, current - 1);
            return true;
        }
        return false;
    }
}