package com.example.demo.service;

import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        java.util.List<com.example.demo.model.Booking> allBookings = bookingRepository.findAll();
        System.out.println("DEBUG - Analytics: Found " + allBookings.size() + " total bookings in database.");

        long totalBookings = allBookings.size();
        double totalSales = allBookings.stream()
                .filter(b -> "Completed".equalsIgnoreCase(b.getStatus()))
                .mapToDouble(b -> {
                    double price = b.getTotalPrice() != null ? b.getTotalPrice() : 0.0;
                    if (price > 0) System.out.println("DEBUG - Analytics: Found Sale: " + price + " for booking ID " + b.getBookingId());
                    return price;
                })
                .sum();

        Map<String, Long> statusDistribution = allBookings.stream()
                .collect(Collectors.groupingBy(b -> b.getStatus() != null ? b.getStatus() : "Pending", Collectors.counting()));
        
        System.out.println("DEBUG - Analytics: Status Breakdown: " + statusDistribution);
        System.out.println("DEBUG - Analytics: Total Sales Calculated: $" + totalSales);

        stats.put("totalBookings", totalBookings);
        stats.put("totalSales", totalSales);
        stats.put("statusDistribution", statusDistribution);
        
        return stats;
    }

    public Map<String, Double> getSalesTrends() {
        // Group by Month (simplified for this demo)
        return bookingRepository.findAll().stream()
                .filter(b -> "Completed".equalsIgnoreCase(b.getStatus()) && b.getBookingDate() != null)
                .collect(Collectors.groupingBy(
                        b -> b.getBookingDate().getMonth().toString(),
                        Collectors.summingDouble(b -> b.getTotalPrice() != null ? b.getTotalPrice() : 0.0)
                ));
    }
}
