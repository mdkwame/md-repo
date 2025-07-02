package com.example.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    private String id;
    private String name;
    private String location;
    private String address;
    private int stars;
    private double pricePerNight;
    private int availableRooms;
    private String amenities;
    private double rating;
}