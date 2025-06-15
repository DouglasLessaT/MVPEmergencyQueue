package com.douglas.unisales.emegencyQueue.controller.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.douglas.unisales.emegencyQueue.model.domain.QueuePatinentMedicalCare;
import com.douglas.unisales.emegencyQueue.services.domain.QueuePatinentMedicalCareService;

@RestController
@RequestMapping("/api/queue-patinent-medical-care")
public class QueuePatinentMedicalCareController {

    @Autowired
    private QueuePatinentMedicalCareService service;

    @GetMapping
    public List<QueuePatinentMedicalCare> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public QueuePatinentMedicalCare getById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public QueuePatinentMedicalCare create(@RequestBody QueuePatinentMedicalCare queue) {
        return service.save(queue);
    }

    @PutMapping("/{id}")
    public QueuePatinentMedicalCare update(@PathVariable UUID id, @RequestBody QueuePatinentMedicalCare queue) {
        queue.setId(id);
        return service.save(queue);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}