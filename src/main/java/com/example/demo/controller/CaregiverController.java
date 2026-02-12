package com.example.demo.controller;

import com.example.demo.model.Caregiver;
import com.example.demo.service.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caregivers")
@CrossOrigin(origins = "*")
public class CaregiverController {

    @Autowired
    private CaregiverService caregiverService;

    @GetMapping
    public List<Caregiver> getAllCaregivers() {
        return caregiverService.getAllCaregivers();
    }

    @GetMapping("/available")
    public List<Caregiver> getAvailableCaregivers() {
        return caregiverService.getAvailableCaregivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caregiver> getCaregiverById(@PathVariable Integer id) {
        return caregiverService.getCaregiverById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Caregiver> getCaregiverByUserId(@PathVariable Integer userId) {
        return caregiverService.getCaregiverByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Caregiver createCaregiver(@RequestBody Caregiver caregiver) {
        return caregiverService.saveCaregiver(caregiver);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caregiver> updateCaregiver(@PathVariable Integer id, @RequestBody Caregiver caregiver) {
        return caregiverService.getCaregiverById(id)
                .map(existing -> {
                    caregiver.setCaregiverId(id);
                    return ResponseEntity.ok(caregiverService.saveCaregiver(caregiver));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaregiver(@PathVariable Integer id) {
        caregiverService.deleteCaregiver(id);
        return ResponseEntity.ok().build();
    }
}
