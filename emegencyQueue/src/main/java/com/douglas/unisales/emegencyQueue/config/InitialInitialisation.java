package com.douglas.unisales.emegencyQueue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.douglas.unisales.emegencyQueue.model.domain.Hospital;
import com.douglas.unisales.emegencyQueue.model.domain.Company;
import com.douglas.unisales.emegencyQueue.model.domain.Address;
import com.douglas.unisales.emegencyQueue.model.domain.Status;
import com.douglas.unisales.emegencyQueue.model.domain.ServicePhase;
import com.douglas.unisales.emegencyQueue.model.domain.QueuePatinent;
import com.douglas.unisales.emegencyQueue.model.domain.Patient;
import com.douglas.unisales.emegencyQueue.model.domain.Ambulance;
import com.douglas.unisales.emegencyQueue.model.domain.Bedroom;
import com.douglas.unisales.emegencyQueue.model.domain.Bed;
import com.douglas.unisales.emegencyQueue.model.domain.MedicalCare;
import com.douglas.unisales.emegencyQueue.model.domain.Transfer;
import com.douglas.unisales.emegencyQueue.model.security.User;
import com.douglas.unisales.emegencyQueue.repository.domain.HospitalRepository;
import com.douglas.unisales.emegencyQueue.services.security.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Component
public class InitialInitialisation implements CommandLineRunner {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Hospital hospital = hospitalRepository.findAll().stream().findFirst().orElse(null);
        if (hospital == null) {
            hospital = new Hospital();
            hospital.setId(UUID.randomUUID());
            hospital.setName("Hospital Teste");
            // Adicione outros campos obrigatórios se necessário
            hospitalRepository.save(hospital);
        }

        try {
            User u = new User();
            u.setLogin("admin");
            u.setSenha(passwordEncoder.encode("1234"));
            u.getPermissoes().add("ROLE_USER");
            u.getPermissoes().add("ROLE_ADMIN");
            u.setHospital(hospital);
            userService.insert(u);
            System.out.println("Usuário admin criado com sucesso!");
        } catch (Exception ex) {
            System.out.println("Erro ao criar usuário admin: " + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            User u = new User();
            u.setLogin("usuario1");
            u.setSenha(passwordEncoder.encode("1234"));
            u.getPermissoes().add("ROLE_USER");
            u.getPermissoes().add("ROLE_TAREFAS");
            u.setHospital(hospital);
            userService.insert(u);
        } catch (Exception ex) {
        }

        try {
            User u = new User();
            u.setLogin("usuario2");
            u.setSenha(passwordEncoder.encode("1234"));
            u.getPermissoes().add("ROLE_USER");
            u.setHospital(hospital);
            userService.insert(u);
        } catch (Exception ex) {
        }

        // Company
        Company company = new Company();
        company.setId(UUID.randomUUID());
        company.setName("Empresa Teste");
        company.setCnpj("12345678000199");
        // Salve no repositório correspondente

        // Address
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setCity("Cidade");
        address.setCountry("País");
        // ... outros campos ...
        // Salve no repositório correspondente

        // Status
        Status status = new Status();
        status.setId(UUID.randomUUID());
        status.setName("Status Teste");
        // ... outros campos ...
        // Salve no repositório correspondente

        // ServicePhase
        ServicePhase servicePhase = new ServicePhase();
        servicePhase.setId(UUID.randomUUID());
        servicePhase.setName("Fase Teste");
        // ... outros campos ...
        // Salve no repositório correspondente

        // QueuePatinent
        QueuePatinent queue = new QueuePatinent();
        queue.setId(UUID.randomUUID());
        queue.setNameQueue("Fila Teste");
        queue.setHospital(hospital);
        // ... outros campos ...
        // Salve no repositório correspondente

        // Patient
        Patient patient = new Patient();
        patient.setId(UUID.randomUUID());
        patient.setName("Paciente Teste");
        // ... outros campos ...
        // Salve no repositório correspondente

        // Ambulance
        Ambulance ambulance = new Ambulance();
        ambulance.setId(UUID.randomUUID());
        ambulance.setModel("Modelo Teste");
        ambulance.setHospital(hospital);
        // ... outros campos ...
        // Salve no repositório correspondente

        // Bedroom
        Bedroom bedroom = new Bedroom();
        bedroom.setId(UUID.randomUUID());
        bedroom.setHospital(hospital);
        // ... outros campos ...
        // Salve no repositório correspondente

        // Bed
        Bed bed = new Bed();
        bed.setId(UUID.randomUUID());
        bed.setBedroom(bedroom);
        // ... outros campos ...
        // Salve no repositório correspondente

        // MedicalCare
        MedicalCare medicalCare = new MedicalCare();
        medicalCare.setId(UUID.randomUUID());
        // ... outros campos ...
        // Salve no repositório correspondente

        // Transfer
        Transfer transfer = new Transfer();
        transfer.setId(UUID.randomUUID());
        // ... outros campos ...
        // Salve no repositório correspondente
    }
} 