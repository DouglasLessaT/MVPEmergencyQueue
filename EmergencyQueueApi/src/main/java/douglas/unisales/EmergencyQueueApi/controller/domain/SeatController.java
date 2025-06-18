package douglas.unisales.EmergencyQueueApi.controller.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import douglas.unisales.EmergencyQueueApi.model.domain.Seat;
import douglas.unisales.EmergencyQueueApi.services.domain.SeatService;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping
    public ResponseEntity<Seat> create(@RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.save(seat));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(seatService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Seat>> findAll() {
        return ResponseEntity.ok(seatService.findAll());
    }
    
    @GetMapping("/infermaria/{infermariaId}")
    public ResponseEntity<List<Seat>> findByInfermariaId(@PathVariable UUID infermariaId) {
        return ResponseEntity.ok(seatService.findByInfermariaId(infermariaId));
    }
    
    @PostMapping("/{id}/occupy")
    public ResponseEntity<Seat> occupySeat(@PathVariable UUID id, @RequestParam UUID patientId) {
        return ResponseEntity.ok(seatService.occupySeat(id, patientId));
    }
    
    @PostMapping("/{id}/free")
    public ResponseEntity<Seat> freeSeat(@PathVariable UUID id) {
        return ResponseEntity.ok(seatService.freeSeat(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> update(@PathVariable UUID id, @RequestBody Seat seat) {
        seat.setId(id);
        return ResponseEntity.ok(seatService.save(seat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 