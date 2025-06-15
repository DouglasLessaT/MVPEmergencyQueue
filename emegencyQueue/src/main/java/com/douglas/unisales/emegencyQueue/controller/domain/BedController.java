package com.douglas.unisales.emegencyQueue.controller.domain;

import com.douglas.unisales.emegencyQueue.model.domain.Bed;
import com.douglas.unisales.emegencyQueue.services.domain.BedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @PostMapping
    public ResponseEntity<Bed> create(@RequestBody Bed bed) {
        return ResponseEntity.ok(bedService.save(bed));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bed> update(@PathVariable UUID id, @RequestBody Bed bed) {
        bed.setId(id);
        return ResponseEntity.ok(bedService.update(bed));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bed> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bedService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Bed>> getAll() {
        return ResponseEntity.ok(bedService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bedService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 