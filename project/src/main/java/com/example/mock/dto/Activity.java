package com.example.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private String id;
    private String name;
    private String location;
    private String category;
    private double price;
    private int availableSlots;
    private String description;
    private int duration; // in hours
    private double rating;
}