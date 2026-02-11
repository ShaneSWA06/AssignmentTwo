# Booking API Documentation

## Base URL

```
http://localhost:8081/user-ws/api/bookings
```

## Endpoints

### 1. Get All Bookings

**GET** `/api/bookings`

**Response:** Array of Booking objects

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings
```

---

### 2. Get Booking by ID

**GET** `/api/bookings/{id}`

**Parameters:**

- `id` (path) - Booking ID

**Response:** Single Booking object

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings/1
```

---

### 3. Get Bookings by User ID

**GET** `/api/bookings/user/{userId}`

**Parameters:**

- `userId` (path) - User ID

**Response:** Array of Booking objects

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings/user/5
```

---

### 4. Get Bookings by Caregiver ID

**GET** `/api/bookings/caregiver/{caregiverId}`

**Parameters:**

- `caregiverId` (path) - Caregiver ID

**Response:** Array of Booking objects

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings/caregiver/3
```

---

### 5. Get Unassigned Bookings

**GET** `/api/bookings/unassigned`

**Description:** Returns bookings with no caregiver assigned and status 'Pending' or 'Confirmed'

**Response:** Array of Booking objects

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings/unassigned
```

---

### 6. Get Bookings by Status

**GET** `/api/bookings/status/{status}`

**Parameters:**

- `status` (path) - Booking status (e.g., "Pending", "Confirmed", "Completed", "Cancelled")

**Response:** Array of Booking objects

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings/status/Pending
```

---

### 7. Get Bookings by Payment Status

**GET** `/api/bookings/payment-status/{paymentStatus}`

**Parameters:**

- `paymentStatus` (path) - Payment status (e.g., "Paid", "Unpaid")

**Response:** Array of Booking objects

**Example:**

```bash
curl -X GET http://localhost:8081/user-ws/api/bookings/payment-status/Unpaid
```

---

### 8. Create Booking

**POST** `/api/bookings`

**Request Body:**

```json
{
  "userId": 5,
  "serviceId": 2,
  "bookingDate": "2026-02-15",
  "bookingTime": "14:30:00",
  "status": "Pending",
  "caregiverStatus": "Pending",
  "paymentStatus": "Unpaid",
  "notes": "Please arrive 10 minutes early",
  "pickupAddress": "123 Main St",
  "destinationAddress": "456 Hospital Rd",
  "totalPrice": 150.0,
  "caregiverId": null
}
```

**Response:** Created Booking object (HTTP 201)

**Example:**

```bash
curl -X POST http://localhost:8081/user-ws/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 5,
    "serviceId": 2,
    "bookingDate": "2026-02-15",
    "bookingTime": "14:30:00",
    "totalPrice": 150.00
  }'
```

---

### 9. Update Booking

**PUT** `/api/bookings/{id}`

**Parameters:**

- `id` (path) - Booking ID

**Request Body:** Partial Booking object (only fields to update)

```json
{
  "status": "Confirmed",
  "caregiverId": 3,
  "notes": "Updated notes"
}
```

**Response:** Updated Booking object

**Example:**

```bash
curl -X PUT http://localhost:8081/user-ws/api/bookings/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "Confirmed",
    "caregiverId": 3
  }'
```

---

### 10. Update Booking Status

**PATCH** `/api/bookings/{id}/status`

**Parameters:**

- `id` (path) - Booking ID
- `status` (query) - New status value

**Response:** Updated Booking object

**Example:**

```bash
curl -X PATCH "http://localhost:8081/user-ws/api/bookings/1/status?status=Completed"
```

---

### 11. Update Payment Status

**PATCH** `/api/bookings/{id}/payment-status`

**Parameters:**

- `id` (path) - Booking ID
- `paymentStatus` (query) - New payment status

**Response:** Updated Booking object

**Example:**

```bash
curl -X PATCH "http://localhost:8081/user-ws/api/bookings/1/payment-status?paymentStatus=Paid"
```

---

### 12. Assign Caregiver

**PATCH** `/api/bookings/{id}/assign-caregiver`

**Parameters:**

- `id` (path) - Booking ID
- `caregiverId` (query) - Caregiver ID to assign

**Description:** Assigns a caregiver and automatically sets status to "Confirmed" and caregiverStatus to "Accepted"

**Response:** Updated Booking object

**Example:**

```bash
curl -X PATCH "http://localhost:8081/user-ws/api/bookings/1/assign-caregiver?caregiverId=3"
```

---

### 13. Update Caregiver Status

**PATCH** `/api/bookings/{id}/caregiver-status`

**Parameters:**

- `id` (path) - Booking ID
- `caregiverStatus` (query) - New caregiver status (e.g., "Pending", "Accepted", "Rejected")

**Response:** Updated Booking object

**Example:**

```bash
curl -X PATCH "http://localhost:8081/user-ws/api/bookings/1/caregiver-status?caregiverStatus=Accepted"
```

---

### 14. Delete Booking

**DELETE** `/api/bookings/{id}`

**Parameters:**

- `id` (path) - Booking ID

**Response:** HTTP 204 (No Content) on success

**Example:**

```bash
curl -X DELETE http://localhost:8081/user-ws/api/bookings/1
```

---

## Booking Object Structure

```json
{
  "bookingId": 1,
  "userId": 5,
  "serviceId": 2,
  "caregiverId": 3,
  "bookingDate": "2026-02-15",
  "bookingTime": "14:30:00",
  "status": "Confirmed",
  "caregiverStatus": "Accepted",
  "paymentStatus": "Paid",
  "notes": "Please arrive 10 minutes early",
  "pickupAddress": "123 Main St",
  "destinationAddress": "456 Hospital Rd",
  "totalPrice": 150.0,
  "createdAt": "2026-02-11T14:30:00",
  "updatedAt": "2026-02-11T15:00:00"
}
```

## Status Values

### Booking Status

- `Pending` - Initial status
- `Confirmed` - Booking confirmed (usually when caregiver assigned)
- `Completed` - Service completed
- `Cancelled` - Booking cancelled

### Caregiver Status

- `Pending` - Waiting for caregiver response
- `Accepted` - Caregiver accepted the booking
- `Rejected` - Caregiver rejected the booking

### Payment Status

- `Unpaid` - Payment not received
- `Paid` - Payment completed

## Error Responses

### 404 Not Found

```json
{
  "timestamp": "2026-02-11T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Booking not found",
  "path": "/api/bookings/999"
}
```

### 400 Bad Request

```json
{
  "timestamp": "2026-02-11T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid booking data",
  "path": "/api/bookings"
}
```
