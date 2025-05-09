package com.DouglasLessa.emergencyBedApi.model.security;

import java.util.ArrayList;
import java.util.UUID;

import com.DouglasLessa.emergencyBedApi.model.domain.Hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id = UUID.randomUUID();
    private String login;
    private String senha;
    
    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    private ArrayList<String> permissoes = new ArrayList<>();
    
}