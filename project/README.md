# Voyageur Mock Backend 🚀

A simple Spring Boot mock backend for the Voyageur travel booking app (Trip.com clone).

## Features ✅

- **Mock Services**: Flights, Hotels, Cars, Activities
- **Search & Filtering**: By location, price, type, etc.
- **Availability Checking**: Real-time inventory management
- **Booking Simulation**: Simple booking with inventory updates
- **API Documentation**: Built-in Swagger UI
- **Student-Friendly**: Clean, simple code structure

## Quick Start 🏃‍♂️

1. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Access the API**:
   - Main URL: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api-docs

## API Endpoints 📋

### Flights ✈️
- `GET /flights` - Get all flights
- `GET /flights?from=NYC&to=LAX&sort=price_asc` - Search flights
- `GET /flights/availability?id=F001` - Check availability
- `POST /flights/book?id=F001` - Book flight

### Hotels 🏨
- `GET /hotels` - Get all hotels
- `GET /hotels?location=NYC&minStars=4&sort=rating_desc` - Search hotels
- `GET /hotels/availability?id=H001` - Check availability
- `POST /hotels/book?id=H001` - Book hotel

### Cars 🚗
- `GET /cars` - Get all cars
- `GET /cars?location=NYC&type=SUV&sort=price_asc` - Search cars
- `GET /cars/availability?id=C001` - Check availability
- `POST /cars/book?id=C001` - Book car

### Activities 🎯
- `GET /activities` - Get all activities
- `GET /activities?location=NYC&category=Entertainment` - Search activities
- `GET /activities/availability?id=A001` - Check availability
- `POST /activities/book?id=A001` - Book activity

### Unified Search 🔍
- `GET /search?type=all&location=NYC` - Search everything
- `GET /search?type=hotel&location=MIA&sort=price_asc` - Search specific type

## Sample Requests 💡

```bash
# Get all flights from NYC to LAX
curl "http://localhost:8080/flights?from=NYC&to=LAX"

# Search hotels in NYC, 4+ stars, sorted by rating
curl "http://localhost:8080/hotels?location=NYC&minStars=4&sort=rating_desc"

# Check if flight F001 is available
curl "http://localhost:8080/flights/availability?id=F001"

# Book a hotel room
curl -X POST "http://localhost:8080/hotels/book?id=H001"

# Search everything in NYC
curl "http://localhost:8080/search?location=NYC"
```

## Project Structure 📁

```
src/
├── main/java/com/example/mock/
│   ├── controller/          # REST controllers
│   ├── service/            # Business logic
│   ├── dto/               # Data transfer objects
│   └── VoyageurMockApplication.java
└── resources/
    ├── static/            # Mock JSON data
    │   ├── flights.json
    │   ├── hotels.json
    │   ├── cars.json
    │   └── activities.json
    └── application.properties
```

## Features Implemented ✅

### Week 1: Mock Services + Basic Search
- ✅ Mock controllers for all services
- ✅ Static JSON data loading
- ✅ Basic filtering by location, price range
- ✅ Availability endpoints

### Week 2: Search Aggregation + Inventory
- ✅ Unified search endpoint
- ✅ Sorting by price, rating
- ✅ Simple inventory management with ConcurrentHashMap
- ✅ Mock booking functionality

### Week 3: Documentation + Testing
- ✅ Swagger/OpenAPI documentation
- ✅ Clean API structure
- ✅ Ready for Postman testing

## Testing with Postman 📮

Import these URLs into Postman:

1. **Get All Flights**: `GET http://localhost:8080/flights`
2. **Search NYC Hotels**: `GET http://localhost:8080/hotels?location=NYC`
3. **Check Availability**: `GET http://localhost:8080/flights/availability?id=F001`
4. **Book Flight**: `POST http://localhost:8080/flights/book?id=F001`
5. **Unified Search**: `GET http://localhost:8080/search?type=all&location=NYC`

## Mock Data 📊

The app includes realistic mock data for:
- 4 Flights (NYC↔LAX, NYC→MIA, CHI→LAX)
- 4 Hotels (NYC, Miami, Chicago)
- 4 Cars (Different types and locations)
- 4 Activities (Tours, shows, sports, culture)

All data includes pricing, availability, and relevant details!

## Next Steps 🚀

- Add more realistic mock data
- Implement date-based filtering
- Add pagination for large result sets
- Create a simple frontend
- Deploy to cloud platform

---

**Built with ❤️ for the Voyageur travel booking app**