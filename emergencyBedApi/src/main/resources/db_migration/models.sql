-- Criação da tabela de hospitais
CREATE TABLE hospitals (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    escalation VARCHAR(255),
    street VARCHAR(255),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    state VARCHAR(100),
    city VARCHAR(100),
    total_capacity INT,
    active_capacity INT,
    number_of_beds INT,
    number_of_rooms INT,
    number_of_buildings INT,
    number_of_floors INT
);

-- Criação da tabela de quartos
CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    code VARCHAR(50),
    floor VARCHAR(50),
    room_number VARCHAR(50),
    type VARCHAR(50),
    hospital_id INT REFERENCES hospitals(id),
    status_id INT REFERENCES statuses(id)
);

-- Criação da tabela de leitos
CREATE TABLE beds (
    id UUID PRIMARY KEY,
    code VARCHAR(50),
    floor VARCHAR(50),
    room_number VARCHAR(50),
    type VARCHAR(50),
    room_id UUID REFERENCES rooms(id),
    patient_id UUID REFERENCES patients(id),
    patient_entry_date DATE,
    patient_exit_date DATE,
    status_id INT REFERENCES statuses(id)
);

-- Criação da tabela de pacientes
CREATE TABLE patients (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    age INT,
    insurance VARCHAR(255),
    health_plan VARCHAR(255),
    cpf VARCHAR(20),
    rg VARCHAR(20),
    mother_name VARCHAR(255),
    father_name VARCHAR(255),
    medical_record_pdf VARCHAR(255)
);

-- Criação da tabela de ambulâncias
CREATE TABLE ambulances (
    id UUID PRIMARY KEY,
    license_plate VARCHAR(20),
    model VARCHAR(50),
    type VARCHAR(50),
    company_id INT REFERENCES companies(id)
);

-- Criação da tabela de empresas
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20)
);

-- Criação da tabela de status
CREATE TABLE status (
    id UUID PRIMARY KEY,
    name VARCHAR(50),
    code VARCHAR(50),
    model VARCHAR(50),
    color VARCHAR(50)
);

-- Criação da tabela de transferências
CREATE TABLE transfers (
    id UUID PRIMARY KEY,
    patient_id UUID REFERENCES patients(id),
    origin_hospital_id INT REFERENCES hospitals(id),
    destination_hospital_id INT REFERENCES hospitals(id),
    ambulance_id INT REFERENCES ambulances(id),
    date TIMESTAMP,
    status_id INT REFERENCES statuses(id)
);

-- Criação da tabela de atendimentos
CREATE TABLE medical_care (
    id UUID PRIMARY KEY,
    floor VARCHAR(50),
    room_number VARCHAR(50),
    is_icu BOOLEAN,
    patient_id UUID REFERENCES patients(id),
    status_id INT REFERENCES statuses(id)
);

-- Criação da tabela de usuários
CREATE TABLE users (
    id UUID PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    permissions TEXT
);