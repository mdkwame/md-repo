package com.example.mock.controller;

import com.example.mock.dto.AvailabilityResponse;
import com.example.mock.dto.Hotel;
import com.example.mock.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotels", description = "Hotel booking operations")
public class HotelController {
    
    @Autowired
    private HotelService hotelService;
    
    @GetMapping
    @Operation(summary = "Get all hotels or search with filters")
    public List<Hotel> getHotels(
            @Parameter(description = "City/Location") @RequestParam(required = false) String location,
            @Parameter(description = "Minimum stars") @RequestParam(required = false) Integer minStars,
            @Parameter(description = "Minimum price per night") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price per night") @RequestParam(required = false) Double maxPrice,
            @Parameter(description = "Sort by: price_asc, price_desc, rating_desc") @RequestParam(required = false) String sort) {
        
        if (location != null || minStars != null || minPrice != null || maxPrice != null || sort != null) {
            return hotelService.searchHotels(location, minStars, minPrice, maxPrice, sort);
        }
        return hotelService.getAllHotels();
    }
    
    @GetMapping("/availability")
    @Operation(summary = "Check hotel availability")
    public AvailabilityResponse checkAvailability(@RequestParam String id) {
        boolean available = hotelService.isAvailable(id);
        int count = hotelService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(available)
                .count(count)
                .message(available ? "Hotel rooms available" : "Hotel fully booked")
                .build();
    }
    
    @PostMapping("/book")
    @Operation(summary = "Book a hotel room (demo)")
    public AvailabilityResponse bookHotel(@RequestParam String id) {
        boolean success = hotelService.bookHotel(id);
        int remainingCount = hotelService.getAvailableCount(id);
        
        return AvailabilityResponse.builder()
                .available(success)
                .count(remainingCount)
                .message(success ? "Hotel room booked successfully!" : "Booking failed - no rooms available")
                .build();
    }
}