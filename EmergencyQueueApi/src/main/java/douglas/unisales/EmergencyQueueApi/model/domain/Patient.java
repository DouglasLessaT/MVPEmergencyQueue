package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String gender;
    private Integer age;
    private String insurance;
    private String healthPlan;
    private String cpf;
    private String rg;
    private String motherName;
    private String fatherName;
    private String medicalRecordPDF;

    @ManyToOne
    @JoinColumn(name = "queue_id")
    private QueuePatinent queuePatinent; // Nome corrigido para "queuePatinent"

    private boolean attended = false;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Patient() {
    }

    public Patient(UUID id, String name, String gender, Integer age, String insurance, String healthPlan,
            String cpf, String rg, String motherName, String fatherName, String medicalRecordPDF,
            QueuePatinent queuePatinent, boolean attended, Status status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.insurance = insurance;
        this.healthPlan = healthPlan;
        this.cpf = cpf;
        this.rg = rg;
        this.motherName = motherName;
        this.fatherName = fatherName;
        this.medicalRecordPDF = medicalRecordPDF;
        this.queuePatinent = queuePatinent;
        this.attended = attended;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getHealthPlan() {
        return healthPlan;
    }

    public void setHealthPlan(String healthPlan) {
        this.healthPlan = healthPlan;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMedicalRecordPDF() {
        return medicalRecordPDF;
    }

    public void setMedicalRecordPDF(String medicalRecordPDF) {
        this.medicalRecordPDF = medicalRecordPDF;
    }

    public QueuePatinent getQueuePatinent() {
        return queuePatinent;
    }

    public void setQueuePatinent(QueuePatinent queuePatinent) {
        this.queuePatinent = queuePatinent;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    
}