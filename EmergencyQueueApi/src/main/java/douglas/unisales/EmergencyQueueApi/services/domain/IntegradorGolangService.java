package douglas.unisales.EmergencyQueueApi.services.domain;

import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;
import douglas.unisales.EmergencyQueueApi.services.domain.HospitalService;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
public class IntegradorGolangService {

    @Autowired
    private HospitalService hospitalService;

    public List<Map<String, Object>> getScripts(UUID hospitalId) {
        Hospital hospital = hospitalService.findById(hospitalId);
        String goApiPath = hospital.getGoApiPath();
        
        List<Map<String, Object>> scripts = new ArrayList<>();
        
        try {
            File goApiDir = new File(goApiPath);
            if (goApiDir.exists() && goApiDir.isDirectory()) {
                File[] files = goApiDir.listFiles((dir, name) -> name.endsWith(".go"));
                if (files != null) {
                    for (File file : files) {
                        Map<String, Object> script = new HashMap<>();
                        script.put("name", file.getName());
                        script.put("path", file.getAbsolutePath());
                        script.put("size", file.length());
                        script.put("lastModified", file.lastModified());
                        scripts.add(script);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return scripts;
    }

    public Map<String, Object> getStatus(UUID hospitalId) {
        Hospital hospital = hospitalService.findById(hospitalId);
        Map<String, Object> status = new HashMap<>();
        
        try {
            String goApiPath = hospital.getGoApiPath();
            String goApiPort = hospital.getGoApiPort();
            
            status.put("hospitalId", hospital.getId().toString());
            status.put("hospitalName", hospital.getName());
            status.put("goApiPath", goApiPath);
            status.put("goApiPort", goApiPort);
            status.put("erpSystem", hospital.getErpSystem());
            status.put("active", hospital.isActive());
            status.put("goApiRunning", checkGoApiStatus(goApiPort));
            
        } catch (Exception e) {
            status.put("error", e.getMessage());
        }
        
        return status;
    }

    private boolean checkGoApiStatus(String port) {
        try {
            URL url = new URL("http://localhost:" + port + "/health");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> executeScript(UUID hospitalId, String scriptName) {
        Hospital hospital = hospitalService.findById(hospitalId);
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Lógica para executar script específico do hospital
            result.put("success", true);
            result.put("message", "Script " + scriptName + " executado para hospital " + hospital.getName());
            result.put("hospitalId", hospitalId.toString());
            result.put("timestamp", new Date());
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
} 