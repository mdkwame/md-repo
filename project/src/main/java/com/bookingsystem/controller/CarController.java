package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Car;
import com.bookingsystem.service.CarService;
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
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Car>> searchCars(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String fuelType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Car> cars = carService.searchCars(location, category, maxPrice, fuelType);
        
        SearchResponse<Car> response = new SearchResponse<>(
                cars, cars.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable String carId) {
        Car car = carService.getCarById(carId);
        if (car != null) {
            return ResponseEntity.ok(car);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{carId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(@PathVariable String carId) {
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

    @PostMapping("/{carId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveCar(@PathVariable String carId) {
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