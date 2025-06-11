package com.DouglasLessa.EmergencyQueueApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, UUID> {
    Bed findByCode(String code);

    Bed findByCodeAndBedroomId(String code, UUID bedroomId);

    Bed findByCodeAndBedroomIdAndIdNot(String code, UUID bedroomId, UUID id);

    Bed findByCodeAndBedroomIdAndStatusId(String code, UUID bedroomId, UUID statusId);

    Bed findByCodeAndBedroomIdAndStatusIdAndIdNot(String code, UUID bedroomId, UUID statusId, UUID id);
}