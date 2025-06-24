package com.bookingsystem.service;

import com.bookingsystem.model.Activity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private List<Activity> activities;

    public ActivityService() {
        initializeMockData();
    }

    private void initializeMockData() {
        activities = new ArrayList<>();
        
        activities.add(new Activity("AC001", "Statue of Liberty Tour", "Sightseeing", "New York",
                "Guided tour of the iconic Statue of Liberty and Ellis Island",
                new BigDecimal("29.99"), "USD", 25, "3 hours", 5,
                Arrays.asList("Guided tour", "Ferry ride", "Audio guide", "Historical insights"),
                "https://images.pexels.com/photos/64271/queen-of-liberty-statue-of-liberty-new-york-liberty-64271.jpeg",
                40.6892, -74.0445, "Easy", 0));
        
        activities.add(new Activity("AC002", "Grand Canyon Helicopter Tour", "Adventure", "Las Vegas",
                "Breathtaking helicopter tour over the Grand Canyon",
                new BigDecimal("199.99"), "USD", 8, "45 minutes", 5,
                Arrays.asList("Helicopter ride", "Aerial views", "Professional pilot", "Photo opportunities"),
                "https://images.pexels.com/photos/417054/pexels-photo-417054.jpeg",
                36.1699, -115.1398, "Easy", 12));
        
        activities.add(new Activity("AC003", "Snorkeling Adventure", "Water Sports", "Miami",
                "Explore vibrant coral reefs and marine life",
                new BigDecimal("79.99"), "USD", 15, "4 hours", 4,
                Arrays.asList("Equipment included", "Underwater guide", "Boat transportation", "Lunch"),
                "https://images.pexels.com/photos/544197/pexels-photo-544197.jpeg",
                25.7617, -80.1918, "Moderate", 16));
        
        activities.add(new Activity("AC004", "Rocky Mountain Hiking", "Outdoor", "Denver",
                "Guided hiking tour through scenic mountain trails",
                new BigDecimal("49.99"), "USD", 12, "6 hours", 4,
                Arrays.asList("Professional guide", "Safety equipment", "Trail snacks", "Photography tips"),
                "https://images.pexels.com/photos/618833/pexels-photo-618833.jpeg",
                39.7392, -104.9903, "Moderate", 14));
        
        activities.add(new Activity("AC005", "Golden Gate Bridge Bike Tour", "Cycling", "San Francisco",
                "Scenic bike tour across the famous Golden Gate Bridge",
                new BigDecimal("39.99"), "USD", 20, "3 hours", 4,
                Arrays.asList("Bike rental", "Helmet included", "Route map", "Photo stops"),
                "https://images.pexels.com/photos/208745/pexels-photo-208745.jpeg",
                37.8199, -122.4783, "Easy", 10));
    }

    public List<Activity> searchActivities(String city, String category, BigDecimal maxPrice, String difficulty) {
        return activities.stream()
                .filter(activity -> city == null || activity.getCity().toLowerCase().contains(city.toLowerCase()))
                .filter(activity -> category == null || activity.getCategory().toLowerCase().contains(category.toLowerCase()))
                .filter(activity -> maxPrice == null || activity.getPrice().compareTo(maxPrice) <= 0)
                .filter(activity -> difficulty == null || activity.getDifficulty().toLowerCase().contains(difficulty.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Activity getActivityById(String activityId) {
        return activities.stream()
                .filter(activity -> activity.getActivityId().equals(activityId))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAvailability(String activityId, int spots) {
        Activity activity = getActivityById(activityId);
        return activity != null && activity.getAvailableSpots() >= spots;
    }

    public String reserveActivity(String activityId, int spots) {
        Activity activity = getActivityById(activityId);
        if (activity != null && activity.getAvailableSpots() >= spots) {
            activity.setAvailableSpots(activity.getAvailableSpots() - spots);
            return UUID.randomUUID().toString();
        }
        return null;
    }
}