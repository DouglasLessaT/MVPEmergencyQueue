package douglas.unisales.EmergencyQueueApi.model.domain;

import java.util.List;
import java.util.UUID;

import douglas.unisales.EmergencyQueueApi.model.security.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String phone;
    private String escalation;

    @ManyToOne
    @JoinColumn(name = "address_hospital_id")
    private Address address;

    private Integer totalCapacity;
    private Integer activeCapacity;
    private Integer numberOfBeds;
    private Integer numberOfRooms;
    private Integer numberOfBuildings;
    private Integer numberOfFloors;
    private String goApiPath;
    private String goApiUrl;
    private String goApiPort;
    private String erpSystem;
    private boolean active;

    @OneToMany(mappedBy = "hospital")
    private List<Bedroom> rooms;

    @OneToMany(mappedBy = "hospital")
    private List<Ambulance> ambulances;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueuePatinent> queuePatinents;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public Hospital() {
    }

    public Hospital(UUID id, String name, String phone, String escalation, Address address,
                    Integer totalCapacity, Integer activeCapacity, Integer numberOfBeds,
                    Integer numberOfRooms, Integer numberOfBuildings, Integer numberOfFloors,
                    String goApiPath, String goApiUrl, String goApiPort,
                    List<Bedroom> rooms, List<Ambulance> ambulances, List<User> users,
                    List<QueuePatinent> queuePatinents, Status status, String erpSystem, boolean active) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.escalation = escalation;
        this.address = address;
        this.totalCapacity = totalCapacity;
        this.activeCapacity = activeCapacity;
        this.numberOfBeds = numberOfBeds;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBuildings = numberOfBuildings;
        this.numberOfFloors = numberOfFloors;
        this.goApiPath = goApiPath;
        this.goApiUrl = goApiUrl;
        this.goApiPort = goApiPort;
        this.rooms = rooms;
        this.ambulances = ambulances;
        this.users = users;
        this.queuePatinents = queuePatinents;
        this.status = status;
        this.erpSystem = erpSystem;
        this.active = active;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEscalation() {
        return escalation;
    }

    public void setEscalation(String escalation) {
        this.escalation = escalation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getActiveCapacity() {
        return activeCapacity;
    }

    public void setActiveCapacity(Integer activeCapacity) {
        this.activeCapacity = activeCapacity;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public void setNumberOfBuildings(Integer numberOfBuildings) {
        this.numberOfBuildings = numberOfBuildings;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public String getGoApiPath() {
        return goApiPath;
    }

    public void setGoApiPath(String goApiPath) {
        this.goApiPath = goApiPath;
    }

    public String getGoApiUrl() {
        return goApiUrl;
    }

    public void setGoApiUrl(String goApiUrl) {
        this.goApiUrl = goApiUrl;
    }

    public String getGoApiPort() {
        return goApiPort;
    }

    public void setGoApiPort(String goApiPort) {
        this.goApiPort = goApiPort;
    }

    public String getErpSystem() {
        return erpSystem;
    }

    public void setErpSystem(String erpSystem) {
        this.erpSystem = erpSystem;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Bedroom> getRooms() {
        return rooms;
    }

    public void setRooms(List<Bedroom> rooms) {
        this.rooms = rooms;
    }

    public List<Ambulance> getAmbulances() {
        return ambulances;
    }

    public void setAmbulances(List<Ambulance> ambulances) {
        this.ambulances = ambulances;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<QueuePatinent> getQueuePatinents() {
        return queuePatinents;
    }

    public void setQueuePatinents(List<QueuePatinent> queuePatinents) {
        this.queuePatinents = queuePatinents;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    

}