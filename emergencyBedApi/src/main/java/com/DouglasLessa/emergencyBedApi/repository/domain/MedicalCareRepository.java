package com.DouglasLessa.emergencyBedApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.emergencyBedApi.model.domain.MedicalCare;

@Repository
public interface MedicalCareRepository extends JpaRepository<MedicalCare, UUID> {


}