import 'package:flutter/material.dart';

class AppConfig {
  static const String appName = 'Vila Velha Emergência';
  static const String appVersion = '1.0.0';

  // API Configuration
  static const String baseUrl = 'https://api.vilavelhaemergencia.com.br';
  static const String apiVersion = '/v1';

  // Map Configuration - OpenStreetMap
  static const double defaultLatitude = -20.3297; // Vila Velha, ES
  static const double defaultLongitude = -40.2925;
  static const double defaultZoom = 12.0;

  // Hospital Data
  static const List<Map<String, dynamic>> hospitals = [
    {
      'id': '1',
      'name': 'Hospital Santa Casa de Misericórdia',
      'address': 'Rua Dr. João Carlos, 100 - Centro, Vila Velha',
      'phone': '(27) 3149-1234',
      'latitude': -20.3297,
      'longitude': -40.2925,
      'queue': 15,
      'waitTime': 45,
    },
    {
      'id': '2',
      'name': 'Hospital Evangélico de Vila Velha',
      'address': 'Av. Luciano das Neves, 2000 - Praia da Costa',
      'phone': '(27) 3149-5678',
      'latitude': -20.3250,
      'longitude': -40.2900,
      'queue': 8,
      'waitTime': 25,
    },
    {
      'id': '3',
      'name': 'Hospital São Camilo',
      'address': 'Rua Henrique Moscoso, 500 - Centro',
      'phone': '(27) 3149-9012',
      'latitude': -20.3300,
      'longitude': -40.2950,
      'queue': 22,
      'waitTime': 60,
    },
  ];

  // Colors
  static const int primaryColor = 0xFF2563EB;
  static const int secondaryColor = 0xFF64748B;
  static const int successColor = 0xFF10B981;
  static const int warningColor = 0xFFF59E0B;
  static const int errorColor = 0xFFEF4444;

  // Queue Status Colors
  static const int lowQueueColor = 0xFF10B981; // Green
  static const int mediumQueueColor = 0xFFF59E0B; // Yellow
  static const int highQueueColor = 0xFFEF4444; // Red

  // Firebase Configuration
  static const String firebaseApiKey = String.fromEnvironment(
    'FIREBASE_API_KEY',
    defaultValue: '',
  );

  // Theme Configuration
  static const Color primaryColorObj = Color(0xFF2563EB);
  static const Color secondaryColorObj = Color(0xFF3B82F6);
  static const Color accentColor = Color(0xFF60A5FA);
  static const Color errorColorObj = Color(0xFFEF4444);
  static const Color successColorObj = Color(0xFF22C55E);
  static const Color warningColorObj = Color(0xFFF59E0B);

  // Cache Configuration
  static const Duration cacheDuration = Duration(hours: 1);
  static const int maxCacheSize = 100;

  // Animation Durations
  static const Duration shortAnimation = Duration(milliseconds: 200);
  static const Duration mediumAnimation = Duration(milliseconds: 350);
  static const Duration longAnimation = Duration(milliseconds: 500);

  // Other Constants
  static const int maxLoginAttempts = 3;
  static const Duration sessionTimeout = Duration(hours: 24);
  static const int maxFileSize = 5 * 1024 * 1024; // 5MB
}
