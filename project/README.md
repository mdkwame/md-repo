# Booking System Backend

A Spring Boot REST API backend for a mock booking system that provides endpoints for searching and booking flights, hotels, cars, and activities.

## Project Overview

This is a mock booking system backend built with Spring Boot that simulates third-party booking APIs. It provides RESTful endpoints for searching and reserving various travel services without requiring actual external API integrations.

## Project Structure

### Root Configuration Files

#### `pom.xml`
Maven project configuration file that defines:
- Project metadata (groupId, artifactId, version)
- Spring Boot parent dependency (version 3.2.0)
- Required dependencies:
  - `spring-boot-starter-web` - Web MVC framework
  - `spring-boot-starter-validation` - Bean validation
  - `jackson-databind` - JSON serialization/deserialization
  - `spring-boot-starter-test` - Testing framework
- Build plugins for Maven compilation and Spring Boot packaging

### Source Code Structure

#### Main Application
- **`src/main/java/com/bookingsystem/BookingSystemApplication.java`**
  - Main Spring Boot application entry point
  - Contains the `@SpringBootApplication` annotation
  - Runs the embedded Tomcat server and initializes the Spring context

#### Controllers (`src/main/java/com/bookingsystem/controller/`)
REST API controllers that handle HTTP requests and responses:

- **`ActivityController.java`**
  - Handles activity-related endpoints (`/api/activities`)
  - Search activities by city, category, price, difficulty
  - Get activity details, check availability, make reservations

- **`CarController.java`**
  - Manages car rental endpoints (`/api/cars`)
  - Search cars by location, category, price, fuel type
  - Get car details, check availability, make reservations

- **`FlightController.java`**
  - Handles flight booking endpoints (`/api/flights`)
  - Search flights by departure/arrival cities, dates, price, airline
  - Get flight details, check seat availability, make reservations

- **`HotelController.java`**
  - Manages hotel booking endpoints (`/api/hotels`)
  - Search hotels by city, dates, price, rating
  - Get hotel details, check room availability, make reservations

#### Models (`src/main/java/com/bookingsystem/model/`)
Data models representing the core entities:

- **`Activity.java`**
  - Represents tourist activities and experiences
  - Fields: ID, name, category, city, price, available spots, duration, rating, etc.

- **`Car.java`**
  - Represents rental cars
  - Fields: ID, brand, model, category, price per day, fuel type, transmission, etc.

- **`Flight.java`**
  - Represents flight bookings
  - Fields: ID, airline, flight number, departure/arrival info, price, available seats, etc.
  - Uses `@JsonFormat` for date/time serialization

- **`Hotel.java`**
  - Represents hotel accommodations
  - Fields: ID, name, city, address, rating, price per night, amenities, etc.

#### Services (`src/main/java/com/bookingsystem/service/`)
Business logic layer with mock data and operations:

- **`ActivityService.java`**
  - Contains mock activity data initialization
  - Implements search filtering logic
  - Handles availability checking and reservation logic

- **`CarService.java`**
  - Contains mock car rental data
  - Implements car search and filtering
  - Manages car availability and reservations

- **`FlightService.java`**
  - Contains mock flight data with realistic schedules
  - Implements flight search functionality
  - Handles seat availability and booking logic

- **`HotelService.java`**
  - Contains mock hotel data
  - Implements hotel search and filtering
  - Manages room availability and reservations

#### DTOs (`src/main/java/com/bookingsystem/dto/`)
Data Transfer Objects for API responses:

- **`SearchResponse.java`**
  - Generic wrapper for search results
  - Contains results list, pagination info, and search metadata
  - Used by all search endpoints for consistent response format

#### Configuration (`src/main/resources/`)

- **`application.properties`**
  - Spring Boot application configuration
  - Server port (8080), context path, application name
  - JSON serialization settings
  - Logging configuration for debugging

## API Endpoints

### Activities
- `GET /api/activities/search` - Search activities with filters
- `GET /api/activities/{id}` - Get activity details
- `GET /api/activities/{id}/availability` - Check availability
- `POST /api/activities/{id}/reserve` - Reserve activity

### Cars
- `GET /api/cars/search` - Search cars with filters
- `GET /api/cars/{id}` - Get car details
- `GET /api/cars/{id}/availability` - Check availability
- `POST /api/cars/{id}/reserve` - Reserve car

### Flights
- `GET /api/flights/search` - Search flights with filters
- `GET /api/flights/{id}` - Get flight details
- `GET /api/flights/{id}/availability` - Check seat availability
- `POST /api/flights/{id}/reserve` - Reserve flight

### Hotels
- `GET /api/hotels/search` - Search hotels with filters
- `GET /api/hotels/{id}` - Get hotel details
- `GET /api/hotels/{id}/availability` - Check room availability
- `POST /api/hotels/{id}/reserve` - Reserve hotel

## Features

- **Mock Data**: Pre-populated with realistic sample data for all services
- **Search & Filter**: Advanced filtering capabilities for each service type
- **Availability Checking**: Real-time availability simulation
- **Reservation System**: Mock booking with unique reservation IDs
- **CORS Enabled**: Cross-origin requests allowed for frontend integration
- **Consistent API**: Uniform response format across all endpoints
- **Error Handling**: Proper HTTP status codes and error responses

## Running the Application

1. Ensure Java 17+ is installed
2. Run `mvn clean install` to build the project
3. Run `mvn spring-boot:run` or execute the main class
4. Application will start on `http://localhost:8080`
5. Test endpoints using tools like Postman or curl

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Web MVC**
- **Jackson** for JSON processing
- **Maven** for dependency management
- **Embedded Tomcat** server

## Development Notes

- All data is stored in-memory and resets on application restart
- No database integration - purely mock services
- Designed for MVP/prototype development
- Ready for frontend integration with CORS support
- Easily extensible for additional booking services