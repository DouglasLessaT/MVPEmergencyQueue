package com.douglas.unisales.emegencyQueue.controller.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.douglas.unisales.emegencyQueue.model.domain.Infermaria;
import com.douglas.unisales.emegencyQueue.services.domain.InfermariaService;
import com.douglas.unisales.emegencyQueue.services.domain.InfermariaService.InfermariaStats;

@RestController
@RequestMapping("/api/infermaria")
public class InfermariaController {

    @Autowired
    private InfermariaService infermariaService;

    @PostMapping
    public ResponseEntity<Infermaria> create(@RequestBody Infermaria infermaria) {
        return ResponseEntity.ok(infermariaService.save(infermaria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Infermaria> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(infermariaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Infermaria>> findAll() {
        return ResponseEntity.ok(infermariaService.findAll());
    }
    
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<Infermaria>> findByHospitalId(@PathVariable UUID hospitalId) {
        return ResponseEntity.ok(infermariaService.findByHospitalId(hospitalId));
    }
    
    @GetMapping("/{id}/stats")
    public ResponseEntity<InfermariaStats> getStats(@PathVariable UUID id) {
        return ResponseEntity.ok(infermariaService.getInfermariaStats(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Infermaria> update(@PathVariable UUID id, @RequestBody Infermaria infermaria) {
        infermaria.setId(id);
        return ResponseEntity.ok(infermariaService.save(infermaria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        infermariaService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 