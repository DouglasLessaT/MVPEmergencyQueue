package com.douglasLessa.emergencyBedApi.repository.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasLessa.emergencyBedApi.model.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Hospital findByCnpj(String cnpj);

    Hospital findByCnpjAndIdNot(String cnpj, UUID id);

    Hospital findByName(String name);

    Hospital findByNameAndIdNot(String name, UUID id);
}