package com.DouglasLessa.emergencyBedApi.model.security;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.DouglasLessa.emergencyBedApi.model.domain.Hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class User {

    @Id
    private UUID id = UUID.randomUUID();
    private String login;
    private String senha;
    @OneToMany(mappedBy = "hospital_id")
    private List<Hospital> hospitals;
    private List<String> permissoes = new ArrayList<>();
}