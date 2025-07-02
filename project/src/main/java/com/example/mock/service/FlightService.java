package com.example.mock.service;

import com.example.mock.dto.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class FlightService {
    
    private List<Flight> flights;
    private final ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public FlightService() {
        loadFlights();
    }
    
    private void loadFlights() {
        try {
            ClassPathResource resource = new ClassPathResource("static/flights.json");
            flights = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Flight>>() {});
            
            // Initialize inventory
            flights.forEach(flight -> inventory.put(flight.getId(), flight.getAvailableSeats()));
        } catch (IOException e) {
            System.err.println("Error loading flights: " + e.getMessage());
        }
    }
    
    public List<Flight> getAllFlights() {
        return flights;
    }
    
    public List<Flight> searchFlights(String from, String to, Double minPrice, Double maxPrice, String sort) {
        return flights.stream()
                .filter(flight -> from == null || flight.getFrom().equalsIgnoreCase(from))
                .filter(flight -> to == null || flight.getTo().equalsIgnoreCase(to))
                .filter(flight -> minPrice == null || flight.getPrice() >= minPrice)
                .filter(flight -> maxPrice == null || flight.getPrice() <= maxPrice)
                .sorted((f1, f2) -> {
                    if ("price_asc".equals(sort)) return Double.compare(f1.getPrice(), f2.getPrice());
                    if ("price_desc".equals(sort)) return Double.compare(f2.getPrice(), f1.getPrice());
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
    
    public boolean bookFlight(String id) {
        int current = inventory.getOrDefault(id, 0);
        if (current > 0) {
            inventory.put(id, current - 1);
            return true;
        }
        return false;
    }
}