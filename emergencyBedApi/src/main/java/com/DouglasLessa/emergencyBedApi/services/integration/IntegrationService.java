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
        System.out.println("Processing JSON: " + json);
    }

    private void processXml(String xml) {
        System.out.println("Processing XML: " + xml);
    }
}