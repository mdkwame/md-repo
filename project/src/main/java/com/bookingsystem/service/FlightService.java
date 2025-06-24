package com.bookingsystem.service;

import com.bookingsystem.model.Flight;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private List<Flight> flights;

    public FlightService() {
        initializeMockData();
    }

    private void initializeMockData() {
        flights = new ArrayList<>();
        
        // Mock flight data
        flights.add(new Flight("FL001", "American Airlines", "AA123", "New York (JFK)", "Los Angeles (LAX)",
                LocalDateTime.of(2024, 2, 15, 8, 30), LocalDateTime.of(2024, 2, 15, 11, 45),
                new BigDecimal("299.99"), "USD", 45, "Boeing 737", "5h 15m", true));
        
        flights.add(new Flight("FL002", "Delta Air Lines", "DL456", "Chicago (ORD)", "Miami (MIA)",
                LocalDateTime.of(2024, 2, 16, 14, 20), LocalDateTime.of(2024, 2, 16, 17, 30),
                new BigDecimal("189.99"), "USD", 23, "Airbus A320", "3h 10m", true));
        
        flights.add(new Flight("FL003", "United Airlines", "UA789", "San Francisco (SFO)", "Seattle (SEA)",
                LocalDateTime.of(2024, 2, 17, 10, 15), LocalDateTime.of(2024, 2, 17, 12, 25),
                new BigDecimal("149.99"), "USD", 67, "Boeing 737", "2h 10m", true));
        
        flights.add(new Flight("FL004", "JetBlue Airways", "B6101", "Boston (BOS)", "Orlando (MCO)",
                LocalDateTime.of(2024, 2, 18, 6, 45), LocalDateTime.of(2024, 2, 18, 9, 30),
                new BigDecimal("219.99"), "USD", 34, "Airbus A321", "2h 45m", true));
        
        flights.add(new Flight("FL005", "Southwest Airlines", "WN202", "Denver (DEN)", "Las Vegas (LAS)",
                LocalDateTime.of(2024, 2, 19, 16, 00), LocalDateTime.of(2024, 2, 19, 17, 20),
                new BigDecimal("129.99"), "USD", 89, "Boeing 737", "1h 20m", true));
    }

    public List<Flight> searchFlights(String departure, String arrival, String departureDate, 
                                     BigDecimal maxPrice, String airline) {
        return flights.stream()
                .filter(flight -> departure == null || flight.getDeparture().toLowerCase().contains(departure.toLowerCase()))
                .filter(flight -> arrival == null || flight.getArrival().toLowerCase().contains(arrival.toLowerCase()))
                .filter(flight -> maxPrice == null || flight.getPrice().compareTo(maxPrice) <= 0)
                .filter(flight -> airline == null || flight.getAirline().toLowerCase().contains(airline.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Flight getFlightById(String flightId) {
        return flights.stream()
                .filter(flight -> flight.getFlightId().equals(flightId))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAvailability(String flightId, int seats) {
        Flight flight = getFlightById(flightId);
        return flight != null && flight.getAvailableSeats() >= seats;
    }

    public String reserveFlight(String flightId, int seats) {
        Flight flight = getFlightById(flightId);
        if (flight != null && flight.getAvailableSeats() >= seats) {
            flight.setAvailableSeats(flight.getAvailableSeats() - seats);
            return UUID.randomUUID().toString();
        }
        return null;
    }
}