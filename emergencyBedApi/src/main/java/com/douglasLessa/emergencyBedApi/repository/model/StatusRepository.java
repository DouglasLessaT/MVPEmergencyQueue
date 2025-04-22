package com.douglasLessa.emergencyBedApi.repository.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglasLessa.emergencyBedApi.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    Status findByDescription(String description);

    Status findByDescriptionAndIdNot(String description, UUID id);

    Status findByName(String name);

    Status findByNameAndIdNot(String name, UUID id);
}