import 'dart:convert';
import 'package:http/http.dart' as http;

class MapService {
  static const String baseUrl = 'http://localhost:8081/EmergencyApi/api/map';

  static Map<String, dynamic> _parseHospitalData(
    Map<String, dynamic> jsonData,
  ) {
    print('🔧 [DEBUG] _parseHospitalData - Processando dados do hospital');
    print('   ID: ${jsonData['id']}');
    print('   Nome: ${jsonData['name']}');
    print('   Lat: ${jsonData['latitude']}');
    print('   Lng: ${jsonData['longitude']}');

    final parsedData = {
      'id': jsonData['id'],
      'name': jsonData['name'] ?? '',
      'phone': jsonData['phone'] ?? '',
      'escalation': jsonData['escalation'] ?? '',
      'latitude': jsonData['latitude']?.toDouble() ?? 0.0,
      'longitude': jsonData['longitude']?.toDouble() ?? 0.0,
      'address': jsonData['address'] ?? '',
      'totalCapacity': jsonData['totalCapacity'] ?? 0,
      'activeCapacity': jsonData['activeCapacity'] ?? 0,
      'numberOfBeds': jsonData['numberOfBeds'] ?? 0,
      'numberOfRooms': jsonData['numberOfRooms'] ?? 0,
      'status': jsonData['status'] ?? '',
      'statusCode': jsonData['statusCode'] ?? '',
      'statusColor': jsonData['statusColor'] ?? '#000000',
      'queueSize': jsonData['queueSize'] ?? 0,
      'averageWaitTime': jsonData['averageWaitTime'] ?? 0.0,
      'queueStatus': jsonData['queueStatus'] ?? '',
      'occupiedSeats': jsonData['occupiedSeats'] ?? 0,
      'activeMedicalCares': jsonData['activeMedicalCares'] ?? 0,
    };

    print('🔧 [DEBUG] Dados parseados com sucesso');
    return parsedData;
  }

  // Método de teste para verificar conexão
  static Future<Map<String, dynamic>> testConnection() async {
    print('🧪 [DEBUG] testConnection() iniciado');
    print('🧪 [DEBUG] URL: $baseUrl/test');

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/test'),
        headers: {'Content-Type': 'application/json'},
      );

      print('🧪 [DEBUG] Status code: ${response.statusCode}');
      print('🧪 [DEBUG] Response body: ${response.body}');

