CREATE TABLE hospitals (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE status (
    id UUID PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE service_phase (
    id UUID PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE address (
    id UUID PRIMARY KEY,
    latitude FLOAT,
    longitude FLOAT,
    city VARCHAR(255),
    country VARCHAR(255),
    neighborhood VARCHAR(255),
    number VARCHAR(255),
    state VARCHAR(255),
    street VARCHAR(255),
    zip_code VARCHAR(255)
);

CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE patients (
    id UUID PRIMARY KEY,
    queue_id UUID,
    -- outros campos
    CONSTRAINT fk_patients_queue FOREIGN KEY (queue_id) REFERENCES queue_patinent(id)
);

CREATE TABLE queue_patinent (
    id UUID PRIMARY KEY,
    hospital_id UUID,
    -- outros campos
    CONSTRAINT fk_queue_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE TABLE ambulances (
    id UUID PRIMARY KEY,
    hospital_id UUID,
    -- outros campos
    CONSTRAINT fk_ambulance_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);

CREATE TABLE bedroom (
    id UUID PRIMARY KEY,
    hospital_id UUID,
    status_id UUID,
    -- outros campos
    CONSTRAINT fk_bedroom_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals(id),
    CONSTRAINT fk_bedroom_status FOREIGN KEY (status_id) REFERENCES status(id)
);

CREATE TABLE bed (
    id UUID PRIMARY KEY,
    bedroom_id UUID,
    patient_id UUID,
    status_id UUID,
    -- outros campos
    CONSTRAINT fk_bed_bedroom FOREIGN KEY (bedroom_id) REFERENCES bedroom(id),
    CONSTRAINT fk_bed_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_bed_status FOREIGN KEY (status_id) REFERENCES status(id),
    CONSTRAINT unique_patient UNIQUE (patient_id)
);

CREATE TABLE medical_care (
    id UUID PRIMARY KEY,
    patient_id UUID,
    service_phase_id UUID,
    status_id UUID,
    -- outros campos
    CONSTRAINT fk_medicalcare_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_medicalcare_servicephase FOREIGN KEY (service_phase_id) REFERENCES service_phase(id),
    CONSTRAINT fk_medicalcare_status FOREIGN KEY (status_id) REFERENCES status(id)
);

CREATE TABLE transfers (
    id UUID PRIMARY KEY,
    ambulance_id UUID,
    destination_hospital_id UUID,
    origin_hospital_id UUID,
    addressorigin_hospital_id UUID,
    patient_id UUID,
    status_id UUID,
    -- outros campos
    CONSTRAINT fk_transfer_ambulance FOREIGN KEY (ambulance_id) REFERENCES ambulances(id),
    CONSTRAINT fk_transfer_destination FOREIGN KEY (destination_hospital_id) REFERENCES hospitals(id),
    CONSTRAINT fk_transfer_origin FOREIGN KEY (origin_hospital_id) REFERENCES hospitals(id),
    CONSTRAINT fk_transfer_addressorigin FOREIGN KEY (addressorigin_hospital_id) REFERENCES hospitals(id),
    CONSTRAINT fk_transfer_patient FOREIGN KEY (patient_id) REFERENCES patients(id),
    CONSTRAINT fk_transfer_status FOREIGN KEY (status_id) REFERENCES status(id)
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    hospital_id UUID,
    login VARCHAR(255) UNIQUE,
    senha VARCHAR(255),
    permissoes VARCHAR(255),
    -- outros campos
    CONSTRAINT fk_user_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals(id)
);