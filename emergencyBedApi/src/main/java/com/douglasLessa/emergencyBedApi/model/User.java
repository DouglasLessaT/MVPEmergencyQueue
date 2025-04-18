package com.douglasLessa.emergencyBedApi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

 private List<String> permissoes = new ArrayList<>();
}