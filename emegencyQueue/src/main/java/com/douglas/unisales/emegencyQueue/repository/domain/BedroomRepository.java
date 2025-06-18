package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.Bedroom;

@Repository
public interface BedroomRepository extends JpaRepository<Bedroom, UUID> {
    Bedroom findByCodeAndHospitalId(String code, UUID hospitalId);

    Bedroom findByCodeAndHospitalIdAndIdNot(String code, UUID hospitalId, UUID id);

    Bedroom findByCodeAndHospitalIdAndStatusId(String code, UUID hospitalId, UUID statusId);

    Bedroom findByCodeAndHospitalIdAndStatusIdAndIdNot(String code, UUID hospitalId, UUID statusId, UUID id);

    List<Bedroom> findByHospitalId(UUID hospitalId);
    List<Bedroom> findByStatusId(UUID statusId);
    List<Bedroom> findByStatusEntityType(String entityType);
}