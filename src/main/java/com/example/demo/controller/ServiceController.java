package com.example.demo.controller;

import com.example.demo.model.Service;
import com.example.demo.service.ServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceEntityService serviceService;

    @GetMapping
    public List<Service> getAllServices(@RequestParam(required = false) Integer categoryId) {
        if (categoryId != null) {
            return serviceService.getServicesByCategory(categoryId);
        }
        return serviceService.getAllServices();
    }

    @GetMapping("/active")
    public List<Service> getActiveServices() {
        return serviceService.getActiveServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Integer id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceService.saveService(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Integer id, @RequestBody Service service) {
        return serviceService.getServiceById(id)
                .map(existing -> {
                    service.setServiceId(id);
                    return ResponseEntity.ok(serviceService.saveService(service));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok().build();
    }
}
