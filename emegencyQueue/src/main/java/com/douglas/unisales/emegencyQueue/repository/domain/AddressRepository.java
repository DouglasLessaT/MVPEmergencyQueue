package com.douglas.unisales.emegencyQueue.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.unisales.emegencyQueue.model.domain.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Address findByStreetAndNeighborhoodAndCityAndStateAndZipCode(String street, String neighborhood, String city, String state, String zipCode);

    Address findByStreetAndNeighborhoodAndCityAndStateAndZipCodeAndIdNot(String street, String neighborhood, String city, String state, String zipCode, UUID id);
}