package com.DouglasLessa.emergencyBedApi.services.integration;

import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

    public void processData(String payload, String format) {
        if ("json".equalsIgnoreCase(format)) {
            processJson(payload);
        } else if ("xml".equalsIgnoreCase(format)) {
            processXml(payload);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    private void processJson(String json) {
        // Parse and process JSON data
        System.out.println("Processing JSON: " + json);
        // Save data to the database
    }

    private void processXml(String xml) {
        // Parse and process XML data
        System.out.println("Processing XML: " + xml);
        // Save data to the database
    }
}