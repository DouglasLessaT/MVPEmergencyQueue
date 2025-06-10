package com.DouglasLessa.EmergencyQueueApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Hospital findByName(String name);

    Hospital findByNameAndIdNot(String name, UUID id);
}