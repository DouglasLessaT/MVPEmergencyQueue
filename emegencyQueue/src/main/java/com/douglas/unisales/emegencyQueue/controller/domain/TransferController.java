package com.douglas.unisales.emegencyQueue.controller.domain;

import com.douglas.unisales.emegencyQueue.model.domain.Transfer;
import com.douglas.unisales.emegencyQueue.services.domain.TransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<Transfer> create(@RequestBody Transfer transfer) {
        return ResponseEntity.ok(transferService.save(transfer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transfer> update(@PathVariable UUID id, @RequestBody Transfer transfer) {
        transfer.setId(id);
        return ResponseEntity.ok(transferService.update(transfer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transfer> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(transferService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> getAll() {
        return ResponseEntity.ok(transferService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        transferService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 