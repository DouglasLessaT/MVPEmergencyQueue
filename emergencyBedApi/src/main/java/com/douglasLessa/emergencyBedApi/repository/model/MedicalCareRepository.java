package com.douglasLessa.emergencyBedApi.repository.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasLessa.emergencyBedApi.model.MedicalCare;

@Repository
public interface MedicalCareRepository extends JpaRepository<MedicalCare, UUID> {
    MedicalCare findByDescription(String description);

    MedicalCare findByDescriptionAndIdNot(String description, UUID id);

    MedicalCare findByName(String name);

    MedicalCare findByNameAndIdNot(String name, UUID id);

}