package com.example.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String id;
    private String brand;
    private String model;
    private String type;
    private String location;
    private double pricePerDay;
    private int availableCount;
    private String features;
    private int seats;
    private String fuelType;
}