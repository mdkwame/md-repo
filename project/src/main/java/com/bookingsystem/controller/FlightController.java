package com.bookingsystem.controller;

import com.bookingsystem.dto.SearchResponse;
import com.bookingsystem.model.Flight;
import com.bookingsystem.service.FlightService;
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
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
@Tag(name = "Flights", description = "Flight booking and search operations")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Operation(
        summary = "Search flights",
        description = "Search for flights with optional filters including departure city, arrival city, date, price, and airline"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flights found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<SearchResponse<Flight>> searchFlights(
            @Parameter(description = "Departure city or airport code") @RequestParam(required = false) String departure,
            @Parameter(description = "Arrival city or airport code") @RequestParam(required = false) String arrival,
            @Parameter(description = "Departure date (YYYY-MM-DD)") @RequestParam(required = false) String departureDate,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Airline name filter") @RequestParam(required = false) String airline,
            @Parameter(description = "Page number for pagination") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of results per page") @RequestParam(defaultValue = "10") int pageSize) {
        
        List<Flight> flights = flightService.searchFlights(departure, arrival, departureDate, maxPrice, airline);
        
        SearchResponse<Flight> response = new SearchResponse<>(
                flights, flights.size(), page, pageSize, UUID.randomUUID().toString());
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get flight by ID",
        description = "Retrieve detailed information about a specific flight"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
        @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> getFlightById(
            @Parameter(description = "Flight ID") @PathVariable String flightId) {
        Flight flight = flightService.getFlightById(flightId);
        if (flight != null) {
            return ResponseEntity.ok(flight);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Check flight availability",
        description = "Check seat availability for a specific flight"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability checked successfully"),
        @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @GetMapping("/{flightId}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @Parameter(description = "Flight ID") @PathVariable String flightId,
            @Parameter(description = "Number of seats requested") @RequestParam(defaultValue = "1") int seats) {
        
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

    @Operation(
        summary = "Reserve flight",
        description = "Make a reservation for a specific flight"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flight reserved successfully"),
        @ApiResponse(responseCode = "400", description = "Unable to reserve flight - insufficient availability"),
        @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    @PostMapping("/{flightId}/reserve")
    public ResponseEntity<Map<String, Object>> reserveFlight(
            @Parameter(description = "Flight ID") @PathVariable String flightId,
            @Parameter(description = "Number of seats to reserve") @RequestParam(defaultValue = "1") int seats) {
        
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