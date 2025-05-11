package com.DouglasLessa.emergencyBedApi.controller.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DouglasLessa.emergencyBedApi.model.domain.ServicePhase;
import com.DouglasLessa.emergencyBedApi.services.domain.ServicePhaseService;

@RestController
@RequestMapping
public class ServicePhaseController {
    
    @Autowired
    private ServicePhaseService servicePhaseService;

    @PostMapping
    public ResponseEntity <ServicePhase> createServicePhase(@RequestBody ServicePhase servicePhase){
        return ResponseEntity.ok(servicePhaseService.save(servicePhase));
    }

        @GetMapping
    public ResponseEntity<List<ServicePhase>> getAllServicePhase() {
        return ResponseEntity.ok(servicePhaseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePhase> getServicePhaseById(@PathVariable UUID id) {
        return ResponseEntity.ok(servicePhaseService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteservicePhase(@PathVariable UUID id) {
        servicePhaseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
