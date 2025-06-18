package com.douglas.unisales.emegencyQueue.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.unisales.emegencyQueue.model.domain.Bedroom;
import com.douglas.unisales.emegencyQueue.repository.domain.BedroomRepository;
import com.douglas.unisales.emegencyQueue.model.domain.Bed;
import com.douglas.unisales.emegencyQueue.services.domain.BedService;

@Service
public class BedroomService {

    @Autowired
    private BedroomRepository bedroomRepository;

    @Autowired
    private BedService bedService;

    public Bedroom save(Bedroom bedroom) {
        return bedroomRepository.save(bedroom);
    }

    public Bedroom findById(UUID id) {
        return bedroomRepository.findById(id).orElseThrow(() -> new RuntimeException("Bedroom not found with id: " + id));
    }

    public List<Bedroom> findAll() {
        return bedroomRepository.findAll();
    }

    public void delete(UUID id) {
        Bedroom bedroom = findById(id);
        bedroomRepository.delete(bedroom);
    }

    public void updateBedroom(Bedroom bedroom) {
        Bedroom existingBedroom = findById(bedroom.getId());
        existingBedroom.setCode(bedroom.getCode());
        existingBedroom.setType(bedroom.getType());
        existingBedroom.setFloor(bedroom.getFloor());
        existingBedroom.setBeds(bedroom.getBeds());
        existingBedroom.setHospital(bedroom.getHospital());
        existingBedroom.setStatus(bedroom.getStatus());

        bedroomRepository.save(existingBedroom);
    }
    
    // Métodos de busca
    public List<Bedroom> findByHospitalId(UUID hospitalId) {
        return bedroomRepository.findByHospitalId(hospitalId);
    }
    
    public List<Bedroom> findByStatusId(UUID statusId) {
        return bedroomRepository.findByStatusId(statusId);
    }
    
    public List<Bedroom> findByStatusEntityType(String entityType) {
        return bedroomRepository.findByStatusEntityType(entityType);
    }
    
    public Bedroom findByCodeAndHospitalId(String code, UUID hospitalId) {
        return bedroomRepository.findByCodeAndHospitalId(code, hospitalId);
    }
    
    // Método para obter estatísticas do quarto
    public BedroomStats getBedroomStats(UUID bedroomId) {
        List<Bed> beds = bedService.findByBedroomId(bedroomId);
        long occupiedCount = beds.stream().filter(Bed::isOccupied).count();
        
        return new BedroomStats(beds.size(), (int) occupiedCount);
    }
    
    public static class BedroomStats {
        private int totalBeds;
        private int occupiedBeds;
        
        public BedroomStats(int totalBeds, int occupiedBeds) {
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