import 'package:flutter/material.dart';
import 'presentation/routes/routes.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'EmergencyBed Manager',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      initialRoute: AppRoutes.login, // Define a rota inicial
      routes: AppRoutes.routes, // Usa as rotas definidas no routes.dart
    );
  }
}
