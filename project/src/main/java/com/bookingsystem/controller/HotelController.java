package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Hotel;
import com.bookingsystem.service.HotelService;
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
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Hotel>> searchHotels(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Hotel> hotels = hotelService.searchHotels(city, checkIn, checkOut, maxPrice, minRating);
        
        SearchResponse<Hotel> response = new SearchResponse<>(
                hotels, hotels.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel != null) {
            return ResponseEntity.ok(hotel);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{hotelId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @PathVariable String hotelId,
            @RequestParam(defaultValue = "1") int rooms) {
        
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

    @PostMapping("/{hotelId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveHotel(
            @PathVariable String hotelId,
            @RequestParam(defaultValue = "1") int rooms) {
        
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