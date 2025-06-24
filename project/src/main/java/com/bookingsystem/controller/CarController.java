package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Car;
import com.bookingsystem.service.CarService;
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
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*")
@Tag(name = "Cars", description = "Car rental booking and search operations")
public class CarController {

    @Autowired
    private CarService carService;

    @Operation(
        summary = "Search cars",
        description = "Search for rental cars with optional filters including location, category, price, and fuel type"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cars found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Car>> searchCars(
            @Parameter(description = "Pickup location") @RequestParam(required = false) String location,
            @Parameter(description = "Car category (Economy, Luxury, SUV, etc.)") @RequestParam(required = false) String category,
            @Parameter(description = "Maximum price per day") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Fuel type (Gasoline, Electric, Hybrid)") @RequestParam(required = false) String fuelType,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of results per page") @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Car> cars = carService.searchCars(location, category, maxPrice, fuelType);
        
        SearchResponse<Car> response = new SearchResponse<>(
                cars, cars.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get car by ID",
        description = "Retrieve detailed information about a specific rental car"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
        @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(
            @Parameter(description = "Car ID") @PathVariable String carId) {
        Car car = carService.getCarById(carId);
        if (car != null) {
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Check car availability",
        description = "Check availability for a specific rental car"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability checked successfully"),
        @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{carId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @Parameter(description = "Car ID") @PathVariable String carId) {
        boolean available = carService.checkAvailability(carId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("available", available);
        response.put("carId", carId);
        
        if (available) {
            Car car = carService.getCarById(carId);
            response.put("availableCount", car.getAvailableCount());
            response.put("pricePerDay", car.getPricePerDay());
        }
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Reserve car",
        description = "Make a reservation for a specific rental car"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car reserved successfully"),
        @ApiResponse(responseCode = "400", description = "Unable to reserve car - not available"),
        @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @PostMapping("/{carId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveCar(
            @Parameter(description = "Car ID") @PathVariable String carId) {
        String reservationId = carService.reserveCar(carId);
        
        Map<String, Object> response = new HashMap<>();
        if (reservationId != null) {
            response.put("success", true);
            response.put("reservationId", reservationId);
            response.put("carId", carId);
            response.put("message", "Car reserved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Unable to reserve car - not available");
            return ResponseEntity.badRequest().body(response);
        }
    }
}