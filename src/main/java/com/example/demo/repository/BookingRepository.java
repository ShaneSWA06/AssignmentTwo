package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
    /**
     * Find all bookings for a specific user
     */
    List<Booking> findByUserIdOrderByBookingDateDescBookingTimeDesc(Integer userId);
    
    /**
     * Find all bookings for a specific caregiver
     */
    List<Booking> findByCaregiverIdOrderByBookingDateDescBookingTimeDesc(Integer caregiverId);
    
    /**
     * Find unassigned bookings (no caregiver assigned yet)
     */
    @Query("SELECT b FROM Booking b WHERE b.caregiverId IS NULL AND b.status IN ('Pending', 'Confirmed') ORDER BY b.bookingDate ASC, b.bookingTime ASC")
    List<Booking> findUnassignedBookings();
    
    /**
     * Find bookings by status
     */
    List<Booking> findByStatusOrderByBookingDateDesc(String status);
    
    /**
     * Find bookings by payment status
     */
    List<Booking> findByPaymentStatusOrderByBookingDateDesc(String paymentStatus);
    
    /**
     * Find bookings by caregiver status
     */
    List<Booking> findByCaregiverStatusOrderByBookingDateDesc(String caregiverStatus);
    
    /**
     * Find all bookings ordered by date
     */
    List<Booking> findAllByOrderByBookingDateDescBookingTimeDesc();
}
