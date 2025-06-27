package douglas.unisales.EmergencyQueueApi.controller.domain;

import douglas.unisales.EmergencyQueueApi.services.domain.MapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "*")
public class MapController {

    @Autowired
    private MapService mapService;

    /**
     * Endpoint de teste simples
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = Map.of(
            "message", "MapController is working!",
            "status", "OK"
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Retorna todos os hospitais com dados para exibição no mapa
     * Inclui informações de localização, status, filas e capacidade
     */
    @GetMapping("/hospitals")
    public ResponseEntity<List<Map<String, Object>>> getHospitalsWithMapData() {
        try {
            List<Map<String, Object>> hospitals = mapService.getHospitalsWithMapData();
            return ResponseEntity.ok(hospitals);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retorna detalhes completos de um hospital específico
     * Inclui informações detalhadas sobre filas, leitos e cuidados médicos
     */
    @GetMapping("/hospitals/{hospitalId}")
    public ResponseEntity<Map<String, Object>> getHospitalDetails(@PathVariable String hospitalId) {
        try {
            Map<String, Object> hospitalDetails = mapService.getHospitalDetailsById(hospitalId);
            return ResponseEntity.ok(hospitalDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retorna hospitais filtrados por status
     * Útil para filtrar hospitais por nível de ocupação ou emergência
     */
    @GetMapping("/hospitals/status/{statusCode}")
    public ResponseEntity<List<Map<String, Object>>> getHospitalsByStatus(@PathVariable String statusCode) {
        try {
            List<Map<String, Object>> hospitals = mapService.getHospitalsByStatus(statusCode);
            return ResponseEntity.ok(hospitals);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint de saúde para verificar se o serviço está funcionando
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = Map.of(
            "status", "UP",
            "service", "MapService",
            "message", "Map service is running"
        );
        return ResponseEntity.ok(response);
    }
} 