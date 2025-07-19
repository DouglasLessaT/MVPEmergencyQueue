package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_phase")
public class ServicePhase {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String code;
    private String color;
    private String description;

    public ServicePhase() {
    }
    public ServicePhase(UUID id, String name, String code, String color, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.color = color;
        this.description = description;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
