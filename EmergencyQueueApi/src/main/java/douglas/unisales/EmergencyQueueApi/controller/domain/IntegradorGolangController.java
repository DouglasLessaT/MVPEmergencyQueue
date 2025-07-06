package douglas.unisales.EmergencyQueueApi.controller.domain;

import douglas.unisales.EmergencyQueueApi.services.domain.IntegradorGolangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/integrador-golang")
@CrossOrigin(origins = "*")
public class IntegradorGolangController {

    @Autowired
    private IntegradorGolangService integradorGolangService;

    @GetMapping("/{hospitalId}/scripts")
    public ResponseEntity<List<Map<String, Object>>> getScripts(@PathVariable UUID hospitalId) {
        return ResponseEntity.ok(integradorGolangService.getScripts(hospitalId));
    }

    @GetMapping("/{hospitalId}/status")
    public ResponseEntity<Map<String, Object>> getStatus(@PathVariable UUID hospitalId) {
        return ResponseEntity.ok(integradorGolangService.getStatus(hospitalId));
    }

    @PostMapping("/{hospitalId}/execute/{scriptName}")
    public ResponseEntity<Map<String, Object>> executeScript(
        @PathVariable UUID hospitalId, 
        @PathVariable String scriptName
    ) {
        return ResponseEntity.ok(integradorGolangService.executeScript(hospitalId, scriptName));
    }
} 