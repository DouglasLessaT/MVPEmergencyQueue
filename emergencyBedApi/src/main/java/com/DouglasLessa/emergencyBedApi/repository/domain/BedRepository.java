package com.DouglasLessa.emergencyBedApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.emergencyBedApi.model.domain.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, UUID> {
    Bed findByNumberAndHospitalId(String number, UUID hospitalId);

    Bed findByNumberAndHospitalIdAndIdNot(String number, UUID hospitalId, UUID id);

    Bed findByNumberAndHospitalIdAndStatusAndIdNot(String number, UUID hospitalId, String status, UUID id);

    Bed findByNumberAndHospitalIdAndStatus(String number, UUID hospitalId, String status);
}