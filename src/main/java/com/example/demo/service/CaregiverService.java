package com.example.demo.service;

import com.example.demo.model.Caregiver;
import com.example.demo.repository.CaregiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CaregiverService {

    @Autowired
    private CaregiverRepository caregiverRepository;

    public List<Caregiver> getAllCaregivers() {
        return caregiverRepository.findAll();
    }

    public List<Caregiver> getAvailableCaregivers() {
        return caregiverRepository.findByIsAvailableTrue();
    }

    public Optional<Caregiver> getCaregiverById(Integer id) {
        return caregiverRepository.findById(id);
    }

    public Optional<Caregiver> getCaregiverByUserId(Integer userId) {
        return caregiverRepository.findByUserId(userId);
    }

    public Caregiver saveCaregiver(Caregiver caregiver) {
        return caregiverRepository.save(caregiver);
    }

    public void deleteCaregiver(Integer id) {
        caregiverRepository.deleteById(id);
    }
}
