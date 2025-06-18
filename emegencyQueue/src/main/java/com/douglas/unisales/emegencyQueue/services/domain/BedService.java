package com.douglas.unisales.emegencyQueue.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.unisales.emegencyQueue.model.domain.Bed;
import com.douglas.unisales.emegencyQueue.model.domain.Patient;
import com.douglas.unisales.emegencyQueue.repository.domain.BedRepository;
import com.douglas.unisales.emegencyQueue.services.domain.PatientService;

@Service
public class BedService {

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private PatientService patientService;

    public Bed save(Bed bed) {
        return bedRepository.save(bed);
    }

    public Bed findById(UUID id) {
        return bedRepository.findById(id).orElseThrow(() -> new RuntimeException("Bed not found with id: " + id));
    }

    public List<Bed> findAll() {
        return bedRepository.findAll();
    }

    public void delete(UUID id) {
        Bed bed = findById(id);
        bedRepository.delete(bed);
    }

    public Bed update(Bed bed) {
        Bed existingBed = findById(bed.getId());
        
        existingBed.setCode(bed.getCode());
        existingBed.setFloor(bed.getFloor());
        existingBed.setType(bed.getType());
        existingBed.setStatus(bed.getStatus());
        existingBed.setPatient(bed.getPatient());
        existingBed.setBedroom(bed.getBedroom());
        existingBed.setInfermaria(bed.getInfermaria());
        existingBed.setEntryPacient(bed.getEntryPacient());
        existingBed.setExitPacient(bed.getExitPacient());
        existingBed.setOccupied(bed.isOccupied());

        return bedRepository.save(existingBed);
    }
    
    // Métodos para gerenciar ocupação
    public Bed occupyBed(UUID bedId, UUID patientId) {
        Bed bed = findById(bedId);
        bed.setOccupied(true);
        bed.setPatient(patientService.findById(patientId));
        return save(bed);
    }
    
    public Bed freeBed(UUID bedId) {
        Bed bed = findById(bedId);
        bed.setOccupied(false);
        bed.setPatient(null);
        return save(bed);
    }
    
    // Métodos de busca
    public List<Bed> findByInfermariaId(UUID infermariaId) {
        return bedRepository.findByInfermariaId(infermariaId);
    }
    
    public List<Bed> findByBedroomId(UUID bedroomId) {
        return bedRepository.findByBedroomId(bedroomId);
    }
    
    public List<Bed> findByStatusId(UUID statusId) {
        return bedRepository.findByStatusId(statusId);
    }
    
    public List<Bed> findOccupiedBeds() {
        return bedRepository.findByOccupiedTrue();
    }
    
    public List<Bed> findAvailableBeds() {
        return bedRepository.findByOccupiedFalse();
    }
    
    public List<Bed> findOccupiedBedsByInfermaria(UUID infermariaId) {
        return bedRepository.findByInfermariaIdAndOccupied(infermariaId, true);
    }
    
    public List<Bed> findAvailableBedsByInfermaria(UUID infermariaId) {
        return bedRepository.findByInfermariaIdAndOccupied(infermariaId, false);
    }
    
    public List<Bed> findOccupiedBedsByBedroom(UUID bedroomId) {
        return bedRepository.findByBedroomIdAndOccupied(bedroomId, true);
    }
    
    public List<Bed> findAvailableBedsByBedroom(UUID bedroomId) {
        return bedRepository.findByBedroomIdAndOccupied(bedroomId, false);
    }
    
    // Método para obter estatísticas
    public BedStats getBedStats(UUID infermariaId) {
        List<Bed> allBeds = findByInfermariaId(infermariaId);
        long occupiedCount = allBeds.stream().filter(Bed::isOccupied).count();
        
        return new BedStats(allBeds.size(), (int) occupiedCount);
    }
    
    public static class BedStats {
        private int totalBeds;
        private int occupiedBeds;
        
        public BedStats(int totalBeds, int occupiedBeds) {
            this.totalBeds = totalBeds;
            this.occupiedBeds = occupiedBeds;
        }
        
        public int getTotalBeds() { return totalBeds; }
        public int getOccupiedBeds() { return occupiedBeds; }
        public int getAvailableBeds() { return totalBeds - occupiedBeds; }
        public double getOccupancyRate() { 
            return totalBeds > 0 ? (double) occupiedBeds / totalBeds * 100 : 0; 
        }
    }
}