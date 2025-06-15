package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.MedicalCare;

@Repository
public interface MedicalCareRepository extends JpaRepository<MedicalCare, UUID> {


}