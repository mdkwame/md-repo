package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Flight;
import com.bookingsystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Flight>> searchFlights(
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String arrival,
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String airline,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Flight> flights = flightService.searchFlights(departure, arrival, departureDate, maxPrice, airline);
        
        SearchResponse<Flight> response = new SearchResponse<>(
                flights, flights.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{flightId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @PathVariable String flightId,
            @RequestParam(defaultValue = "1") int seats) {
        
        boolean available = flightService.checkAvailability(flightId, seats);
        
        Map<String, Object> response = new HashMap<>();
        response.put("available", available);
        response.put("flightId", flightId);
        response.put("requestedSeats", seats);
        
        if (available) {
            Flight flight = flightService.getFlightById(flightId);
            response.put("availableSeats", flight.getAvailableSeats());
            response.put("price", flight.getPrice());
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{flightId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveFlight(
            @PathVariable String flightId,
            @RequestParam(defaultValue = "1") int seats) {
        
        String reservationId = flightService.reserveFlight(flightId, seats);
        
        Map<String, Object> response = new HashMap<>();
        if (reservationId != null) {
            response.put("success", true);
            response.put("reservationId", reservationId);
            response.put("flightId", flightId);
            response.put("seats", seats);
            response.put("message", "Flight reserved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Unable to reserve flight - insufficient availability");
            return ResponseEntity.badRequest().body(response);
        }
    }
}