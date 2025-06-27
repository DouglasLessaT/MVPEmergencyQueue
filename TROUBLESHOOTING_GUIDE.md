# Guia de Troubleshooting - Integração Flutter + Java API

## Problema: 404 Not Found

### 1. Verificar se o Backend está rodando

```bash
# Verificar se a aplicação Java está rodando na porta 8081
curl http://localhost:8081/EmergencyApi/api/map/test
```

**Resposta esperada:**
```json
{
  "message": "MapController is working!",
  "status": "OK"
}
```

### 2. Verificar URLs dos Endpoints

**URLs corretas:**
- Teste: `http://localhost:8081/EmergencyApi/api/map/test`
- Health: `http://localhost:8081/EmergencyApi/api/map/health`
- Hospitais: `http://localhost:8081/EmergencyApi/api/map/hospitals`
- Hospital específico: `http://localhost:8081/EmergencyApi/api/map/hospitals/{id}`
- Por status: `http://localhost:8081/EmergencyApi/api/map/hospitals/status/{code}`

### 3. Verificar Configuração do Banco de Dados

1. **Executar o script SQL:**
```sql
-- Executar o arquivo: EmergencyQueueApi/src/main/resources/db_migration/hospitals_data.sql
```

2. **Verificar se os dados foram inseridos:**
```sql
SELECT * FROM hospitals;
SELECT * FROM address;
SELECT * FROM status;
SELECT * FROM "Queue_patinent";
```

### 4. Verificar Logs do Spring Boot

Procure por erros nos logs da aplicação Java:
- Erros de conexão com banco
- Erros de mapeamento JPA
- Erros de beans não encontrados

### 5. Testar Endpoints Individualmente

```bash
# Teste básico
curl http://localhost:8081/EmergencyApi/api/map/test

# Health check
curl http://localhost:8081/EmergencyApi/api/map/health

# Listar hospitais
curl http://localhost:8081/EmergencyApi/api/map/hospitals
```

### 6. Verificar CORS

Se houver problemas de CORS, verificar se o `@CrossOrigin(origins = "*")` está presente no MapController.

### 7. Verificar Dependências Flutter

Certificar que a dependência `http` está no `pubspec.yaml`:
```yaml
dependencies:
  http: ^1.1.0
```

### 8. Testar no Flutter

Adicionar um botão de teste na página do mapa:

```dart
ElevatedButton(
  onPressed: () async {
    try {
      final result = await MapService.testConnection();
      print('Teste bem-sucedido: $result');
    } catch (e) {
      print('Erro no teste: $e');
    }
  },
  child: Text('Testar Conexão'),
)
```

## Problemas Comuns e Soluções

### Problema 1: "Connection refused"
**Causa:** Backend não está rodando
**Solução:** Iniciar a aplicação Java

### Problema 2: "404 Not Found"
**Causa:** URL incorreta ou endpoint não mapeado
**Solução:** Verificar URLs e mapeamentos no controller

### Problema 3: "500 Internal Server Error"
**Causa:** Erro no backend (banco, lógica, etc.)
**Solução:** Verificar logs do Spring Boot

### Problema 4: "CORS error"
**Causa:** Problema de CORS
**Solução:** Verificar anotações @CrossOrigin

### Problema 5: "No data returned"
**Causa:** Banco de dados vazio
**Solução:** Executar script de inserção de dados

## Checklist de Verificação

- [ ] Backend Java rodando na porta 8081
- [ ] Banco de dados PostgreSQL conectado
- [ ] Dados de teste inseridos no banco
- [ ] Endpoints respondendo corretamente
- [ ] CORS configurado
- [ ] Dependência http no Flutter
- [ ] URLs corretas no MapService Flutter

## Comandos Úteis

```bash
# Verificar se a porta 8081 está em uso
netstat -an | grep 8081

# Testar endpoint com curl
curl -v http://localhost:8081/EmergencyApi/api/map/test

# Verificar logs do Spring Boot
tail -f logs/application.log
```

## Próximos Passos

1. Testar o endpoint `/test` primeiro
2. Se funcionar, testar `/health`
3. Se funcionar, testar `/hospitals`
4. Se tudo funcionar, integrar no Flutter 