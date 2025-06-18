package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.Bed;

@Repository
public interface BedRepository extends JpaRepository<Bed, UUID> {
    Bed findByCode(String code);

    Bed findByCodeAndBedroomId(String code, UUID bedroomId);

    Bed findByCodeAndBedroomIdAndIdNot(String code, UUID bedroomId, UUID id);

    Bed findByCodeAndBedroomIdAndStatusId(String code, UUID bedroomId, UUID statusId);

    Bed findByCodeAndBedroomIdAndStatusIdAndIdNot(String code, UUID bedroomId, UUID statusId, UUID id);

    List<Bed> findByInfermariaId(UUID infermariaId);

    List<Bed> findByInfermariaIdAndOccupied(UUID infermariaId, boolean occupied);

    List<Bed> findByBedroomId(UUID bedroomId);

    List<Bed> findByBedroomIdAndOccupied(UUID bedroomId, boolean occupied);

    List<Bed> findByStatusId(UUID statusId);

    List<Bed> findByOccupiedTrue();

    List<Bed> findByOccupiedFalse();
}