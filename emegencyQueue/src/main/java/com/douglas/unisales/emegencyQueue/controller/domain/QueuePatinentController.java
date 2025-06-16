package com.douglas.unisales.emegencyQueue.controller.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.douglas.unisales.emegencyQueue.model.domain.QueuePatinent;
import com.douglas.unisales.emegencyQueue.services.domain.QueuePatinentService;

@RestController
@RequestMapping("/api/queue-patinent-medical-care")
public class QueuePatinentController {

    @Autowired
    private QueuePatinentService service;

    @GetMapping
    public List<QueuePatinent> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public QueuePatinent getById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public QueuePatinent create(@RequestBody QueuePatinent queue) {
        return service.save(queue);
    }

    @PutMapping("/{id}")
    public QueuePatinent update(@PathVariable UUID id, @RequestBody QueuePatinent queue) {
        queue.setId(id);
        return service.save(queue);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}