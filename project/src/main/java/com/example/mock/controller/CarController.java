package com.example.mock.controller;

import com.example.mock.dto.AvailabilityResponse;
import com.example.mock.dto.Car;
import com.example.mock.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@Tag(name = "Cars", description = "Car rental operations")
public class CarController {
    
    @Autowired
    private CarService carService;
    
    @GetMapping
    @Operation(summary = "Get all cars or search with filters")
    public List<Car> getCars(
            @Parameter(description = "City/Location") @RequestParam(required = false) String location,
            @Parameter(description = "Car type (Sedan, SUV, Sports)") @RequestParam(required = false) String type,
            @Parameter(description = "Minimum price per day") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price per day") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Sort by: price_asc, price_desc") @RequestParam(required = false) String sort) {
        
        if (location != null || type != null || minPrice != null || maxPrice != null || sort != null) {
            return carService.searchCars(location, type, minPrice, maxPrice, sort);
        }
        return carService.getAllCars();
    }
    
    @GetMapping("/availability")
    @Operation(summary = "Check car availability")
    public AvailabilityResponse checkAvailability(@RequestParam String id) {
        boolean available = carService.isAvailable(id);
        int count = carService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(available)
                .count(count)
                .message(available ? "Car available for rental" : "Car not available")
                .build();
    }
    
    @PostMapping("/book")
    @Operation(summary = "Book a car rental (demo)")
    public AvailabilityResponse bookCar(@RequestParam String id) {
        boolean success = carService.bookCar(id);
        int remainingCount = carService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(success)
                .count(remainingCount)
                .message(success ? "Car rental booked successfully!" : "Booking failed - car not available")
                .build();
    }
}