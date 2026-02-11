package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    /**
     * Get all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByBookingDateDescBookingTimeDesc();
    }
    
    /**
     * Get booking by ID
     */
    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }
    
    /**
     * Get bookings by user ID
     */
    public List<Booking> getBookingsByUserId(Integer userId) {
        return bookingRepository.findByUserIdOrderByBookingDateDescBookingTimeDesc(userId);
    }
    
    /**
     * Get bookings by caregiver ID
     */
    public List<Booking> getBookingsByCaregiver(Integer caregiverId) {
        return bookingRepository.findByCaregiverIdOrderByBookingDateDescBookingTimeDesc(caregiverId);
    }
    
    /**
     * Get unassigned bookings
     */
    public List<Booking> getUnassignedBookings() {
        return bookingRepository.findUnassignedBookings();
    }
    
    /**
     * Get bookings by status
     */
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatusOrderByBookingDateDesc(status);
    }
    
    /**
     * Get bookings by payment status
     */
    public List<Booking> getBookingsByPaymentStatus(String paymentStatus) {
        return bookingRepository.findByPaymentStatusOrderByBookingDateDesc(paymentStatus);
    }
    
    /**
     * Create a new booking
     */
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    /**
     * Update an existing booking
     */
    public Booking updateBooking(Integer id, Booking bookingDetails) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            
            // Update fields if provided
            if (bookingDetails.getServiceId() != null) {
                booking.setServiceId(bookingDetails.getServiceId());
            }
            if (bookingDetails.getCaregiverId() != null) {
                booking.setCaregiverId(bookingDetails.getCaregiverId());
            }
            if (bookingDetails.getBookingDate() != null) {
                booking.setBookingDate(bookingDetails.getBookingDate());
            }
            if (bookingDetails.getBookingTime() != null) {
                booking.setBookingTime(bookingDetails.getBookingTime());
            }
            if (bookingDetails.getStatus() != null) {
                booking.setStatus(bookingDetails.getStatus());
            }
            if (bookingDetails.getCaregiverStatus() != null) {
                booking.setCaregiverStatus(bookingDetails.getCaregiverStatus());
            }
            if (bookingDetails.getPaymentStatus() != null) {
                booking.setPaymentStatus(bookingDetails.getPaymentStatus());
            }
            if (bookingDetails.getNotes() != null) {
                booking.setNotes(bookingDetails.getNotes());
            }
            if (bookingDetails.getPickupAddress() != null) {
                booking.setPickupAddress(bookingDetails.getPickupAddress());
            }
            if (bookingDetails.getDestinationAddress() != null) {
                booking.setDestinationAddress(bookingDetails.getDestinationAddress());
            }
            if (bookingDetails.getTotalPrice() != null) {
                booking.setTotalPrice(bookingDetails.getTotalPrice());
            }
            
            return bookingRepository.save(booking);
        }
        
        return null;
    }
    
    /**
     * Update booking status
     */
    public Booking updateBookingStatus(Integer id, String status) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        
        return null;
    }
    
    /**
     * Update payment status
     */
    public Booking updatePaymentStatus(Integer id, String paymentStatus) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setPaymentStatus(paymentStatus);
            return bookingRepository.save(booking);
        }
        
        return null;
    }
    
    /**
     * Assign caregiver to booking
     */
    public Booking assignCaregiver(Integer bookingId, Integer caregiverId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setCaregiverId(caregiverId);
            booking.setStatus("Confirmed");
            booking.setCaregiverStatus("Accepted");
            return bookingRepository.save(booking);
        }
        
        return null;
    }
    
    /**
     * Update caregiver status
     */
    public Booking updateCaregiverStatus(Integer id, String caregiverStatus) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setCaregiverStatus(caregiverStatus);
            return bookingRepository.save(booking);
        }
        
        return null;
    }

    /**
     * Caregiver clock in
     */
    public Booking clockIn(Integer id, String location) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setClockInTime(LocalDateTime.now());
            booking.setClockInLocation(location);
            booking.setStatus("In-Progress");
            return bookingRepository.save(booking);
        }
        return null;
    }

    /**
     * Caregiver clock out
     */
    public Booking clockOut(Integer id, String location) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setClockOutTime(LocalDateTime.now());
            booking.setClockOutLocation(location);
            booking.setStatus("Completed");
            return bookingRepository.save(booking);
        }
        return null;
    }
    
    /**
     * Delete a booking
     */
    public boolean deleteBooking(Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
