-- Tabela Address
CREATE TABLE address (
    id UUID PRIMARY KEY,
    street VARCHAR(255),
    number VARCHAR(50),
    neighborhood VARCHAR(255),
    country VARCHAR(255),
    zip_code VARCHAR(20),
    state VARCHAR(100),
    city VARCHAR(100)
);

-- Tabela Hospital
CREATE TABLE hospitals (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    escalation VARCHAR(255),
    address_hospital_id UUID,
    total_capacity INTEGER,
    active_capacity INTEGER,
    number_of_beds INTEGER,
    number_of_rooms INTEGER,
    number_of_buildings INTEGER,
    number_of_floors INTEGER,
    CONSTRAINT fk_address_hospital FOREIGN KEY (address_hospital_id) REFERENCES address (id)
);

-- Tabela Status
CREATE TABLE status (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(50),
    color VARCHAR(50),
    description VARCHAR(255)
);

-- Tabela Patient
CREATE TABLE patients (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(50),
    age INTEGER,
    insurance VARCHAR(255),
    health_plan VARCHAR(255),
    cpf VARCHAR(20),
    rg VARCHAR(20),
    mother_name VARCHAR(255),
    father_name VARCHAR(255),
    medical_record_pdf VARCHAR(255)
);

-- Tabela Bedroom
CREATE TABLE bedroom (
    id UUID PRIMARY KEY,
    codigo VARCHAR(255),
    andar VARCHAR(50),
    sala VARCHAR(50),
    tipo VARCHAR(50),
    hospital_id UUID,
    status_id UUID,
    CONSTRAINT fk_bedroom_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals (id),
    CONSTRAINT fk_bedroom_status FOREIGN KEY (status_id) REFERENCES status (id)
);

-- Tabela Bed
CREATE TABLE bed (
    id UUID PRIMARY KEY,
    code VARCHAR(255),
    floor VARCHAR(50),
    type VARCHAR(50),
    status_id UUID,
    patient_id UUID,
    bedroom_id UUID,
    entry_pacient TIMESTAMP,
    exit_pacient TIMESTAMP,
    CONSTRAINT fk_bed_status FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT fk_bed_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_bed_bedroom FOREIGN KEY (bedroom_id) REFERENCES bedroom (id)
);

-- Tabela Ambulance
CREATE TABLE ambulances (
    id UUID PRIMARY KEY,
    license_plate VARCHAR(50),
    model VARCHAR(255),
    type VARCHAR(50),
    renavam VARCHAR(50),
    hospital_id UUID,
    CONSTRAINT fk_ambulance_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals (id)
);

-- Tabela MedicalCare
CREATE TABLE medical_care (
    id UUID PRIMARY KEY,
    floor VARCHAR(50),
    room_number VARCHAR(50),
    is_icu BOOLEAN,
    patient_id UUID,
    status_id UUID,
    CONSTRAINT fk_medicalcare_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_medicalcare_status FOREIGN KEY (status_id) REFERENCES status (id)
);

-- Tabela Transfer
CREATE TABLE transfers (
    id UUID PRIMARY KEY,
    patient_id UUID,
    origin_hospital_id UUID,
    destination_hospital_id UUID,
    ambulance_id UUID,
    date TIMESTAMP,
    status_id UUID,
    address_destination_hospital_id UUID,
    addressorigin_hospital_id UUID,
    CONSTRAINT fk_transfer_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_transfer_origin_hospital FOREIGN KEY (origin_hospital_id) REFERENCES hospitals (id),
    CONSTRAINT fk_transfer_destination_hospital FOREIGN KEY (destination_hospital_id) REFERENCES hospitals (id),
    CONSTRAINT fk_transfer_ambulance FOREIGN KEY (ambulance_id) REFERENCES ambulances (id),
    CONSTRAINT fk_transfer_status FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT fk_transfer_address_destination FOREIGN KEY (address_destination_hospital_id) REFERENCES address (id),
    CONSTRAINT fk_transfer_address_origin FOREIGN KEY (addressorigin_hospital_id) REFERENCES address (id)
);

-- Tabela User
CREATE TABLE users (
    id UUID PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    hospital_id UUID,
    permissoes TEXT,
    CONSTRAINT fk_user_hospital FOREIGN KEY (hospital_id) REFERENCES hospitals (id)
);

-- Tabela Company
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    cnpj VARCHAR(50)
);