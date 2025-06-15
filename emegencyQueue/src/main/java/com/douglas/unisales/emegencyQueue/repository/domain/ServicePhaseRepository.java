package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.ServicePhase;

@Repository
public interface ServicePhaseRepository extends JpaRepository<ServicePhase, UUID> {
    ServicePhase findByDescription(String description);

    ServicePhase findByDescriptionAndIdNot(String description, UUID id);

    ServicePhase findByName(String name);

    ServicePhase findByNameAndIdNot(String name, UUID id);
}