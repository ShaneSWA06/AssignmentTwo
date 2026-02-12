package com.example.demo.service;

import com.example.demo.model.Service;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service("serviceEntityService")
public class ServiceEntityService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.example.demo.model.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<com.example.demo.model.Service> getActiveServices() {
        return serviceRepository.findByIsActiveTrue();
    }

    public List<com.example.demo.model.Service> getServicesByCategory(Integer categoryId) {
        return serviceRepository.findByCategoryId(categoryId);
    }

    public Optional<com.example.demo.model.Service> getServiceById(Integer id) {
        return serviceRepository.findById(id);
    }

    public com.example.demo.model.Service saveService(com.example.demo.model.Service service) {
        return serviceRepository.save(service);
    }

    public void deleteService(Integer id) {
        serviceRepository.deleteById(id);
    }
}
