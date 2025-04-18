package com.douglasLessa.emergencyBedApi.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bed")
public class Bed {
     private UUID id = UUID.randomUUID();
     

    
}
