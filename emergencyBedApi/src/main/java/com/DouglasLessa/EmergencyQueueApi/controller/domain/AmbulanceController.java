package com.DouglasLessa.EmergencyQueueApi.controller.domain;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Ambulance;
import com.DouglasLessa.EmergencyQueueApi.services.domain.AmbulanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ambulances")
public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    @PostMapping
    public ResponseEntity<Ambulance> create(@RequestBody Ambulance ambulance) {
        return ResponseEntity.ok(ambulanceService.save(ambulance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ambulance> update(@PathVariable UUID id, @RequestBody Ambulance ambulance) {
        return ResponseEntity.ok(ambulanceService.update(id, ambulance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ambulance> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ambulanceService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Ambulance>> getAll() {
        return ResponseEntity.ok(ambulanceService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ambulanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 