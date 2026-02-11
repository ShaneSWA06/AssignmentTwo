# Quick Start Guide - Booking API

## Prerequisites

- Java 17 installed
- Maven installed
- PostgreSQL database accessible (Neon database already configured)

## Step 1: Start the Spring Boot API

Open a terminal and navigate to the Spring Boot project:

```bash
cd c:\Users\shane\Downloads\AssignmentTwo
```

Run the Spring Boot application:

```bash
mvnw spring-boot:run
```

Or on Windows:

```cmd
mvnw.cmd spring-boot:run
```

You should see output like:

```
Started DemoApplication in X.XXX seconds
```

The API is now running at: **http://localhost:8081/user-ws/api/bookings**

## Step 2: Test the API

### Option A: Using Browser

Open your browser and visit:

```
http://localhost:8081/user-ws/api/bookings
```

You should see a JSON array of bookings (might be empty if no bookings exist).

### Option B: Using cURL

**Get all bookings:**

```bash
curl http://localhost:8081/user-ws/api/bookings
```

**Create a test booking:**

```bash
curl -X POST http://localhost:8081/user-ws/api/bookings ^
  -H "Content-Type: application/json" ^
  -d "{\"userId\": 1, \"serviceId\": 1, \"bookingDate\": \"2026-02-15\", \"bookingTime\": \"14:30:00\", \"totalPrice\": 150.00}"
```

**Get unassigned bookings:**

```bash
curl http://localhost:8081/user-ws/api/bookings/unassigned
```

## Step 3: Connect from J2EE Application

Your J2EE application already has the `BookingServiceAPI` class configured. You can use it like this:

```java
// In your controller or service
BookingServiceAPI api = new BookingServiceAPI();

// Get all bookings
List<Booking> allBookings = api.getAllBookings();

// Get bookings for a specific user
List<Booking> userBookings = api.getBookingsByUser(userId);

// Get unassigned bookings
List<Booking> unassigned = api.getUnassignedBookings();

// Create a new booking
Booking newBooking = new Booking();
newBooking.setUserId(1);
newBooking.setServiceId(1);
newBooking.setBookingDate(Date.valueOf("2026-02-15"));
newBooking.setBookingTime(Time.valueOf("14:30:00"));
newBooking.setTotalPrice(150.00);
boolean created = api.createBooking(newBooking);

// Assign a caregiver
boolean assigned = api.assignCaregiver(bookingId, caregiverId);
```

## Step 4: Update Your Controllers (Optional)

### For User Section

Update `BookingController.java` to use the API:

```java
// Replace this:
private BookingDAO bookingDAO;

// With this:
private BookingServiceAPI bookingAPI;

@Override
public void init() throws ServletException {
    bookingAPI = new BookingServiceAPI();
}

// In listMyBookings method:
List<Booking> bookings = bookingAPI.getBookingsByUser(userId);
```

### For Admin Section

Update `AdminBookingController.java` to use the API:

```java
// Replace this:
private BookingDAO bookingDAO;

// With this:
private BookingServiceAPI bookingAPI;

@Override
public void init() throws ServletException {
    bookingAPI = new BookingServiceAPI();
}

// In listBookings method:
if ("unassigned".equals(view)) {
    bookings = bookingAPI.getUnassignedBookings();
} else {
    bookings = bookingAPI.getAllBookings();
}
```

## Troubleshooting

### API Not Starting

**Error:** Port 8081 already in use
**Solution:** Change the port in `application.properties`:

```properties
server.port=8082
```

And update the J2EE API URL:

```java
private static final String API_BASE_URL = "http://localhost:8082/user-ws/api/bookings";
```

### Database Connection Error

**Error:** Connection refused or authentication failed
**Solution:** Verify your database credentials in `application.properties`

### CORS Error from J2EE

**Error:** CORS policy blocking requests
**Solution:** The controller already has `@CrossOrigin(origins = "*")` enabled

### JSON Parsing Error

**Error:** Unable to parse booking response
**Solution:** Check the API response format matches the expected structure

## Monitoring

### Check API Health

```bash
curl http://localhost:8081/user-ws/api/bookings
```

### View Logs

The Spring Boot application logs will show:

- SQL queries being executed
- Request/response details
- Any errors

### Database Verification

Connect to your PostgreSQL database and run:

```sql
SELECT * FROM booking ORDER BY created_at DESC LIMIT 10;
```

## Next Steps

1. ✅ API is running
2. ✅ Test endpoints with cURL or browser
3. ✅ Integrate with J2EE application
4. 📝 Add error handling in J2EE controllers
5. 📝 Add validation for booking data
6. 📝 Implement authentication/authorization
7. 📝 Add logging and monitoring

## Useful Commands

**Stop the API:**
Press `Ctrl+C` in the terminal where it's running

**Rebuild the project:**

```bash
mvnw clean install
```

**Run tests:**

```bash
mvnw test
```

**Package as WAR:**

```bash
mvnw package
```

The WAR file will be in `target/demo-0.0.1-SNAPSHOT.war`

## API Endpoints Quick Reference

| Endpoint                              | Method | Description             |
| ------------------------------------- | ------ | ----------------------- |
| `/api/bookings`                       | GET    | Get all bookings        |
| `/api/bookings/{id}`                  | GET    | Get booking by ID       |
| `/api/bookings/user/{userId}`         | GET    | Get user bookings       |
| `/api/bookings/caregiver/{id}`        | GET    | Get caregiver bookings  |
| `/api/bookings/unassigned`            | GET    | Get unassigned bookings |
| `/api/bookings`                       | POST   | Create booking          |
| `/api/bookings/{id}`                  | PUT    | Update booking          |
| `/api/bookings/{id}/assign-caregiver` | PATCH  | Assign caregiver        |
| `/api/bookings/{id}`                  | DELETE | Delete booking          |

For detailed documentation, see `BOOKING_API_DOCUMENTATION.md`
