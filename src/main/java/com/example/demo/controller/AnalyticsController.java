package com.example.demo.controller;

import com.example.demo.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        System.out.println("DEBUG - Analytics API: Fetching Summary stats");
        return ResponseEntity.ok(analyticsService.getDashboardStats());
    }

    @GetMapping("/sales-trends")
    public ResponseEntity<Map<String, Double>> getSalesTrends() {
        System.out.println("DEBUG - Analytics API: Fetching Sales Trends");
        return ResponseEntity.ok(analyticsService.getSalesTrends());
    }
}
