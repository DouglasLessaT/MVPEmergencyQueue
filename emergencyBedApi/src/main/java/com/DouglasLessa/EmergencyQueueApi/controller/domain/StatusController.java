package com.DouglasLessa.EmergencyQueueApi.controller.domain;

import com.DouglasLessa.EmergencyQueueApi.model.domain.Status;
import com.DouglasLessa.EmergencyQueueApi.services.domain.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        return ResponseEntity.ok(statusService.save(status));
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatus() {
        return ResponseEntity.ok(statusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable UUID id) {
        return ResponseEntity.ok(statusService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable UUID id) {
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
