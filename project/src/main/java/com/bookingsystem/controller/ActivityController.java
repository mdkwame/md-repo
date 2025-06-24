package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Activity;
import com.bookingsystem.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Activities", description = "Activity booking and search operations")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(
        summary = "Search activities",
        description = "Search for activities with optional filters including city, category, price, and difficulty level"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activities found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Activity>> searchActivities(
            @Parameter(description = "City name") @RequestParam(required = false) String city,
            @Parameter(description = "Activity category") @RequestParam(required = false) String category,
            @Parameter(description = "Maximum price") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Difficulty level (Easy, Moderate, Hard)") @RequestParam(required = false) String difficulty,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of results per page") @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Activity> activities = activityService.searchActivities(city, category, maxPrice, difficulty);
        
        SearchResponse<Activity> response = new SearchResponse<>(
                activities, activities.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get activity by ID",
        description = "Retrieve detailed information about a specific activity"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activity found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
        @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivityById(
            @Parameter(description = "Activity ID") @PathVariable String activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity != null) {
            return ResponseEntity.ok(activity);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Check activity availability",
        description = "Check spot availability for a specific activity"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability checked successfully"),
        @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @GetMapping("/{activityId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @Parameter(description = "Activity ID") @PathVariable String activityId,
            @Parameter(description = "Number of spots requested") @RequestParam(defaultValue = "1") int spots) {
        
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

    @Operation(
        summary = "Reserve activity",
        description = "Make a reservation for a specific activity"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activity reserved successfully"),
        @ApiResponse(responseCode = "400", description = "Unable to reserve activity - insufficient availability"),
        @ApiResponse(responseCode = "404", description = "Activity not found")
    })
    @PostMapping("/{activityId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveActivity(
            @Parameter(description = "Activity ID") @PathVariable String activityId,
            @Parameter(description = "Number of spots to reserve") @RequestParam(defaultValue = "1") int spots) {
        
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