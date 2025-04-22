-- Criação da tabela de endereços
CREATE TABLE address (
    id UUID PRIMARY KEY,
    street VARCHAR(255),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    state VARCHAR(100),
    city VARCHAR(100)
);

-- Criação da tabela de empresas
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE
);

-- Criação da tabela de hospitais
CREATE TABLE hospitals (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    escalation VARCHAR(255),
    address_hospital_id UUID REFERENCES address(id),
    total_capacity INT,
    active_capacity INT,
    number_of_beds INT,
    number_of_rooms INT,
    number_of_buildings INT,
    number_of_floors INT
);

-- Criação da tabela de status
CREATE TABLE status (
    id UUID PRIMARY KEY,
    nome VARCHAR(50),
    codigo VARCHAR(50),
    cor VARCHAR(50)
);

-- Criação da tabela de quartos
CREATE TABLE bedroom (
    id UUID PRIMARY KEY,
    codigo VARCHAR(50),
    andar VARCHAR(50),
    sala VARCHAR(50),
    tipo VARCHAR(50),
    hospital_id UUID REFERENCES hospitals(id),
    status_id UUID REFERENCES status(id)
);

-- Criação da tabela de leitos
CREATE TABLE bed (
    id UUID PRIMARY KEY,
    code VARCHAR(50),
    floor VARCHAR(50),
    type VARCHAR(50),
    status_id UUID REFERENCES status(id),
    patient_id UUID REFERENCES patients(id),
    bedroom_id UUID REFERENCES bedroom(id),
    entry_pacient DATE,
    exit_pacient DATE
);

-- Criação da tabela de pacientes
CREATE TABLE patients (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    age INT,
    insurance VARCHAR(255),
    health_plan VARCHAR(255),
    cpf VARCHAR(20) UNIQUE,
    rg VARCHAR(20),
    mother_name VARCHAR(255),
    father_name VARCHAR(255),
    medical_record_pdf VARCHAR(255)
);

-- Criação da tabela de ambulâncias
CREATE TABLE ambulances (
    id UUID PRIMARY KEY,
    license_plate VARCHAR(20) UNIQUE,
    model VARCHAR(50),
    type VARCHAR(50),
    company_id UUID REFERENCES companies(id)
);

-- Criação da tabela de transferências
CREATE TABLE transfers (
    id UUID PRIMARY KEY,
    patient_id UUID REFERENCES patients(id),
    origin_hospital_id UUID REFERENCES hospitals(id),
    destination_hospital_id UUID REFERENCES hospitals(id),
    ambulance_id UUID REFERENCES ambulances(id),
    date TIMESTAMP,
    status_id UUID REFERENCES status(id),
    address_destination_hospital_id UUID REFERENCES address(id)
);

-- Criação da tabela de atendimentos
CREATE TABLE medical_care (
    id UUID PRIMARY KEY,
    floor VARCHAR(50),
    room_number VARCHAR(50),
    is_icu BOOLEAN,
    patient_id UUID REFERENCES patients(id),
    status_id UUID REFERENCES status(id)
);

-- Criação da tabela de usuários
CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    permissoes TEXT
);