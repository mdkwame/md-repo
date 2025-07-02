package com.example.mock.service;

import com.example.mock.dto.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CarService {
    
    private List<Car> cars;
    private final ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public CarService() {
        loadCars();
    }
    
    private void loadCars() {
        try {
            ClassPathResource resource = new ClassPathResource("static/cars.json");
            cars = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Car>>() {});
            
            // Initialize inventory
            cars.forEach(car -> inventory.put(car.getId(), car.getAvailableCount()));
        } catch (IOException e) {
            System.err.println("Error loading cars: " + e.getMessage());
        }
    }
    
    public List<Car> getAllCars() {
        return cars;
    }
    
    public List<Car> searchCars(String location, String type, Double minPrice, Double maxPrice, String sort) {
        return cars.stream()
                .filter(car -> location == null || car.getLocation().equalsIgnoreCase(location))
                .filter(car -> type == null || car.getType().equalsIgnoreCase(type))
                .filter(car -> minPrice == null || car.getPricePerDay() >= minPrice)
                .filter(car -> maxPrice == null || car.getPricePerDay() <= maxPrice)
                .sorted((c1, c2) -> {
                    if ("price_asc".equals(sort)) return Double.compare(c1.getPricePerDay(), c2.getPricePerDay());
                    if ("price_desc".equals(sort)) return Double.compare(c2.getPricePerDay(), c1.getPricePerDay());
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
    
    public boolean bookCar(String id) {
        int current = inventory.getOrDefault(id, 0);
        if (current > 0) {
            inventory.put(id, current - 1);
            return true;
        }
        return false;
    }
}