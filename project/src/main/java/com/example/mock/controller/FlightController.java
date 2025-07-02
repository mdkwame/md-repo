package com.example.mock.controller;

import com.example.mock.dto.AvailabilityResponse;
import com.example.mock.dto.Flight;
import com.example.mock.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@Tag(name = "Flights", description = "Flight booking operations")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @GetMapping
    @Operation(summary = "Get all flights or search with filters")
    public List<Flight> getFlights(
            @Parameter(description = "Departure city") @RequestParam(required = false) String from,
            @Parameter(description = "Destination city") @RequestParam(required = false) String to,
            @Parameter(description = "Minimum price") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Sort by: price_asc, price_desc") @RequestParam(required = false) String sort) {
        
        if (from != null || to != null || minPrice != null || maxPrice != null || sort != null) {
            return flightService.searchFlights(from, to, minPrice, maxPrice, sort);
        }
        return flightService.getAllFlights();
    }
    
    @GetMapping("/availability")
    @Operation(summary = "Check flight availability")
    public AvailabilityResponse checkAvailability(@RequestParam String id) {
        boolean available = flightService.isAvailable(id);
        int count = flightService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(available)
                .count(count)
                .message(available ? "Flight available" : "Flight fully booked")
                .build();
    }
    
    @PostMapping("/book")
    @Operation(summary = "Book a flight (demo)")
    public AvailabilityResponse bookFlight(@RequestParam String id) {
        boolean success = flightService.bookFlight(id);
        int remainingCount = flightService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(success)
                .count(remainingCount)
                .message(success ? "Flight booked successfully!" : "Booking failed - no seats available")
                .build();
    }
}