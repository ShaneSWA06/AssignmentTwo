# Booking API Implementation Summary

## What Was Created

### Spring Boot Project (AssignmentTwo)

#### 1. **Dependencies Added** (`pom.xml`)

- `spring-boot-starter-data-jpa` - For database ORM
- `postgresql` - PostgreSQL JDBC driver

#### 2. **Configuration** (`application.properties`)

- Server running on port **8081**
- Context path: `/user-ws`
- Database connection to your PostgreSQL Neon database
- JPA/Hibernate configuration with auto-update schema

#### 3. **Model Layer**

**`Booking.java`** - JPA Entity

- Maps to `booking` table in database
- All fields: bookingId, userId, serviceId, caregiverId, bookingDate, bookingTime, status, caregiverStatus, paymentStatus, notes, pickupAddress, destinationAddress, totalPrice, createdAt, updatedAt
- Automatic timestamp management with `@PrePersist` and `@PreUpdate`

#### 4. **Repository Layer**

**`BookingRepository.java`** - Spring Data JPA Repository

- Extends `JpaRepository` for automatic CRUD operations
- Custom query methods:
  - `findByUserIdOrderByBookingDateDescBookingTimeDesc()`
  - `findByCaregiverIdOrderByBookingDateDescBookingTimeDesc()`
  - `findUnassignedBookings()` - Custom JPQL query
  - `findByStatusOrderByBookingDateDesc()`
  - `findByPaymentStatusOrderByBookingDateDesc()`
  - `findByCaregiverStatusOrderByBookingDateDesc()`

#### 5. **Service Layer**

**`BookingService.java`** - Business Logic

- All CRUD operations
- Status management methods
- Caregiver assignment logic
- Partial update support (only updates non-null fields)

#### 6. **Controller Layer**

**`BookingController.java`** - REST API Endpoints

- **14 endpoints** covering all booking operations
- Full CRUD support
- Status management endpoints
- CORS enabled for J2EE integration

### J2EE Project Updates

#### Updated `BookingServiceAPI.java`

- Fixed `createBooking()` method to use proper JSON field names (camelCase)
- Fixed `assignCaregiver()` to use the dedicated PATCH endpoint
- Added `escapeJson()` helper method for proper JSON string escaping
- Improved error handling

## API Endpoints Summary

| Method | Endpoint                                       | Description                    |
| ------ | ---------------------------------------------- | ------------------------------ |
| GET    | `/api/bookings`                                | Get all bookings               |
| GET    | `/api/bookings/{id}`                           | Get booking by ID              |
| GET    | `/api/bookings/user/{userId}`                  | Get user's bookings            |
| GET    | `/api/bookings/caregiver/{caregiverId}`        | Get caregiver's bookings       |
| GET    | `/api/bookings/unassigned`                     | Get unassigned bookings        |
| GET    | `/api/bookings/status/{status}`                | Get bookings by status         |
| GET    | `/api/bookings/payment-status/{paymentStatus}` | Get bookings by payment status |
| POST   | `/api/bookings`                                | Create new booking             |
| PUT    | `/api/bookings/{id}`                           | Update booking (partial)       |
| PATCH  | `/api/bookings/{id}/status`                    | Update booking status          |
| PATCH  | `/api/bookings/{id}/payment-status`            | Update payment status          |
| PATCH  | `/api/bookings/{id}/assign-caregiver`          | Assign caregiver               |
| PATCH  | `/api/bookings/{id}/caregiver-status`          | Update caregiver status        |
| DELETE | `/api/bookings/{id}`                           | Delete booking                 |

## How to Run

### 1. Start Spring Boot API

```bash
cd c:\Users\shane\Downloads\AssignmentTwo
mvnw spring-boot:run
```

The API will be available at: `http://localhost:8081/user-ws/api/bookings`

### 2. Your J2EE Application

Your J2EE application is already configured to connect to the Spring Boot API at:

```java
private static final String API_BASE_URL = "http://localhost:8081/user-ws/api/bookings";
```

## Integration Points

### User Section (J2EE)

The `BookingController` in your J2EE project uses:

- Direct database access via `BookingDAO` for listing user bookings
- Can be updated to use API if needed

### Admin Section (J2EE)

The `AdminBookingController` in your J2EE project uses:

- Direct database access via `BookingDAO` for all operations
- Can be updated to use API if needed

### API Client Usage

The `BookingServiceAPI.java` class provides methods to:

- `getUnassignedBookings()` - Fetch unassigned bookings
- `getBookingsByCaregiver(int caregiverId)` - Fetch caregiver's bookings
- `assignCaregiver(int bookingId, int caregiverId)` - Assign caregiver
- `createBooking(Booking booking)` - Create new booking

## Next Steps

### Option 1: Keep Hybrid Approach

- User/Admin sections continue using direct database access
- API is available for external integrations or specific features

### Option 2: Full API Migration

Update J2EE controllers to use `BookingServiceAPI` instead of `BookingDAO`:

**Example for User Controller:**

```java
// Instead of:
List<Booking> bookings = bookingDAO.getBookingsByUser(userId);

// Use:
BookingServiceAPI api = new BookingServiceAPI();
List<Booking> bookings = api.getBookingsByUser(userId);
```

You would need to add this method to `BookingServiceAPI.java`:

```java
public List<Booking> getBookingsByUser(int userId) {
    try {
        String json = sendRequest(API_BASE_URL + "/user/" + userId, "GET", null);
        return parseBookingList(json);
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}
```

## Testing the API

### Test with cURL:

**Create a booking:**

```bash
curl -X POST http://localhost:8081/user-ws/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "serviceId": 1,
    "bookingDate": "2026-02-15",
    "bookingTime": "14:30:00",
    "totalPrice": 150.00,
    "status": "Pending"
  }'
```

**Get all bookings:**

```bash
curl http://localhost:8081/user-ws/api/bookings
```

**Assign caregiver:**

```bash
curl -X PATCH "http://localhost:8081/user-ws/api/bookings/1/assign-caregiver?caregiverId=1"
```

## Files Created/Modified

### Spring Boot (AssignmentTwo)

- ✅ `pom.xml` - Added dependencies
- ✅ `src/main/resources/application.properties` - Database configuration
- ✅ `src/main/java/com/example/demo/model/Booking.java` - Entity
- ✅ `src/main/java/com/example/demo/repository/BookingRepository.java` - Repository
- ✅ `src/main/java/com/example/demo/service/BookingService.java` - Service
- ✅ `src/main/java/com/example/demo/controller/BookingController.java` - REST Controller
- ✅ `BOOKING_API_DOCUMENTATION.md` - API documentation

### J2EE Project

- ✅ `src/main/java/service/BookingServiceAPI.java` - Updated API client

## Database Schema

The API expects this table structure (which you already have):

```sql
CREATE TABLE booking (
    booking_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    service_id INTEGER NOT NULL,
    caregiver_id INTEGER,
    booking_date DATE NOT NULL,
    booking_time TIME NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    caregiver_status VARCHAR(50) DEFAULT 'Pending',
    payment_status VARCHAR(50) DEFAULT 'Unpaid',
    notes TEXT,
    pickup_address VARCHAR(500),
    destination_address VARCHAR(500),
    total_price DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Notes

- The API uses **camelCase** for JSON field names (e.g., `bookingId`, `userId`)
- The database uses **snake_case** for column names (e.g., `booking_id`, `user_id`)
- JPA automatically handles the mapping between these naming conventions
- CORS is enabled to allow requests from your J2EE application
