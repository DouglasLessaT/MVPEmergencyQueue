import 'package:flutter/material.dart';
import '../screens/home/home_page.dart';
import '../screens/login/login_page.dart';
import '../screens/dashboard/dashboard_page.dart';
import '../screens/patients/patients_page.dart';
import '../screens/bad/bad_page.dart';
import '../screens/transfers/transfers_page.dart';
import '../screens/hospital/hospital_page.dart';
import '../screens/medical_attention/medical_attention_page.dart';

class AppRoutes {
  static const String home = '/home';
  static const String login = '/login';
  static const String dashboard = '/dashboard';
  static const String patients = '/patients';
  static const String bad = '/bad';
  static const String transfers = '/transfers';
  static const String hospital = '/hospital';
  static const String medicalAttention = '/medical_attention';

  static Map<String, WidgetBuilder> routes = {
    home: (context) => const HomePage(),
    login: (context) => const LoginPage(),
    // dashboard: (context) => const DashboardPage(),
    // patients: (context) => const PatientsPage(),
    // bad: (context) => const BadPage(),
    // transfers: (context) => const TransfersPage(),
    // hospital: (context) => const HospitalPage(),
    // medicalAttention: (context) => const MedicalAttentionPage(),
  };
}
