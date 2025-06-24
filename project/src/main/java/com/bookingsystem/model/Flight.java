package com.bookingsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Flight {
    private String flightId;
    private String airline;
    private String flightNumber;
    private String departure;
    private String arrival;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;
    
    private BigDecimal price;
    private String currency;
    private int availableSeats;
    private String aircraft;
    private String duration;
    private boolean direct;

    // Constructors
    public Flight() {}

    public Flight(String flightId, String airline, String flightNumber, String departure, 
                  String arrival, LocalDateTime departureTime, LocalDateTime arrivalTime, 
                  BigDecimal price, String currency, int availableSeats, String aircraft, 
                  String duration, boolean direct) {
        this.flightId = flightId;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.currency = currency;
        this.availableSeats = availableSeats;
        this.aircraft = aircraft;
        this.duration = duration;
        this.direct = direct;
    }

    // Getters and Setters
    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDeparture() { return departure; }
    public void setDeparture(String departure) { this.departure = departure; }

    public String getArrival() { return arrival; }
    public void setArrival(String arrival) { this.arrival = arrival; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public String getAircraft() { return aircraft; }
    public void setAircraft(String aircraft) { this.aircraft = aircraft; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public boolean isDirect() { return direct; }
    public void setDirect(boolean direct) { this.direct = direct; }
}