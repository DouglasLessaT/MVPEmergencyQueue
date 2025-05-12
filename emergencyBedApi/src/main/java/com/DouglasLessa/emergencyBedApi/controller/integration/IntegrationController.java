package com.DouglasLessa.emergencyBedApi.controller.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DouglasLessa.emergencyBedApi.services.integration.IntegrationService;

@RestController
@RequestMapping("/integration")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @PostMapping("/data")
    public ResponseEntity<?> receiveData(@RequestBody String payload, @RequestParam String format) {
        try {
            integrationService.processData(payload, format);
            return ResponseEntity.ok("Data processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing data: " + e.getMessage());
        }
    }
}