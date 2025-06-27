package douglas.unisales.EmergencyQueueApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import douglas.unisales.EmergencyQueueApi.model.domain.Hospital;
import douglas.unisales.EmergencyQueueApi.model.domain.Company;
import douglas.unisales.EmergencyQueueApi.model.domain.Address;
import douglas.unisales.EmergencyQueueApi.model.domain.Status;
import douglas.unisales.EmergencyQueueApi.model.domain.ServicePhase;
import douglas.unisales.EmergencyQueueApi.model.domain.QueuePatinent;
import douglas.unisales.EmergencyQueueApi.model.domain.Patient;
import douglas.unisales.EmergencyQueueApi.model.domain.Ambulance;
import douglas.unisales.EmergencyQueueApi.model.domain.Bedroom;
import douglas.unisales.EmergencyQueueApi.model.domain.Bed;
import douglas.unisales.EmergencyQueueApi.model.domain.MedicalCare;
import douglas.unisales.EmergencyQueueApi.model.domain.Transfer;
import douglas.unisales.EmergencyQueueApi.model.security.User;
import douglas.unisales.EmergencyQueueApi.repository.domain.HospitalRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.AddressRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.StatusRepository;
import douglas.unisales.EmergencyQueueApi.repository.domain.QueuePatinentRepository;
import douglas.unisales.EmergencyQueueApi.services.security.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Component
public class InitialInitialisation implements CommandLineRunner {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private QueuePatinentRepository queuePatinentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("[DEBUG] Iniciando inicialização dos dados...");
        
        // Criar Status para hospitais
        Status statusBaixa = createStatusIfNotExists("Baixa", "LOW", "#00FF00", "Ocupação baixa", "HOSPITAL");
        Status statusModerada = createStatusIfNotExists("Moderada", "MOD", "#FFA500", "Ocupação moderada", "HOSPITAL");
        Status statusAlta = createStatusIfNotExists("Alta", "HIGH", "#FF0000", "Ocupação alta", "HOSPITAL");
        
        // Criar Status para filas
        Status statusFilaNormal = createStatusIfNotExists("Normal", "NORMAL", "#00FF00", "Fila normal", "QUEUE");
        Status statusFilaModerada = createStatusIfNotExists("Moderada", "MOD", "#FFA500", "Fila moderada", "QUEUE");
        Status statusFilaCritica = createStatusIfNotExists("Crítica", "CRITICAL", "#FF0000", "Fila crítica", "QUEUE");

        // Verificar se já existem hospitais
        if (hospitalRepository.count() == 0) {
            System.out.println("[DEBUG] Nenhum hospital encontrado. Criando hospitais de exemplo...");
            
            // Hospital 1: Hospital Estadual Antônio Bezerra de Faria
            createHospitalWithData(
                "Hospital Estadual Antônio Bezerra de Faria",
                "(27) 3636-3514",
                "emergency",
                "R. Liberalino Lima, s/n - Olaria, Vila Velha - ES, 29123-180",
                -20.3328, -40.2990,
                100, 75, 80, 20, 2, 3,
                statusModerada,
                15, 45.0, statusFilaModerada
            );

            // Hospital 2: Hospital Evangélico de Vila Velha
            createHospitalWithData(
                "Hospital Evangélico de Vila Velha",
                "(27) 2121-1000",
                "pediatrics",
                "Av. Champagnat, 555 - Centro, Vila Velha - ES, 29101-220",
                -20.34605, -40.34327,
                80, 45, 60, 15, 1, 2,
                statusBaixa,
                8, 20.0, statusFilaNormal
            );

            // Hospital 3: Hospital Meridional Praia da Costa
            createHospitalWithData(
                "Hospital Meridional Praia da Costa",
                "(27) 2121-0200",
                "neurology",
                "R. Prof. Telmo de Souza Torres, 116 - Praia da Costa, Vila Velha - ES, 29101-295",
                -20.3342, -40.2877,
                120, 110, 100, 25, 3, 4,
                statusAlta,
                28, 80.0, statusFilaCritica
            );

            // Hospital 4: UPA Vila Velha Centro
            createHospitalWithData(
                "UPA Vila Velha Centro",
                "(27) 3149-3456",
                "emergency",
                "Rua Santa Rosa, 150 - Centro, Vila Velha - ES, 29100-040",
                -20.3399, -40.30690,
                50, 35, 40, 10, 1, 1,
                statusModerada,
                12, 35.0, statusFilaModerada
            );

            // Hospital 5: Hospital Santa Mônica
            createHospitalWithData(
                "Hospital Santa Mônica",
                "(27) 3320-3500",
                "oncology",
                "Rod. do Sol, s/n - Km 01 - Praia de Itaparica, Vila Velha - ES, 29102-020",
                -20.3601, -40.2960,
                90, 55, 70, 18, 2, 3,
                statusBaixa,
                5, 15.0, statusFilaNormal
            );

            System.out.println("[DEBUG] Todos os 5 hospitais foram criados com sucesso!");
        } else {
            System.out.println("[DEBUG] Hospitais já existem no banco. Pulando criação...");
        }

        // Criar usuário admin se não existir
        Hospital firstHospital = hospitalRepository.findAll().stream().findFirst().orElse(null);
        if (firstHospital != null) {
            createAdminUser(firstHospital);
        }

