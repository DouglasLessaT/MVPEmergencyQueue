# Integração do Mapa - Java API + Flutter

Este documento descreve a implementação do MapService e MapController no Java para fornecer dados para o mapa Flutter.

## Estrutura Criada

### Backend (Java)

#### 1. MapService (`EmergencyQueueApi/src/main/java/douglas/unisales/EmergencyQueueApi/services/domain/MapService.java`)

O MapService consolida dados de múltiplas entidades para fornecer informações completas sobre hospitais para o mapa:

**Métodos principais:**
- `getHospitalsWithMapData()`: Retorna todos os hospitais com dados para o mapa
- `getHospitalDetailsById(String hospitalId)`: Retorna detalhes completos de um hospital
- `getHospitalsByStatus(String statusCode)`: Filtra hospitais por status

**Dados consolidados:**
- Informações básicas do hospital (nome, telefone, endereço)
- Coordenadas geográficas (latitude/longitude)
- Status atual do hospital
- Dados da fila de emergência
- Capacidade e ocupação
- Leitos ocupados
- Cuidados médicos ativos

#### 2. MapController (`EmergencyQueueApi/src/main/java/douglas/unisales/EmergencyQueueApi/controller/domain/MapController.java`)

O MapController expõe endpoints REST para o Flutter consumir:

**Endpoints:**
- `GET /api/map/hospitals` - Lista todos os hospitais com dados do mapa
- `GET /api/map/hospitals/{hospitalId}` - Detalhes de um hospital específico
- `GET /api/map/hospitals/status/{statusCode}` - Hospitais filtrados por status
- `GET /api/map/health` - Verificação de saúde do serviço

#### 3. Repositórios Atualizados

Foram adicionados métodos necessários aos repositórios:
- `QueuePatinentRepository.findByHospital(Hospital hospital)`
- `StatusRepository.findByCode(String code)`
- `HospitalRepository.findByStatus(Status status)`
- `SeatRepository.findByOccupied(boolean occupied)`
- `MedicalCareRepository.findByStatusIdIsNotNull()`

### Frontend (Flutter)

#### 1. MapService Flutter (`emergency_queue_flutter/lib/core/services/map_service.dart`)

Serviço Flutter para consumir a API Java:

**Funcionalidades:**
- Comunicação HTTP com a API Java
- Parsing dos dados JSON
- Conversão para formato compatível com o mapa Flutter
- Tratamento de erros de conexão
- Health check do serviço

#### 2. MapPageWithAPI (`emergency_queue_flutter/lib/features/map/presentation/pages/map_page_with_api.dart`)

Versão da página do mapa que usa dados da API em vez de dados estáticos:

**Características:**
- Carregamento dinâmico de dados
- Indicador de loading
- Tratamento de erros de conexão
- Botão de refresh
- Integração completa com a API Java

## Como Usar

### 1. Configuração do Backend

1. Certifique-se de que o Spring Boot está rodando na porta 8080
2. Configure o banco de dados com dados de hospitais incluindo coordenadas
3. Verifique se os endpoints estão funcionando: `http://localhost:8080/api/map/health`

### 2. Configuração do Flutter

1. Adicione a dependência `http` no `pubspec.yaml`:
```yaml
dependencies:
  http: ^1.1.0
```

2. Configure a URL base no `MapService`:
```dart
static const String baseUrl = 'http://localhost:8080/api/map';
```

3. Use a `MapPageWithAPI` em vez da `MapPage` original

### 3. Estrutura de Dados Esperada

O backend retorna dados no formato:
```json
{
  "id": "uuid",
  "name": "Nome do Hospital",
  "phone": "(27) 3636-3514",
  "escalation": "emergency",
  "latitude": -20.3328,
  "longitude": -40.2990,
  "address": "Endereço completo",
  "totalCapacity": 100,
  "activeCapacity": 75,
  "status": "moderada",
  "statusCode": "MOD",
  "statusColor": "#FFA500",
  "queueSize": 15,
  "averageWaitTime": 45.0,
  "occupiedSeats": 60,
  "activeMedicalCares": 25
}
```

## Entidades Utilizadas

### Hospital.java
- Dados básicos do hospital
- Endereço com coordenadas
- Capacidade e status

### Status.java
- Status do hospital (baixa, moderada, alta)
- Códigos e cores para identificação visual

### QueuePatinent.java
- Filas de pacientes
- Tamanho atual e tempo de espera médio

### Seat.java
- Leitos e assentos
- Informações de ocupação

### MedicalCare.java
- Cuidados médicos ativos
- Status dos tratamentos

## Vantagens da Implementação

1. **Dados Reais**: Usa dados do banco de dados em vez de dados estáticos
2. **Tempo Real**: Pode ser atualizado dinamicamente
3. **Escalável**: Fácil adicionar novos hospitais ou campos
4. **Flexível**: Suporta filtros por status e outros critérios
5. **Robusto**: Tratamento de erros e health checks
6. **Performance**: Consultas otimizadas no backend

## Próximos Passos

1. Implementar cache no Flutter para melhor performance
2. Adicionar atualização automática dos dados
3. Implementar filtros avançados
4. Adicionar notificações push para mudanças de status
5. Implementar autenticação se necessário 