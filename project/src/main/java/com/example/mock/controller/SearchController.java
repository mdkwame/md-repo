package com.example.mock.controller;

import com.example.mock.service.ActivityService;
import com.example.mock.service.CarService;
import com.example.mock.service.FlightService;
import com.example.mock.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search")  
@Tag(name = "Search", description = "Unified search across all services")
public class SearchController {
    
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private CarService carService;
    
    @Autowired
    private ActivityService activityService;
    
    @GetMapping
    @Operation(summary = "Search across all services or specific type")
    public Map<String, Object> search(
            @Parameter(description = "Service type: flight, hotel, car, activity, all") @RequestParam(required = false, defaultValue = "all") String type,
            @Parameter(description = "Location/City") @RequestParam(required = false) String location,
            @Parameter(description = "Sort by: price_asc, price_desc") @RequestParam(required = false) String sort) {
        
        Map<String, Object> results = new HashMap<>();
        
        switch (type.toLowerCase()) {
            case "flight":
                results.put("flights", flightService.searchFlights(location, null, null, null, sort));
                break;
            case "hotel":
                results.put("hotels", hotelService.searchHotels(location, null, null, null, sort));
                break;
            case "car":
                results.put("cars", carService.searchCars(location, null, null, null, sort));
                break;
            case "activity":
                results.put("activities", activityService.searchActivities(location, null, null, null, sort));
                break;
            case "all":
            default:
                results.put("flights", flightService.searchFlights(location, null, null, null, sort));
                results.put("hotels", hotelService.searchHotels(location, null, null, null, sort));
                results.put("cars", carService.searchCars(location, null, null, null, sort));
                results.put("activities", activityService.searchActivities(location, null, null, null, sort));
                break;
        }
        
        return results;
    }
}