import 'dart:convert';
import 'package:http/http.dart' as http;

class IntegradorService {
  static const String baseUrl = 'http://localhost:8080/api';

  static Future<List<Map<String, dynamic>>> getScripts() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/integrador-golang/scripts'),
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        return data.cast<Map<String, dynamic>>();
      } else {
        throw Exception('Falha ao carregar scripts');
      }
    } catch (e) {
      throw Exception('Erro de conexão: $e');
    }
  }

  static Future<Map<String, dynamic>> getStatus() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/integrador-golang/status'),
      );

      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Falha ao carregar status');
      }
    } catch (e) {
      throw Exception('Erro de conexão: $e');
    }
  }

  static Future<Map<String, dynamic>> executeScript(String scriptName) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/integrador-golang/execute/$scriptName'),
      );

      if (response.statusCode == 200) {
        return json.decode(response.body);
      } else {
        throw Exception('Falha ao executar script');
      }
    } catch (e) {
      throw Exception('Erro de conexão: $e');
    }
  }
}
