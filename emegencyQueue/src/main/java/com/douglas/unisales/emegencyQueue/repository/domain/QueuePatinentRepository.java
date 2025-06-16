package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.douglas.unisales.emegencyQueue.model.domain.QueuePatinent;

public interface QueuePatinentRepository extends JpaRepository<QueuePatinent, UUID> {



}
