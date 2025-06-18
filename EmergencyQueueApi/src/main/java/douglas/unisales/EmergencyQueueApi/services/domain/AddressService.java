package douglas.unisales.EmergencyQueueApi.services.domain;

import org.springframework.stereotype.Service;
import douglas.unisales.EmergencyQueueApi.model.domain.Address;

import java.util.List;
import java.util.UUID;
import douglas.unisales.EmergencyQueueApi.repository.domain.AddressRepository;


import org.springframework.beans.factory.annotation.Autowired;
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address) {
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

    public Address update(UUID id, Address address) {
        if (address.getId() == null) {
            throw new RuntimeException("Address ID cannot be null");
        }
        if (!address.getId().equals(id)) {
            throw new RuntimeException("Address ID in the request body does not match the ID in the URL");
        }
        if (address.getId() == null) {
            throw new RuntimeException("Address ID cannot be null");
        }
        Address existingAddress = findById(id); 
        existingAddress.setStreet(address.getStreet());
        existingAddress.setNumber(address.getNumber());
        existingAddress.setNeighborhood(address.getNeighborhood());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setZipCode(address.getZipCode());
        existingAddress.setState(address.getState());
        existingAddress.setCity(address.getCity());
        existingAddress.setLatitude(address.getLatitude());
        existingAddress.setLongitude(address.getLongitude());
        if (existingAddress.getId() == null) {
            throw new RuntimeException("Address ID cannot be null");
        }
        if (!existingAddress.getId().equals(id)) {
            throw new RuntimeException("Address ID in the request body does not match the ID in the URL");
        }
        if (existingAddress.getId() == null) {
            throw new RuntimeException("Address ID cannot be null");
        }
        if (existingAddress.getStreet() == null || existingAddress.getStreet().isEmpty()) {
            throw new RuntimeException("Street cannot be null or empty");
        }
        if (existingAddress.getNumber() == null || existingAddress.getNumber().isEmpty()) {
            throw new RuntimeException("Number cannot be null or empty");
        }
        if (existingAddress.getNeighborhood() == null || existingAddress.getNeighborhood().isEmpty()) {
            throw new RuntimeException("Neighborhood cannot be null or empty");
        }
        if (existingAddress.getCountry() == null || existingAddress.getCountry().isEmpty()) {
            throw new RuntimeException("Country cannot be null or empty");
        }
        if (existingAddress.getZipCode() == null || existingAddress.getZipCode().isEmpty()) {
            throw new RuntimeException("Zip Code cannot be null or empty");
        }
        if (existingAddress.getState() == null || existingAddress.getState().isEmpty()) {
            throw new RuntimeException("State cannot be null or empty");
        }
        if (existingAddress.getCity() == null || existingAddress.getCity().isEmpty()) {
            throw new RuntimeException("City cannot be null or empty");
        }
        if (existingAddress.getLatitude() == null) {
            throw new RuntimeException("Latitude cannot be null");
        }
        if (existingAddress.getLongitude() == null) {
            throw new RuntimeException("Longitude cannot be null");
        }

        return addressRepository.save(existingAddress);
    }
}
