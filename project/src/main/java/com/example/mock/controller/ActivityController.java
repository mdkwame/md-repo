package com.example.mock.controller;

import com.example.mock.dto.Activity;
import com.example.mock.dto.AvailabilityResponse;
import com.example.mock.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@Tag(name = "Activities", description = "Activity booking operations")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    @GetMapping
    @Operation(summary = "Get all activities or search with filters")
    public List<Activity> getActivities(
            @Parameter(description = "City/Location") @RequestParam(required = false) String location,
            @Parameter(description = "Activity category") @RequestParam(required = false) String category,
            @Parameter(description = "Minimum price") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Sort by: price_asc, price_desc, rating_desc") @RequestParam(required = false) String sort) {
        
        if (location != null || category != null || minPrice != null || maxPrice != null || sort != null) {
            return activityService.searchActivities(location, category, minPrice, maxPrice, sort);
        }
        return activityService.getAllActivities();
    }
    
    @GetMapping("/availability")
    @Operation(summary = "Check activity availability")
    public AvailabilityResponse checkAvailability(@RequestParam String id) {
        boolean available = activityService.isAvailable(id);
        int count = activityService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(available)
                .count(count)
                .message(available ? "Activity slots available" : "Activity fully booked")
                .build();
    }
    
    @PostMapping("/book")
    @Operation(summary = "Book an activity (demo)")
    public AvailabilityResponse bookActivity(@RequestParam String id) {
        boolean success = activityService.bookActivity(id);
        int remainingCount = activityService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(success)
                .count(remainingCount)
                .message(success ? "Activity booked successfully!" : "Booking failed - no slots available")
                .build();
    }
}