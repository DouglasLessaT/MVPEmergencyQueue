import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'core/theme/app_theme.dart';
import 'features/homePage/presentation/pages/home_page.dart';
import 'features/login/presentation/pages/login_page_with_api.dart';
import 'features/dashboard/presentation/pages/dashboard_page_demo.dart';
import 'features/admin/presentation/pages/admin_page_demo.dart';
import 'features/map/presentation/pages/map_page_with_api.dart';
import 'features/report/presentation/pages/report.dart';
import 'features/transfers/presentation/pages/transfers_page.dart';
import 'features/settings/presentation/pages/setings_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'Emergency Queue Flutter',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFF2563EB)),
        useMaterial3: true,
      ),
      routerConfig: _router,
      debugShowCheckedModeBanner: false,
    );
  }
}

final GoRouter _router = GoRouter(
  initialLocation: '/',
  routes: [
    GoRoute(path: '/', builder: (context, state) => const HomePage()),
    GoRoute(path: '/login', builder: (context, state) => const LoginPage()),
    GoRoute(
      path: '/dashboard',
      builder: (context, state) => const DashboardPage(),
    ),
    GoRoute(path: '/admin', builder: (context, state) => const AdminPage()),
    GoRoute(path: '/map', builder: (context, state) => const MapPageWithAPI()),
    GoRoute(path: '/reports', builder: (context, state) => const ReportPage()),
    GoRoute(
      path: '/transfers',
      builder: (context, state) => const TransfersPage(),
    ),
    GoRoute(
      path: '/settings',
      builder: (context, state) => const SettingsPage(),
    ),
  ],
);
