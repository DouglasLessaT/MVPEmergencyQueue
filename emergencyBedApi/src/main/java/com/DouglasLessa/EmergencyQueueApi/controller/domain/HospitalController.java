package com.DouglasLessa.EmergencyQueueApi.controller.domain;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Hospital;
import com.DouglasLessa.EmergencyQueueApi.services.domain.HospitalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<Hospital> create(@RequestBody Hospital hospital) {
        return ResponseEntity.ok(hospitalService.save(hospital));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> update(@PathVariable UUID id, @RequestBody Hospital hospital) {
        hospital.setId(id);
        return ResponseEntity.ok(hospitalService.update(hospital));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(hospitalService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Hospital>> getAll() {
        return ResponseEntity.ok(hospitalService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        hospitalService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 