import 'package:emergency_queue_flutter/core/models/hospital.dart';

class Hospital {
  final String id; // UUID
  final String name;
  final String cnpj;
  final String phone;
  final String email;
  final String settings;
  final String erpSystem;
  final String erpEndpoint;
  final String erpCredentials;
  final String goApiPath;
  final String goApiPort;
  final bool active;
  final DateTime createdAt;

  Hospital({
    required this.id,
    required this.name,
    required this.cnpj,
    required this.phone,
    required this.email,
    required this.settings,
    required this.erpSystem,
    required this.erpEndpoint,
    required this.erpCredentials,
    required this.goApiPath,
    required this.goApiPort,
    required this.active,
    required this.createdAt,
  });

  factory Hospital.fromJson(Map<String, dynamic> json) {
    return Hospital(
      id: json['id'],
      name: json['name'],
      cnpj: json['cnpj'],
      phone: json['phone'] ?? '',
      email: json['email'] ?? '',
      settings: json['settings'] ?? '{}',
      erpSystem: json['erpSystem'] ?? '',
      erpEndpoint: json['erpEndpoint'] ?? '',
      erpCredentials: json['erpCredentials'] ?? '',
      goApiPath: json['goApiPath'] ?? '',
      goApiPort: json['goApiPort'] ?? '',
      active: json['active'] ?? false,
      createdAt: DateTime.parse(json['createdAt']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'cnpj': cnpj,
      'phone': phone,
      'email': email,
      'settings': settings,
      'erpSystem': erpSystem,
      'erpEndpoint': erpEndpoint,
      'erpCredentials': erpCredentials,
      'goApiPath': goApiPath,
      'goApiPort': goApiPort,
      'active': active,
      'createdAt': createdAt.toIso8601String(),
    };
  }
}

class HospitalService {
  static Future<List<Hospital>> getHospitals() async => [];
  static Future<Hospital?> getHospital(String id) async => null;
}

class IntegradorService {
  static Future<Map<String, dynamic>> getStatus(String id) async => {};
  static Future<List<Map<String, dynamic>>> getScripts(String id) async => [];
}

bool get isSuperAdmin => true; // ou lógica real
