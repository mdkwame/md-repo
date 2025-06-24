package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Hotel;
import com.bookingsystem.service.HotelService;
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
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
@Tag(name = "Hotels", description = "Hotel booking and search operations")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(
        summary = "Search hotels",
        description = "Search for hotels with optional filters including city, check-in/out dates, price, and rating"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotels found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Hotel>> searchHotels(
            @Parameter(description = "City name") @RequestParam(required = false) String city,
            @Parameter(description = "Check-in date (YYYY-MM-DD)") @RequestParam(required = false) String checkIn,
            @Parameter(description = "Check-out date (YYYY-MM-DD)") @RequestParam(required = false) String checkOut,
            @Parameter(description = "Maximum price per night") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Minimum star rating") @RequestParam(required = false) Integer minRating,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of results per page") @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Hotel> hotels = hotelService.searchHotels(city, checkIn, checkOut, maxPrice, minRating);
        
        SearchResponse<Hotel> response = new SearchResponse<>(
                hotels, hotels.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get hotel by ID",
        description = "Retrieve detailed information about a specific hotel"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hotel.class))),
        @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(
            @Parameter(description = "Hotel ID") @PathVariable String hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel != null) {
            return ResponseEntity.ok(hotel);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Check hotel availability",
        description = "Check room availability for a specific hotel"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability checked successfully"),
        @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{hotelId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @Parameter(description = "Hotel ID") @PathVariable String hotelId,
            @Parameter(description = "Number of rooms requested") @RequestParam(defaultValue = "1") int rooms) {
        
        boolean available = hotelService.checkAvailability(hotelId, rooms);
        
        Map<String, Object> response = new HashMap<>();
        response.put("available", available);
        response.put("hotelId", hotelId);
        response.put("requestedRooms", rooms);
        
        if (available) {
            Hotel hotel = hotelService.getHotelById(hotelId);
            response.put("availableRooms", hotel.getAvailableRooms());
            response.put("pricePerNight", hotel.getPricePerNight());
        }
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Reserve hotel",
        description = "Make a reservation for a specific hotel"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hotel reserved successfully"),
        @ApiResponse(responseCode = "400", description = "Unable to reserve hotel - insufficient availability"),
        @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PostMapping("/{hotelId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveHotel(
            @Parameter(description = "Hotel ID") @PathVariable String hotelId,
            @Parameter(description = "Number of rooms to reserve") @RequestParam(defaultValue = "1") int rooms) {
        
        String reservationId = hotelService.reserveHotel(hotelId, rooms);
        
        Map<String, Object> response = new HashMap<>();
        if (reservationId != null) {
            response.put("success", true);
            response.put("reservationId", reservationId);
            response.put("hotelId", hotelId);
            response.put("rooms", rooms);
            response.put("message", "Hotel reserved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Unable to reserve hotel - insufficient availability");
            return ResponseEntity.badRequest().body(response);
        }
    }
}