package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String cnpj;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Company() {
    }

    public Company(UUID id, String name, String cnpj, Status status) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.status = status;
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
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;   
    }

}