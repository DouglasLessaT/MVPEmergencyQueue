package com.douglas.unisales.emegencyQueue.controller.domain;

import com.douglas.unisales.emegencyQueue.model.domain.Bedroom;
import com.douglas.unisales.emegencyQueue.services.domain.BedroomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/bedrooms")
public class BedroomController {

    @Autowired
    private BedroomService bedroomService;

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Bedroom> create(@RequestBody Bedroom bedroom) {
        return ResponseEntity.ok(bedroomService.save(bedroom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bedroom> update(@PathVariable UUID id, @RequestBody Bedroom bedroom) {
        bedroom.setId(id);
        bedroomService.updateBedroom(bedroom);
        return ResponseEntity.ok(bedroom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bedroom> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bedroomService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Bedroom>> getAll() {
        return ResponseEntity.ok(bedroomService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bedroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 