        System.out.println("[DEBUG] Inicialização concluída!");
    }

    private Status createStatusIfNotExists(String name, String code, String color, String description, String entityType) {
        Status status = statusRepository.findByCode(code).orElse(null);
        if (status == null) {
            status = new Status();
            status.setId(UUID.randomUUID());
            status.setName(name);
            status.setCode(code);
            status.setColor(color);
            status.setDescription(description);
            status.setEntityType(entityType);
            status = statusRepository.save(status);
            System.out.println("[DEBUG] Status criado: " + name);
        } else {
            System.out.println("ℹ[DEBUG] Status já existe: " + name);
        }
        return status;
    }

    private void createHospitalWithData(
            String name, String phone, String escalation, String fullAddress,
            double latitude, double longitude,
            int totalCapacity, int activeCapacity, int numberOfBeds, int numberOfRooms, 
            int numberOfBuildings, int numberOfFloors,
            Status status,
            double queueSize, double averageWaitTime, Status queueStatus) {
        
        try {
            // Criar endereço
            Address address = new Address();
            address.setId(UUID.randomUUID());
            
            // Parse do endereço completo
            String[] addressParts = fullAddress.split(", ");
            if (addressParts.length >= 4) {
                address.setStreet(addressParts[0]);
                address.setNumber(addressParts[1]);
                address.setNeighborhood(addressParts[2]);
                address.setCity("Vila Velha");
                address.setState("ES");
                address.setCountry("Brasil");
                if (addressParts.length > 3) {
                    address.setZipCode(addressParts[3]);
                }
            } else {
                // Fallback se não conseguir fazer parse
                address.setStreet(fullAddress);
                address.setCity("Vila Velha");
                address.setState("ES");
                address.setCountry("Brasil");
            }
            
            address.setLatitude(latitude);
            address.setLongitude(longitude);
            
            address = addressRepository.save(address);
            System.out.println("[DEBUG] Endereço criado para " + name + ": " + fullAddress);

            // Criar hospital
            Hospital hospital = new Hospital();
            hospital.setId(UUID.randomUUID());
            hospital.setName(name);
            hospital.setPhone(phone);
            hospital.setEscalation(escalation);
            hospital.setAddress(address);
            hospital.setTotalCapacity(totalCapacity);
            hospital.setActiveCapacity(activeCapacity);
            hospital.setNumberOfBeds(numberOfBeds);
            hospital.setNumberOfRooms(numberOfRooms);
            hospital.setNumberOfBuildings(numberOfBuildings);
            hospital.setNumberOfFloors(numberOfFloors);
            hospital.setStatus(status);
            
            hospital = hospitalRepository.save(hospital);
            System.out.println("[DEBUG] Hospital criado: " + name);

            // Criar fila de emergência
            QueuePatinent queue = new QueuePatinent();
            queue.setId(UUID.randomUUID());
            queue.setNameQueue("Fila de Emergência");
            queue.setDescription("Fila principal de emergência");
            queue.setCurrentQueueSize(queueSize);
            queue.setAverageWaitTime(averageWaitTime);
            queue.setLastUpdate(new java.sql.Date(System.currentTimeMillis()));
            queue.setHospital(hospital);
            queue.setStatus(queueStatus);
            
            queuePatinentRepository.save(queue);
            System.out.println("[DEBUG] Fila criada para " + name + ": " + queueSize + " pacientes, " + averageWaitTime + " min de espera");

        } catch (Exception e) {
            System.err.println(" [DEBUG] Erro ao criar hospital " + name + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createAdminUser(Hospital hospital) {
        try {
            User u = new User();
            u.setLogin("admin");
            u.setSenha(passwordEncoder.encode("1234"));
            u.getPermissoes().add("ROLE_USER");
            u.getPermissoes().add("ROLE_ADMIN");
            u.setHospital(hospital);
            userService.insert(u);
            System.out.println("👤 [DEBUG] Usuário admin criado com sucesso!");
        } catch (Exception ex) {
            System.out.println("⚠️ [DEBUG] Usuário admin já existe ou erro ao criar: " + ex.getMessage());
        }

        try {
            User u = new User();
            u.setLogin("usuario1");
            u.setSenha(passwordEncoder.encode("1234"));
            u.getPermissoes().add("ROLE_USER");
            u.getPermissoes().add("ROLE_TAREFAS");
            u.setHospital(hospital);
            userService.insert(u);
            System.out.println("👤 [DEBUG] Usuário usuario1 criado com sucesso!");
        } catch (Exception ex) {
            System.out.println("⚠️ [DEBUG] Usuário usuario1 já existe ou erro ao criar: " + ex.getMessage());
        }

        try {
            User u = new User();
            u.setLogin("usuario2");
            u.setSenha(passwordEncoder.encode("1234"));
            u.getPermissoes().add("ROLE_USER");
            u.setHospital(hospital);
            userService.insert(u);
            System.out.println("👤 [DEBUG] Usuário usuario2 criado com sucesso!");
        } catch (Exception ex) {
            System.out.println("⚠️ [DEBUG] Usuário usuario2 já existe ou erro ao criar: " + ex.getMessage());
        }
    }
} 