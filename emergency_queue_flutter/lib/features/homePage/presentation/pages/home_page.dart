import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../../../../core/config/app_config.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    final isMobile = MediaQuery.of(context).size.width < 600;
    return Scaffold(
      body: SingleChildScrollView(
          child: Column(
            children: [
              _buildHeader(context),

              _buildHeroSection(context, isMobile),

              _buildFeaturesSection(context, isMobile),

              // _buildStatsSection(context, isMobile),

              _buildCTASection(context, isMobile),

              _buildFooter(context),
            ],
          ),
      ),
    );
  }

  Widget _buildHeader(BuildContext context) {
    return Material(
      elevation: 2,
      child: Container(
        color: Colors.white,
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 7),
        child: SafeArea(
          child: LayoutBuilder(
            builder: (context, constraints) {
              final isMobile = constraints.maxWidth < 600;
              return Row(
                children: [
                  // Logo e título
                  Row(
                    children: [
                      Tooltip(
                        message: 'Página inicial',
                        child: Container(
                          padding: const EdgeInsets.all(8),
                          decoration: BoxDecoration(
                            color: const Color(0xFF2563EB),
                            borderRadius: BorderRadius.circular(8),
                          ),
                          child: const Icon(
                            Icons.local_hospital,
                            color: Colors.white,
                            size: 24,
                          ),
                        ),
                      ),
                      const SizedBox(width: 12),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'EmergencyQueue',
                            style: TextStyle(
                              fontSize: 20,
                              fontWeight: FontWeight.bold,
                              color: Color(0xFF111827),
                            ),
                          ),
                          const Text(
                            'Filas Hospitalares ES',
                            style: TextStyle(
                              fontSize: 12,
                              color: Color(0xFF6B7280),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                  const Spacer(),
                  // Navegação
                  if (!isMobile)
                    Row(
                      children: [
                        TextButton(
                          onPressed: () => context.go('/map'),
                          style: ButtonStyle(
                            overlayColor: MaterialStateProperty.all(
                              Colors.blue.withOpacity(0.08),
                            ),
                          ),
                          child: const Text(
                            'Mapa Público',
                            style: TextStyle(
                              color: Color(0xFF2563EB),
                              fontSize: 14,
                              fontWeight: FontWeight.w500,
                            ),
                          ),
                        ),
                        const SizedBox(width: 12),
                        OutlinedButton(
                          onPressed: () => context.go('/login'),
                          style: OutlinedButton.styleFrom(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 20,
                              vertical: 12,
                            ),
                            side: const BorderSide(color: Color(0xFF2563EB)),
                            foregroundColor: const Color(0xFF2563EB),
                            textStyle: const TextStyle(
                              fontWeight: FontWeight.w600,
                            ),
                          ),
                          child: const Text(
                            'Login',
                            style: TextStyle(fontSize: 14),
                          ),
                        ),
                      ],
                    ),
                  if (isMobile)
                    PopupMenuButton<String>(
                      icon: const Icon(Icons.menu, color: Color(0xFF2563EB)),
                      onSelected: (value) {
                        if (value == 'map') context.go('/map');
                        if (value == 'login') context.go('/login');
                      },
                      itemBuilder: (context) => [
                        const PopupMenuItem(
                          value: 'map',
                          child: Text('Mapa Público'),
                        ),
                        const PopupMenuItem(
                          value: 'login',
                          child: Text('Login'),
                        ),
                      ],
                    ),
                ],
              );
            },
          ),
        ),
      ),
    );
  }

  Widget _buildHeroSection(BuildContext context, bool isMobile) {
    return Container(
      padding: EdgeInsets.all(isMobile ? 8 : 16),
      child: Column(
        children: [
          SizedBox(height: isMobile ? 24 : 40),
          isMobile
              ? Column(
                  children: [
                    _buildHeroText(context, isMobile),
                    const SizedBox(height: 24),
                    _buildHeroStatusCard(isMobile),
                  ],
                )
              : Row(
                  children: [
                    Expanded(child: _buildHeroText(context, isMobile)),
                    const SizedBox(width: 24),
                    _buildHeroStatusCard(isMobile),
                  ],
                ),
        ],
      ),
    );
  }

  Widget _buildHeroText(BuildContext context, bool isMobile) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          'Transparência as\nFilas Hospitalares\ndo Espírito Santo',
          style: TextStyle(
            fontSize: isMobile ? 22 : 32,
            fontWeight: FontWeight.bold,
            color: const Color(0xFF111827),
            height: 1.2,
          ),
        ),
        SizedBox(height: isMobile ? 8 : 16),
        Text(
          'Sistema integrado para acompanhamento em tempo real das filas de emergência '
          'dos hospitais de Vila Velha, proporcionando transparência e melhor experiência '
          'para pacientes e familiares.',
          style: TextStyle(
            fontSize: isMobile ? 14 : 16,
            color: const Color(0xFF6B7280),
            height: 1.5,
          ),
        ),
        SizedBox(height: isMobile ? 16 : 24),
        isMobile
            ? Column(
                children: [
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton.icon(
                      onPressed: () => context.go('/map'),
                      icon: const Icon(Icons.map, size: 20),
                      label: const Text('Ver Mapa Público'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF2563EB),
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 16),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(height: 12),
                  SizedBox(
                    width: double.infinity,
                    child: OutlinedButton.icon(
                      onPressed: () => context.go('/login'),
                      icon: const Icon(Icons.shield, size: 20),
                      label: const Text('Acesso Hospitalar'),
                      style: OutlinedButton.styleFrom(
                        padding: const EdgeInsets.symmetric(vertical: 16),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                  ),
                ],
              )
            : Row(
                children: [
                  Expanded(
                    child: ElevatedButton.icon(
                      onPressed: () => context.go('/map'),
                      icon: const Icon(Icons.map, size: 20),
                      label: const Text('Ver Mapa Público'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF2563EB),
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.symmetric(vertical: 16),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 12),
                  Expanded(
                    child: OutlinedButton.icon(
                      onPressed: () => context.go('/login'),
                      icon: const Icon(Icons.shield, size: 20),
                      label: const Text('Acesso Hospitalar'),
                      style: OutlinedButton.styleFrom(
                        padding: const EdgeInsets.symmetric(vertical: 16),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
      ],
    );
  }

  Widget _buildHeroStatusCard(bool isMobile) {
    return Container(
      width: isMobile ? double.infinity : 200,
      margin: isMobile ? const EdgeInsets.only(top: 16) : null,
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        gradient: const LinearGradient(
          colors: [Color(0xFF3B82F6), Color(0xFF10B981)],
        ),
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 10,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              const Icon(
                Icons.local_hospital,
                color: Colors.white,
                size: 32,
              ),
              Container(
                padding: const EdgeInsets.symmetric(
                  horizontal: 8,
                  vertical: 4,
                ),
                decoration: BoxDecoration(
                  color: Colors.white.withOpacity(0.2),
                  borderRadius: BorderRadius.circular(12),
                ),
                child: const Text(
                  'Em Tempo Real',
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 12,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(height: 16),
          _buildHospitalStatus('Hospital Santa Casa', 'Moderada', Colors.orange),
          const SizedBox(height: 8),
          _buildHospitalStatus('Hospital Evangélico', 'Baixa', Colors.green),
          const SizedBox(height: 8),
          _buildHospitalStatus('Hospital Meridional', 'Alta', Colors.red),
        ],
      ),
    );
  }

  Widget _buildHospitalStatus(String name, String status, Color statusColor) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Expanded(
          child: Text(
            name,
            style: const TextStyle(color: Colors.white, fontSize: 14),
          ),
        ),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
          decoration: BoxDecoration(
            color: statusColor,
            borderRadius: BorderRadius.circular(4),
          ),
          child: Text(
            status,
            style: const TextStyle(
              color: Colors.white,
              fontSize: 12,
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildFeaturesSection(BuildContext context, bool isMobile) {
    final features = [
      {
        'icon': Icons.map,
        'title': 'Mapa em Tempo Real',
        'description':
            'Visualize a localização e status das filas de todos os hospitais conectados.',
      },
      {
        'icon': Icons.notifications,
        'title': 'Notificações',
        'description':
            'Receba alertas sobre mudanças no status das filas e tempo de espera.',
      },
      {
        'icon': Icons.analytics,
        'title': 'Relatórios',
        'description':
            'Acesse estatísticas detalhadas sobre o fluxo de pacientes e eficiência.',
      },
      {
        'icon': Icons.security,
        'title': 'Segurança',
        'description':
            'Sistema seguro com autenticação e controle de acesso para hospitais.',
      },
    ];

    return Container(
      color: Colors.white,
      padding: EdgeInsets.all(isMobile ? 8 : 16),
      child: Column(
        children: [
          SizedBox(height: isMobile ? 24 : 40),
          const Text(
            'Funcionalidades Principais',
            style: TextStyle(
              fontSize: 28,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16),
          const Text(
            'Nossa plataforma oferece uma solução completa para monitoramento e gestão '
            'das filas hospitalares em tempo real.',
            style: TextStyle(fontSize: 16, color: Color(0xFF6B7280)),
            textAlign: TextAlign.center,
          ),
          SizedBox(height: isMobile ? 16 : 32),
          GridView.builder(
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: isMobile ? 1 : 2,
              crossAxisSpacing: 16,
              mainAxisSpacing: 16,
              childAspectRatio: isMobile ? 1.5 : 0.8,
            ),
            itemCount: features.length,
            itemBuilder: (context, index) {
              final feature = features[index];
              return Card(
                elevation: 4,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Container(
                        padding: const EdgeInsets.all(16),
                        decoration: BoxDecoration(
                          color: const Color(0xFF2563EB).withOpacity(0.1),
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Icon(
                          feature['icon'] as IconData,
                          size: 32,
                          color: const Color(0xFF2563EB),
                        ),
                      ),
                      const SizedBox(height: 16),
                      Text(
                        feature['title'] as String,
                        style: const TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: Color(0xFF111827),
                        ),
                        textAlign: TextAlign.center,
                      ),
                      const SizedBox(height: 8),
                      Text(
                        feature['description'] as String,
                        style: const TextStyle(
                          fontSize: 14,
                          color: Color(0xFF6B7280),
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ],
                  ),
                ),
              );
            },
          ),
        ],
      ),
    );
  }

  Widget _buildStatsSection(BuildContext context, bool isMobile) {
    return Container(
      decoration: const BoxDecoration(
        gradient: LinearGradient(
          colors: [Color(0xFF2563EB), Color(0xFF10B981)],
        ),
      ),
      padding: EdgeInsets.all(isMobile ? 8 : 16),
      child: Column(
        children: [
          SizedBox(height: isMobile ? 24 : 40),
          isMobile
              ? Column(
                  children: [
                    _buildStatItem(Icons.local_hospital, '15+', 'Hospitais Conectados', isMobile),
                    const SizedBox(height: 16),
                    _buildStatItem(Icons.people, '50k+', 'Consultas Mensais', isMobile),
                    const SizedBox(height: 16),
                    _buildStatItem(Icons.favorite, '24/7', 'Monitoramento', isMobile),
                  ],
                )
              : Row(
                  children: [
                    Expanded(
                      child: _buildStatItem(Icons.local_hospital, '15+', 'Hospitais Conectados', isMobile),
                    ),
                    Expanded(
                      child: _buildStatItem(Icons.people, '50k+', 'Consultas Mensais', isMobile),
                    ),
                    Expanded(
                      child: _buildStatItem(Icons.favorite, '24/7', 'Monitoramento', isMobile),
                    ),
                  ],
                ),
          SizedBox(height: isMobile ? 24 : 40),
        ],
      ),
    );
  }

  Widget _buildStatItem(IconData icon, String value, String label, bool isMobile) {
    return Column(
      children: [
        Icon(icon, color: Colors.white, size: isMobile ? 36 : 48),
        SizedBox(height: isMobile ? 8 : 16),
        Text(
          value,
          style: TextStyle(
            fontSize: isMobile ? 22 : 32,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
        SizedBox(height: isMobile ? 4 : 8),
        Text(
          label,
          style: TextStyle(
            fontSize: isMobile ? 13 : 16,
            color: Colors.white,
            fontWeight: FontWeight.w500,
          ),
          textAlign: TextAlign.center,
        ),
      ],
    );
  }

  Widget _buildCTASection(BuildContext context, bool isMobile) {
    return Container(
      color: const Color(0xFFF9FAFB),
      padding: EdgeInsets.all(isMobile ? 8 : 16),
      child: Column(
        children: [
          SizedBox(height: isMobile ? 24 : 40),
          const Text(
            'Pronto para Começar?',
            style: TextStyle(
              fontSize: 28,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16),
          const Text(
            'Acesse o mapa público para visualizar as filas em tempo real ou faça login '
            'para acessar o dashboard do seu hospital.',
            style: TextStyle(fontSize: 16, color: Color(0xFF6B7280)),
            textAlign: TextAlign.center,
          ),
          SizedBox(height: isMobile ? 16 : 24),
          isMobile
              ? Column(
                  children: [
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton.icon(
                        onPressed: () => context.go('/map'),
                        icon: const Icon(Icons.map, size: 20),
                        label: const Text('Acessar Mapa Público'),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xFF2563EB),
                          foregroundColor: Colors.white,
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(height: 12),
                    SizedBox(
                      width: double.infinity,
                      child: OutlinedButton.icon(
                        onPressed: () => context.go('/login'),
                        icon: const Icon(Icons.shield, size: 20),
                        label: const Text('Login Hospitalar'),
                        style: OutlinedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ),
                  ],
                )
              : Row(
                  children: [
                    Expanded(
                      child: ElevatedButton.icon(
                        onPressed: () => context.go('/map'),
                        icon: const Icon(Icons.map, size: 20),
                        label: const Text('Acessar Mapa Público'),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: const Color(0xFF2563EB),
                          foregroundColor: Colors.white,
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 12),
                    Expanded(
                      child: OutlinedButton.icon(
                        onPressed: () => context.go('/login'),
                        icon: const Icon(Icons.shield, size: 20),
                        label: const Text('Login Hospitalar'),
                        style: OutlinedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(vertical: 16),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
          SizedBox(height: isMobile ? 24 : 40),
        ],
      ),
    );
  }

  Widget _buildFooter(BuildContext context) {
    return Container(
      color: const Color(0xFF111827),
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [
          const SizedBox(height: 40),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: const Color(0xFF2563EB),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: const Icon(
                  Icons.local_hospital,
                  color: Colors.white,
                  size: 24,
                ),
              ),
              const SizedBox(width: 12),
              const Text(
                'EmergencyQueue',
                style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
            ],
          ),
          const SizedBox(height: 16),
          const Text(
            'Sistema de Monitoramento de Filas Hospitalares - Espírito Santo',
            style: TextStyle(color: Color(0xFF9CA3AF), fontSize: 14),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 8),
          const Text(
            '© 2024 EmergencyQueue. Todos os direitos reservados.',
            style: TextStyle(color: Color(0xFF6B7280), fontSize: 12),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 40),
        ],
      ),
    );
  }
}
