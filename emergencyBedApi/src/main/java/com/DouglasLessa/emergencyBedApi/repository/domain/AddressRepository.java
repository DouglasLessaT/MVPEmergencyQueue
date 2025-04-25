package com.DouglasLessa.emergencyBedApi.repository.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DouglasLessa.emergencyBedApi.model.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Address findByStreetAndNumberAndNeighborhoodAndCityAndStateAndZipCode(String street, String number, String neighborhood, String city, String state, String zipCode);

    Address findByStreetAndNumberAndNeighborhoodAndCityAndStateAndZipCodeAndIdNot(String street, String number, String neighborhood, String city, String state, String zipCode, UUID id);
    Address findByStreetAndNeighborhoodAndCityAndStateAndZipCode(String street, String neighborhood, String city, String state, String zipCode);
}