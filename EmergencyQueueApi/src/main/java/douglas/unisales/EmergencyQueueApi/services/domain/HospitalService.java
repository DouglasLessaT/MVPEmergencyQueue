package douglas.unisales.EmergencyQueueApi.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;
import douglas.unisales.EmergencyQueueApi.repository.domain.HospitalRepository;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital save(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public Hospital findById(UUID id) {
        return hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
    }

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }

    public void delete(UUID id) {
        Hospital hospital = findById(id);
        hospitalRepository.delete(hospital);
    }

    public Hospital update(Hospital hospital) {
        Hospital existingHospital = findById(hospital.getId());
        
        existingHospital.setName(hospital.getName());
        existingHospital.setPhone(hospital.getPhone());
        existingHospital.setEscalation(hospital.getEscalation());
        existingHospital.setAddress(hospital.getAddress());
        existingHospital.setTotalCapacity(hospital.getTotalCapacity());
        existingHospital.setActiveCapacity(hospital.getActiveCapacity());
        existingHospital.setNumberOfBeds(hospital.getNumberOfBeds());
        existingHospital.setNumberOfRooms(hospital.getNumberOfRooms());
        existingHospital.setNumberOfBuildings(hospital.getNumberOfBuildings());
        existingHospital.setNumberOfFloors(hospital.getNumberOfFloors());
        existingHospital.setRooms(hospital.getRooms());
        existingHospital.setAmbulances(hospital.getAmbulances());
        existingHospital.setUsers(hospital.getUsers());

        return hospitalRepository.save(existingHospital);
    }
}