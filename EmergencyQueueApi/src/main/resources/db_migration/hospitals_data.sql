-- Inserir Status para hospitais
INSERT INTO status (id, name, code, color, description, entity_type) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'Baixa', 'LOW', '#00FF00', 'Ocupação baixa', 'HOSPITAL'),
('550e8400-e29b-41d4-a716-446655440002', 'Moderada', 'MOD', '#FFA500', 'Ocupação moderada', 'HOSPITAL'),
('550e8400-e29b-41d4-a716-446655440003', 'Alta', 'HIGH', '#FF0000', 'Ocupação alta', 'HOSPITAL');

-- Inserir Status para filas
INSERT INTO status (id, name, code, color, description, entity_type) VALUES
('550e8400-e29b-41d4-a716-446655440004', 'Normal', 'NORMAL', '#00FF00', 'Fila normal', 'QUEUE'),
('550e8400-e29b-41d4-a716-446655440005', 'Moderada', 'MOD', '#FFA500', 'Fila moderada', 'QUEUE'),
('550e8400-e29b-41d4-a716-446655440006', 'Crítica', 'CRITICAL', '#FF0000', 'Fila crítica', 'QUEUE');

-- Inserir Endereços
INSERT INTO address (id, street, number, neighborhood, country, zip_code, state, city, latitude, longitude) VALUES
('550e8400-e29b-41d4-a716-446655440011', 'R. Liberalino Lima', 's/n', 'Olaria', 'Brasil', '29123-180', 'ES', 'Vila Velha', -20.3328, -40.2990),
('550e8400-e29b-41d4-a716-446655440012', 'Av. Champagnat', '555', 'Centro', 'Brasil', '29101-220', 'ES', 'Vila Velha', -20.34605, -40.34327),
('550e8400-e29b-41d4-a716-446655440013', 'R. Prof. Telmo de Souza Torres', '116', 'Praia da Costa', 'Brasil', '29101-295', 'ES', 'Vila Velha', -20.3342, -40.2877),
('550e8400-e29b-41d4-a716-446655440014', 'Rua Santa Rosa', '150', 'Centro', 'Brasil', '29100-040', 'ES', 'Vila Velha', -20.3399, -40.30690),
('550e8400-e29b-41d4-a716-446655440015', 'Rod. do Sol', 's/n - Km 01', 'Praia de Itaparica', 'Brasil', '29102-020', 'ES', 'Vila Velha', -20.3601, -40.2960);

-- Inserir Hospitais
INSERT INTO hospitals (id, name, phone, escalation, address_hospital_id, total_capacity, active_capacity, number_of_beds, number_of_rooms, number_of_buildings, number_of_floors, status_id) VALUES
('550e8400-e29b-41d4-a716-446655440021', 'Hospital Estadual Antônio Bezerra de Faria', '(27) 3636-3514', 'emergency', '550e8400-e29b-41d4-a716-446655440011', 100, 75, 80, 20, 2, 3, '550e8400-e29b-41d4-a716-446655440002'),
('550e8400-e29b-41d4-a716-446655440022', 'Hospital Evangélico de Vila Velha', '(27) 2121-1000', 'pediatrics', '550e8400-e29b-41d4-a716-446655440012', 80, 45, 60, 15, 1, 2, '550e8400-e29b-41d4-a716-446655440001'),
('550e8400-e29b-41d4-a716-446655440023', 'Hospital Meridional Praia da Costa', '(27) 2121-0200', 'neurology', '550e8400-e29b-41d4-a716-446655440013', 120, 110, 100, 25, 3, 4, '550e8400-e29b-41d4-a716-446655440003'),
('550e8400-e29b-41d4-a716-446655440024', 'UPA Vila Velha Centro', '(27) 3149-3456', 'emergency', '550e8400-e29b-41d4-a716-446655440014', 50, 35, 40, 10, 1, 1, '550e8400-e29b-41d4-a716-446655440002'),
('550e8400-e29b-41d4-a716-446655440025', 'Hospital Santa Mônica', '(27) 3320-3500', 'oncology', '550e8400-e29b-41d4-a716-446655440015', 90, 55, 70, 18, 2, 3, '550e8400-e29b-41d4-a716-446655440001');

-- Inserir Filas de Pacientes
INSERT INTO "Queue_patinent" (id, name_queue, description, current_queue_size, average_wait_time, last_update, hospital_id, status_id) VALUES
('550e8400-e29b-41d4-a716-446655440031', 'Fila de Emergência', 'Fila principal de emergência', 15, 45.0, CURRENT_DATE, '550e8400-e29b-41d4-a716-446655440021', '550e8400-e29b-41d4-a716-446655440005'),
('550e8400-e29b-41d4-a716-446655440032', 'Fila de Emergência', 'Fila principal de emergência', 8, 20.0, CURRENT_DATE, '550e8400-e29b-41d4-a716-446655440022', '550e8400-e29b-41d4-a716-446655440004'),
('550e8400-e29b-41d4-a716-446655440033', 'Fila de Emergência', 'Fila principal de emergência', 28, 80.0, CURRENT_DATE, '550e8400-e29b-41d4-a716-446655440023', '550e8400-e29b-41d4-a716-446655440006'),
('550e8400-e29b-41d4-a716-446655440034', 'Fila de Emergência', 'Fila principal de emergência', 12, 35.0, CURRENT_DATE, '550e8400-e29b-41d4-a716-446655440024', '550e8400-e29b-41d4-a716-446655440005'),
('550e8400-e29b-41d4-a716-446655440035', 'Fila de Emergência', 'Fila principal de emergência', 5, 15.0, CURRENT_DATE, '550e8400-e29b-41d4-a716-446655440025', '550e8400-e29b-41d4-a716-446655440004'); 