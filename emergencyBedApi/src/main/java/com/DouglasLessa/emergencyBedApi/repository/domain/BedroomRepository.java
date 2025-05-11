package com.DouglasLessa.emergencyBedApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.emergencyBedApi.model.domain.Bedroom;

@Repository
public interface BedroomRepository extends JpaRepository<Bedroom, UUID> {
    Bedroom findByCodeAndHospitalId(String code, UUID hospitalId);

    Bedroom findByCodeAndHospitalIdAndIdNot(String code, UUID hospitalId, UUID id);

    Bedroom findByCodeAndHospitalIdAndStatusId(String code, UUID hospitalId, UUID statusId);

    Bedroom findByCodeAndHospitalIdAndStatusIdAndIdNot(String code, UUID hospitalId, UUID statusId, UUID id);
}