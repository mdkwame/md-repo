package com.bookingsystem.service;

import com.bookingsystem.model.Car;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {

    private List<Car> cars;

    public CarService() {
        initializeMockData();
    }

    private void initializeMockData() {
        cars = new ArrayList<>();
        
        cars.add(new Car("CR001", "Toyota", "Camry", "Economy", new BigDecimal("45.99"), "USD", 5,
                "Gasoline", "Automatic", 5, 4, true, "Los Angeles Airport",
                Arrays.asList("GPS", "Bluetooth", "USB Charging"),
                "https://images.pexels.com/photos/116675/pexels-photo-116675.jpeg"));
        
        cars.add(new Car("CR002", "BMW", "X3", "Luxury", new BigDecimal("89.99"), "USD", 3,
                "Gasoline", "Automatic", 5, 4, true, "Miami Airport",
                Arrays.asList("GPS", "Leather Seats", "Premium Sound", "Sunroof"),
                "https://images.pexels.com/photos/244206/pexels-photo-244206.jpeg"));
        
        cars.add(new Car("CR003", "Ford", "Transit", "Van", new BigDecimal("69.99"), "USD", 2,
                "Gasoline", "Manual", 8, 4, true, "Denver Downtown",
                Arrays.asList("GPS", "Extra Storage", "USB Charging"),
                "https://images.pexels.com/photos/386009/pexels-photo-386009.jpeg"));
        
        cars.add(new Car("CR004", "Jeep", "Wrangler", "SUV", new BigDecimal("75.99"), "USD", 4,
                "Gasoline", "Manual", 4, 4, true, "Phoenix Airport",
                Arrays.asList("4WD", "GPS", "Bluetooth", "Off-road Package"),
                "https://images.pexels.com/photos/385997/pexels-photo-385997.jpeg"));
        
        cars.add(new Car("CR005", "Tesla", "Model 3", "Electric", new BigDecimal("95.99"), "USD", 2,
                "Electric", "Automatic", 5, 4, true, "San Francisco Downtown",
                Arrays.asList("Autopilot", "Supercharging", "Premium Interior", "GPS"),
                "https://images.pexels.com/photos/619654/pexels-photo-619654.jpeg"));
    }

    public List<Car> searchCars(String location, String category, BigDecimal maxPrice, String fuelType) {
        return cars.stream()
                .filter(car -> location == null || car.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(car -> category == null || car.getCategory().toLowerCase().contains(category.toLowerCase()))
                .filter(car -> maxPrice == null || car.getPricePerDay().compareTo(maxPrice) <= 0)
                .filter(car -> fuelType == null || car.getFuelType().toLowerCase().contains(fuelType.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Car getCarById(String carId) {
        return cars.stream()
                .filter(car -> car.getCarId().equals(carId))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAvailability(String carId) {
        Car car = getCarById(carId);
        return car != null && car.getAvailableCount() > 0;
    }

    public String reserveCar(String carId) {
        Car car = getCarById(carId);
        if (car != null && car.getAvailableCount() > 0) {
            car.setAvailableCount(car.getAvailableCount() - 1);
            return UUID.randomUUID().toString();
        }
        return null;
    }
}