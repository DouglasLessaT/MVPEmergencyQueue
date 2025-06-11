package com.DouglasLessa.EmergencyQueueApi.controller.domain;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Address;
import com.DouglasLessa.EmergencyQueueApi.services.domain.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.save(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable UUID id, @RequestBody Address address) {
        address.setId(id);
        return ResponseEntity.ok(addressService.update(address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 