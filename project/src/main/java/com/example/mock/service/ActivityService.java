package com.example.mock.service;

import com.example.mock.dto.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    
    private List<Activity> activities;
    private final ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public ActivityService() {
        loadActivities();
    }
    
    private void loadActivities() {
        try {
            ClassPathResource resource = new ClassPathResource("static/activities.json");
            activities = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Activity>>() {});
            
            // Initialize inventory
            activities.forEach(activity -> inventory.put(activity.getId(), activity.getAvailableSlots()));
        } catch (IOException e) {
            System.err.println("Error loading activities: " + e.getMessage());
        }
    }
    
    public List<Activity> getAllActivities() {
        return activities;
    }
    
    public List<Activity> searchActivities(String location, String category, Double minPrice, Double maxPrice, String sort) {
        return activities.stream()
                .filter(activity -> location == null || activity.getLocation().equalsIgnoreCase(location))
                .filter(activity -> category == null || activity.getCategory().equalsIgnoreCase(category))
                .filter(activity -> minPrice == null || activity.getPrice() >= minPrice)
                .filter(activity -> maxPrice == null || activity.getPrice() <= maxPrice)
                .sorted((a1, a2) -> {
                    if ("price_asc".equals(sort)) return Double.compare(a1.getPrice(), a2.getPrice());
                    if ("price_desc".equals(sort)) return Double.compare(a2.getPrice(), a1.getPrice());
                    if ("rating_desc".equals(sort)) return Double.compare(a2.getRating(), a1.getRating());
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
    
    public boolean bookActivity(String id) {
        int current = inventory.getOrDefault(id, 0);
        if (current > 0) {
            inventory.put(id, current - 1);
            return true;
        }
        return false;
    }
}