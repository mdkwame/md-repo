package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Activity;
import com.bookingsystem.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Activity>> searchActivities(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Activity> activities = activityService.searchActivities(city, category, maxPrice, difficulty);
        
        SearchResponse<Activity> response = new SearchResponse<>(
                activities, activities.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivityById(@PathVariable String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity != null) {
            return ResponseEntity.ok(activity);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{activityId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @PathVariable String activityId,
            @RequestParam(defaultValue = "1") int spots) {
        
        boolean available = activityService.checkAvailability(activityId, spots);
        
        Map<String, Object> response = new HashMap<>();
        response.put("available", available);
        response.put("activityId", activityId);
        response.put("requestedSpots", spots);
        
        if (available) {
            Activity activity = activityService.getActivityById(activityId);
            response.put("availableSpots", activity.getAvailableSpots());
            response.put("price", activity.getPrice());
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{activityId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveActivity(
            @PathVariable String activityId,
            @RequestParam(defaultValue = "1") int spots) {
        
        String reservationId = activityService.reserveActivity(activityId, spots);
        
        Map<String, Object> response = new HashMap<>();
        if (reservationId != null) {
            response.put("success", true);
            response.put("reservationId", reservationId);
            response.put("activityId", activityId);
            response.put("spots", spots);
            response.put("message", "Activity reserved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Unable to reserve activity - insufficient availability");
            return ResponseEntity.badRequest().body(response);
        }
    }
}