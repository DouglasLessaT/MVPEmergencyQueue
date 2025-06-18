package com.douglas.unisales.emegencyQueue.services.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.unisales.emegencyQueue.model.domain.Seat;
import com.douglas.unisales.emegencyQueue.repository.domain.SeatRepository;


@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PatientService patientService;

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    public Seat findById(UUID id) {
        return seatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
    }

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }
    
    public List<Seat> findByInfermariaId(UUID infermariaId) {
        return seatRepository.findByInfermariaId(infermariaId);
    }
    
    public List<Seat> findByStatusId(UUID statusId) {
        return seatRepository.findByStatusId(statusId);
    }

    public void delete(UUID id) {
        Seat seat = findById(id);
        seatRepository.delete(seat);
    }
    
    // Método para ocupar um assento
    public Seat occupySeat(UUID seatId, UUID patientId) {
        Seat seat = findById(seatId);
        seat.setOccupied(true);
        seat.setPatient(patientService.findById(patientId));
        return save(seat);
    }
    
    // Método para liberar um assento
    public Seat freeSeat(UUID seatId) {
        Seat seat = findById(seatId);
        seat.setOccupied(false);
        seat.setPatient(null);
        return save(seat);
    }
} 