package com.DouglasLessa.emergencyBedApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.emergencyBedApi.model.domain.Bedroom;

@Repository
public interface BedroomRepository extends JpaRepository<Bedroom, UUID> {
    Bedroom findByCodigoAndHospitalId(String codigo, UUID hospitalId);

    Bedroom findByCodigoAndHospitalIdAndIdNot(String codigo, UUID hospitalId, UUID id);

    Bedroom findByCodigoAndHospitalIdAndStatusId(String codigo, UUID hospitalId, UUID statusId);

    Bedroom findByCodigoAndHospitalIdAndStatusIdAndIdNot(String codigo, UUID hospitalId, UUID statusId, UUID id);
}