package com.DouglasLessa.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Address;
import com.DouglasLessa.EmergencyQueueApi.repository.domain.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address) {
        validateAddress(address);
        return addressRepository.save(address);
    }

    public Address findById(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public void delete(UUID id) {
        Address address = findById(id);
        addressRepository.delete(address);
    }

    public Address update(Address address) {
        Address existingAddress = findById(address.getId());
        
        existingAddress.setStreet(address.getStreet());
        existingAddress.setNumber(address.getNumber());
        existingAddress.setNeighborhood(address.getNeighborhood());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setZipCode(address.getZipCode());
        existingAddress.setState(address.getState());
        existingAddress.setCity(address.getCity());

        return addressRepository.save(existingAddress);
    }

    private void validateAddress(Address address) {
        if (address.getStreet() == null || address.getStreet().isEmpty()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (address.getNumber() == null || address.getNumber().isEmpty()) {
            throw new IllegalArgumentException("Number is required");
        }
        if (address.getNeighborhood() == null || address.getNeighborhood().isEmpty()) {
            throw new IllegalArgumentException("Neighborhood is required");
        }
        if (address.getCountry() == null || address.getCountry().isEmpty()) {
            throw new IllegalArgumentException("Country is required");
        }
        if (address.getZipCode() == null || address.getZipCode().isEmpty()) {
            throw new IllegalArgumentException("Zip code is required");
        }
        if (address.getState() == null || address.getState().isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (address.getCity() == null || address.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
    }
} 