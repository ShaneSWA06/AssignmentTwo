package com.example.demo.repository;

import com.example.demo.model.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Integer> {
    List<Caregiver> findByIsAvailableTrue();
    Optional<Caregiver> findByUserId(Integer userId);
}