      if (response.statusCode == 200) {
        final result = jsonDecode(response.body);
        print('✅ [DEBUG] Teste de conexão bem-sucedido: $result');
        return result;
      } else {
        print('❌ [DEBUG] Teste falhou com status: ${response.statusCode}');
        throw Exception('Test failed: ${response.statusCode}');
      }
    } catch (e) {
      print('❌ [DEBUG] Erro no teste de conexão: $e');
      throw Exception('Error connecting to server: $e');
    }
  }

  static Future<List<Map<String, dynamic>>> getHospitalsWithMapData() async {
    print('🏥 [DEBUG] getHospitalsWithMapData() iniciado');
    print('🏥 [DEBUG] URL: $baseUrl/hospitals');

    try {
      print('🏥 [DEBUG] Fazendo requisição HTTP...');
      final response = await http.get(
        Uri.parse('$baseUrl/hospitals'),
        headers: {'Content-Type': 'application/json'},
      );

      print('🏥 [DEBUG] Status code: ${response.statusCode}');
      print('🏥 [DEBUG] Response body length: ${response.body.length}');

      if (response.statusCode == 200) {
        print('🏥 [DEBUG] Parsing JSON response...');
        final List<dynamic> jsonList = jsonDecode(response.body);
        print(
          '🏥 [DEBUG] JSON parseado com sucesso. ${jsonList.length} hospitais encontrados',
        );

        final result = jsonList
            .map((jsonData) => _parseHospitalData(jsonData))
            .toList();

        print('✅ [DEBUG] getHospitalsWithMapData() concluído com sucesso');
        return result;
      } else {
        print('❌ [DEBUG] Falha na requisição. Status: ${response.statusCode}');
        print('❌ [DEBUG] Response body: ${response.body}');
        throw Exception('Failed to load hospitals: ${response.statusCode}');
      }
    } catch (e) {
      print('❌ [DEBUG] Erro em getHospitalsWithMapData(): $e');
      throw Exception('Error connecting to server: $e');
    }
  }

  static Future<Map<String, dynamic>> getHospitalDetails(
    String hospitalId,
  ) async {
    print('🏥 [DEBUG] getHospitalDetails() iniciado para ID: $hospitalId');
    print('🏥 [DEBUG] URL: $baseUrl/hospitals/$hospitalId');

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/hospitals/$hospitalId'),
        headers: {'Content-Type': 'application/json'},
      );

      print('🏥 [DEBUG] Status code: ${response.statusCode}');

      if (response.statusCode == 200) {
        final Map<String, dynamic> jsonData = jsonDecode(response.body);
        print('✅ [DEBUG] Detalhes do hospital carregados com sucesso');
        return _parseHospitalData(jsonData);
      } else if (response.statusCode == 404) {
        print('❌ [DEBUG] Hospital não encontrado');
        throw Exception('Hospital not found');
      } else {
        print(
          '❌ [DEBUG] Falha ao carregar detalhes. Status: ${response.statusCode}',
        );
        throw Exception(
          'Failed to load hospital details: ${response.statusCode}',
        );
      }
    } catch (e) {
      print('❌ [DEBUG] Erro em getHospitalDetails(): $e');
      throw Exception('Error connecting to server: $e');
    }
  }

  static Future<List<Map<String, dynamic>>> getHospitalsByStatus(
    String statusCode,
  ) async {
    print(
      '🏥 [DEBUG] getHospitalsByStatus() iniciado para status: $statusCode',
    );
    print('🏥 [DEBUG] URL: $baseUrl/hospitals/status/$statusCode');

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/hospitals/status/$statusCode'),
        headers: {'Content-Type': 'application/json'},
      );

      print('🏥 [DEBUG] Status code: ${response.statusCode}');

      if (response.statusCode == 200) {
        final List<dynamic> jsonList = jsonDecode(response.body);
        print(
          '🏥 [DEBUG] ${jsonList.length} hospitais encontrados para status: $statusCode',
        );

        final result = jsonList
            .map((jsonData) => _parseHospitalData(jsonData))
            .toList();

        print('✅ [DEBUG] getHospitalsByStatus() concluído com sucesso');
        return result;
      } else if (response.statusCode == 404) {
        print('❌ [DEBUG] Status não encontrado');
        throw Exception('Status not found');
      } else {
        print(
          '❌ [DEBUG] Falha ao carregar hospitais por status. Status: ${response.statusCode}',
        );
        throw Exception(
          'Failed to load hospitals by status: ${response.statusCode}',
        );
      }
    } catch (e) {
      print('❌ [DEBUG] Erro em getHospitalsByStatus(): $e');
      throw Exception('Error connecting to server: $e');
    }
  }

  static Future<bool> healthCheck() async {
    print('💚 [DEBUG] healthCheck() iniciado');
    print('💚 [DEBUG] URL: $baseUrl/health');

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/health'),
        headers: {'Content-Type': 'application/json'},
      );

      print('💚 [DEBUG] Status code: ${response.statusCode}');
      final isHealthy = response.statusCode == 200;
      print('💚 [DEBUG] Health check result: $isHealthy');

      return isHealthy;
    } catch (e) {
      print('❌ [DEBUG] Erro no health check: $e');
      return false;
    }
  }

  static Map<String, dynamic> convertToFlutterHospitalFormat(
    Map<String, dynamic> hospitalData,
  ) {
    print('🔄 [DEBUG] convertToFlutterHospitalFormat() iniciado');
    print('🔄 [DEBUG] Dados de entrada:');
    print('   Nome: ${hospitalData['name']}');
    print('   Lat: ${hospitalData['latitude']}');
    print('   Lng: ${hospitalData['longitude']}');
    print('   Status: ${hospitalData['status']}');

    final convertedData = {
      'id': hospitalData['id'],
      'nome': hospitalData['name'],
      'endereco': hospitalData['address'],
      'telefone': hospitalData['phone'],
      'status': _convertStatusToEnum(hospitalData['status']),
      'filaEmergencia': hospitalData['queueSize'] ?? 0,
      'tempoEspera':
          '${hospitalData['averageWaitTime']?.toStringAsFixed(0) ?? 0} min',
      'especialidades': _getSpecialtiesFromEscalation(
        hospitalData['escalation'],
      ),
      'lat': hospitalData['latitude'] ?? 0.0,
      'lng': hospitalData['longitude'] ?? 0.0,
      'capacidadeTotal': hospitalData['totalCapacity'] ?? 0,
      'capacidadeAtiva': hospitalData['activeCapacity'] ?? 0,
      'leitosOcupados': hospitalData['occupiedSeats'] ?? 0,
      'cuidadosMedicosAtivos': hospitalData['activeMedicalCares'] ?? 0,
    };

    print('🔄 [DEBUG] Dados convertidos:');
    print('   Nome: ${convertedData['nome']}');
    print('   Lat: ${convertedData['lat']}');
    print('   Lng: ${convertedData['lng']}');
    print('   Status: ${convertedData['status']}');
    print('   Fila: ${convertedData['filaEmergencia']}');

    return convertedData;
  }

  static String _convertStatusToEnum(String? status) {
    print('🎨 [DEBUG] _convertStatusToEnum() - Status original: "$status"');

    if (status == null) {
      print('🎨 [DEBUG] Status é null, retornando "moderada"');
      return 'moderada';
    }

    String result;
    switch (status.toLowerCase()) {
      case 'baixa':
      case 'low':
        result = 'baixa';
        break;
      case 'alta':
      case 'high':
        result = 'alta';
        break;
      default:
        result = 'moderada';
        break;
    }

    print('🎨 [DEBUG] Status convertido: "$status" -> "$result"');
    return result;
  }

  static List<String> _getSpecialtiesFromEscalation(String? escalation) {
    print(
      '🏥 [DEBUG] _getSpecialtiesFromEscalation() - Escalação: "$escalation"',
    );

    if (escalation == null) {
      print('🏥 [DEBUG] Escalação é null, retornando ["Emergência"]');
      return ['Emergência'];
    }

    List<String> specialties;
    switch (escalation.toLowerCase()) {
      case 'cardiology':
        specialties = ['Emergência', 'Cardiologia'];
        break;
      case 'pediatrics':
        specialties = ['Emergência', 'Pediatria'];
        break;
      case 'neurology':
        specialties = ['Emergência', 'Neurologia'];
        break;
      case 'orthopedics':
        specialties = ['Emergência', 'Ortopedia'];
        break;
      case 'oncology':
        specialties = ['Emergência', 'Oncologia'];
        break;
      case 'icu':
        specialties = ['Emergência', 'UTI'];
        break;
      default:
        specialties = ['Emergência', 'Clínica Geral'];
        break;
    }

    print('🏥 [DEBUG] Especialidades definidas: $specialties');
    return specialties;
  }
}
