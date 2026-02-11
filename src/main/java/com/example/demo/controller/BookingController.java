package com.example.demo.controller;

import com.example.demo.model.Booking;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Allow requests from J2EE application
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    /**
     * GET /api/bookings - Get all bookings
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * GET /api/bookings/{id} - Get booking by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/bookings/user/{userId} - Get bookings by user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Integer userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * GET /api/bookings/caregiver/{caregiverId} - Get bookings by caregiver ID
     */
    @GetMapping("/caregiver/{caregiverId}")
    public ResponseEntity<List<Booking>> getBookingsByCaregiver(@PathVariable Integer caregiverId) {
        List<Booking> bookings = bookingService.getBookingsByCaregiver(caregiverId);
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * GET /api/bookings/unassigned - Get unassigned bookings
     */
    @GetMapping("/unassigned")
    public ResponseEntity<List<Booking>> getUnassignedBookings() {
        List<Booking> bookings = bookingService.getUnassignedBookings();
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * GET /api/bookings/status/{status} - Get bookings by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * GET /api/bookings/payment-status/{paymentStatus} - Get bookings by payment status
     */
    @GetMapping("/payment-status/{paymentStatus}")
    public ResponseEntity<List<Booking>> getBookingsByPaymentStatus(@PathVariable String paymentStatus) {
        List<Booking> bookings = bookingService.getBookingsByPaymentStatus(paymentStatus);
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * POST /api/bookings - Create a new booking
     */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * PUT /api/bookings/{id} - Update an existing booking
     */
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH or POST /api/bookings/{id}/status - Update booking status
     */
    @RequestMapping(value = "/{id}/status", method = {RequestMethod.PATCH, RequestMethod.POST})
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Integer id, 
            @RequestParam String status) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, status);
        
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH or POST /api/bookings/{id}/payment-status - Update payment status
     */
    @RequestMapping(value = "/{id}/payment-status", method = {RequestMethod.PATCH, RequestMethod.POST})
    public ResponseEntity<Booking> updatePaymentStatus(
            @PathVariable Integer id, 
            @RequestParam String paymentStatus) {
        Booking updatedBooking = bookingService.updatePaymentStatus(id, paymentStatus);
        
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH or POST /api/bookings/{id}/assign-caregiver - Assign caregiver to booking
     */
    @RequestMapping(value = "/{id}/assign-caregiver", method = {RequestMethod.PATCH, RequestMethod.POST})
    public ResponseEntity<Booking> assignCaregiver(
            @PathVariable Integer id, 
            @RequestParam Integer caregiverId) {
        Booking updatedBooking = bookingService.assignCaregiver(id, caregiverId);
        
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * PATCH /api/bookings/{id}/caregiver-status - Update caregiver status
     */
    @PatchMapping("/{id}/caregiver-status")
    public ResponseEntity<Booking> updateCaregiverStatus(
            @PathVariable Integer id, 
            @RequestParam String caregiverStatus) {
        Booking updatedBooking = bookingService.updateCaregiverStatus(id, caregiverStatus);
        
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * POST /api/bookings/{id}/clock-in - Caregiver clock in
     */
    @RequestMapping(value = "/{id}/clock-in", method = {RequestMethod.PATCH, RequestMethod.POST})
    public ResponseEntity<Booking> clockIn(
            @PathVariable Integer id,
            @RequestParam(required = false) String location) {
        System.out.println("DEBUG - Spring Boot: Clock-in request for ID " + id + " at " + location);
        Booking updatedBooking = bookingService.clockIn(id, location != null ? location : "Unknown");
        if (updatedBooking != null) {
            System.out.println("DEBUG - Spring Boot: Clock-in success for ID " + id);
            return ResponseEntity.ok(updatedBooking);
        } else {
            System.out.println("DEBUG - Spring Boot: Clock-in FAILED (Not Found) for ID " + id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/bookings/{id}/clock-out - Caregiver clock out
     */
    @RequestMapping(value = "/{id}/clock-out", method = {RequestMethod.PATCH, RequestMethod.POST})
    public ResponseEntity<Booking> clockOut(
            @PathVariable Integer id,
            @RequestParam(required = false) String location) {
        System.out.println("DEBUG - Spring Boot: Clock-out request for ID " + id + " at " + location);
        Booking updatedBooking = bookingService.clockOut(id, location != null ? location : "Unknown");
        if (updatedBooking != null) {
            System.out.println("DEBUG - Spring Boot: Clock-out success for ID " + id);
            return ResponseEntity.ok(updatedBooking);
        } else {
            System.out.println("DEBUG - Spring Boot: Clock-out FAILED (Not Found) for ID " + id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/bookings/{id} - Delete a booking
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        boolean deleted = bookingService.deleteBooking(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
