package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AppUser user;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;
    
    @Column(name = "caregiver_id")
    private Integer caregiverId;
    
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;
    
    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;
    
    @Column(name = "status", length = 50)
    private String status = "Pending";
    
    @Column(name = "caregiver_status", length = 50)
    private String caregiverStatus = "Pending";
    
    @Column(name = "payment_status", length = 50)
    private String paymentStatus = "Unpaid";
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "pickup_address", length = 500)
    private String pickupAddress;
    
    @Column(name = "destination_address", length = 500)
    private String destinationAddress;
    
    @Column(name = "total_price")
    private Double totalPrice;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "clock_in_time")
    private LocalDateTime clockInTime;

    @Column(name = "clock_out_time")
    private LocalDateTime clockOutTime;

    @Column(name = "clock_in_location", length = 500)
    private String clockInLocation;

    @Column(name = "clock_out_location", length = 500)
    private String clockOutLocation;
    
    // Constructors
    public Booking() {
    }
    
    public Booking(Integer userId, Integer serviceId, LocalDate bookingDate, LocalTime bookingTime) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = "Pending";
        if (caregiverStatus == null) caregiverStatus = "Pending";
        if (paymentStatus == null) paymentStatus = "Unpaid";
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Integer getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AppUser getUser() {
        return user;
    }

    public Integer getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    
    public Integer getCaregiverId() {
        return caregiverId;
    }
    
    public void setCaregiverId(Integer caregiverId) {
        this.caregiverId = caregiverId;
    }
    
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public LocalTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCaregiverStatus() {
        return caregiverStatus;
    }
    
    public void setCaregiverStatus(String caregiverStatus) {
        this.caregiverStatus = caregiverStatus;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getPickupAddress() {
        return pickupAddress;
    }
    
    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }
    
    public String getDestinationAddress() {
        return destinationAddress;
    }
    
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
    
    public Double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(LocalDateTime clockInTime) {
        this.clockInTime = clockInTime;
    }

    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public String getClockInLocation() {
        return clockInLocation;
    }

    public void setClockInLocation(String clockInLocation) {
        this.clockInLocation = clockInLocation;
    }

    public String getClockOutLocation() {
        return clockOutLocation;
    }

    public void setClockOutLocation(String clockOutLocation) {
        this.clockOutLocation = clockOutLocation;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", caregiverId=" + caregiverId +
                ", bookingDate=" + bookingDate +
                ", bookingTime=" + bookingTime +
                ", status='" + status + '\'' +
                ", caregiverStatus='" + caregiverStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
