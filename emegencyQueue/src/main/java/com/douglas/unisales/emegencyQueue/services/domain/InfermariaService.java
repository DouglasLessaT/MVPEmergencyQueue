package com.douglas.unisales.emegencyQueue.services.domain;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.unisales.emegencyQueue.model.domain.Infermaria;
import com.douglas.unisales.emegencyQueue.model.domain.Bed;
import com.douglas.unisales.emegencyQueue.model.domain.Seat;
import com.douglas.unisales.emegencyQueue.repository.domain.InfermariaRepository;
import com.douglas.unisales.emegencyQueue.repository.domain.BedRepository;
import com.douglas.unisales.emegencyQueue.repository.domain.SeatRepository;

@Service
public class InfermariaService {

    @Autowired
    private InfermariaRepository infermariaRepository;
    
    @Autowired
    private BedRepository bedRepository;
    
    @Autowired
    private SeatRepository seatRepository;

    public Infermaria save(Infermaria infermaria) {
        return infermariaRepository.save(infermaria);
    }

    public Infermaria findById(UUID id) {
        return infermariaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Infermaria not found with id: " + id));
    }

    public List<Infermaria> findAll() {
        return infermariaRepository.findAll();
    }
    
    public List<Infermaria> findByHospitalId(UUID hospitalId) {
        return infermariaRepository.findByHospitalId(hospitalId);
    }
    
    public List<Infermaria> findByStatusId(UUID statusId) {
        return infermariaRepository.findByStatusId(statusId);
    }

    public void delete(UUID id) {
        Infermaria infermaria = findById(id);
        infermariaRepository.delete(infermaria);
    }
    
    // Métodos para gerenciar leitos ocupados
    public List<Bed> getOccupiedBeds(UUID infermariaId) {
        return bedRepository.findByInfermariaIdAndOccupied(infermariaId, true);
    }
    
    public List<Bed> getAvailableBeds(UUID infermariaId) {
        return bedRepository.findByInfermariaIdAndOccupied(infermariaId, false);
    }
    
    // Métodos para gerenciar assentos ocupados
    public List<Seat> getOccupiedSeats(UUID infermariaId) {
        return seatRepository.findByInfermariaIdAndOccupied(infermariaId, true);
    }
    
    public List<Seat> getAvailableSeats(UUID infermariaId) {
        return seatRepository.findByInfermariaIdAndOccupied(infermariaId, false);
    }
    
    // Método para obter estatísticas da enfermaria
    public InfermariaStats getInfermariaStats(UUID infermariaId) {
        List<Bed> allBeds = bedRepository.findByInfermariaId(infermariaId);
        List<Seat> allSeats = seatRepository.findByInfermariaId(infermariaId);
        
        long occupiedBeds = allBeds.stream().filter(Bed::isOccupied).count();
        long occupiedSeats = allSeats.stream().filter(Seat::isOccupied).count();
        
        return new InfermariaStats(
            allBeds.size(),
            (int) occupiedBeds,
            allSeats.size(),
            (int) occupiedSeats
        );
    }
    
    // Classe interna para estatísticas
    public static class InfermariaStats {
        private int totalBeds;
        private int occupiedBeds;
        private int totalSeats;
        private int occupiedSeats;
        
        public InfermariaStats(int totalBeds, int occupiedBeds, int totalSeats, int occupiedSeats) {
            this.totalBeds = totalBeds;
            this.occupiedBeds = occupiedBeds;
            this.totalSeats = totalSeats;
            this.occupiedSeats = occupiedSeats;
        }
        
        // Getters
        public int getTotalBeds() { return totalBeds; }
        public int getOccupiedBeds() { return occupiedBeds; }
        public int getAvailableBeds() { return totalBeds - occupiedBeds; }
        public int getTotalSeats() { return totalSeats; }
        public int getOccupiedSeats() { return occupiedSeats; }
        public int getAvailableSeats() { return totalSeats - occupiedSeats; }
    }
} 