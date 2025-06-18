package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.Infermaria;

@Repository
public interface InfermariaRepository extends JpaRepository<Infermaria, UUID> {
    
    List<Infermaria> findByHospitalId(UUID hospitalId);
    
    List<Infermaria> findByStatusId(UUID statusId);
    
    List<Infermaria> findByStatusEntityType(String entityType);
    
    Infermaria findByCodeAndHospitalId(String code, UUID hospitalId);
    
    Infermaria findByCodeAndHospitalIdAndIdNot(String code, UUID hospitalId, UUID id);
    
    List<Infermaria> findByNameContainingIgnoreCase(String name);
} 