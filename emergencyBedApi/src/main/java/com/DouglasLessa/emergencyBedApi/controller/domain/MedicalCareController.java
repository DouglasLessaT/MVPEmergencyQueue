package com.DouglasLessa.emergencyBedApi.controller.domain;

import com.DouglasLessa.emergencyBedApi.model.domain.MedicalCare;
import com.DouglasLessa.emergencyBedApi.services.domain.MedicalCareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medical-care")
public class MedicalCareController {

    @Autowired
    private MedicalCareService medicalCareService;

    @PostMapping
    public ResponseEntity<MedicalCare> create(@RequestBody MedicalCare medicalCare) {
        return ResponseEntity.ok(medicalCareService.save(medicalCare));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalCare> update(@PathVariable UUID id, @RequestBody MedicalCare medicalCare) {
        medicalCare.setId(id);
        return ResponseEntity.ok(medicalCareService.update(medicalCare));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalCare> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicalCareService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MedicalCare>> getAll() {
        return ResponseEntity.ok(medicalCareService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        medicalCareService.delete(id);
        return ResponseEntity.noContent().build();
    }
}