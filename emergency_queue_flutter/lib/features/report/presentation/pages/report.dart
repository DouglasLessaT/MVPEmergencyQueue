import 'package:flutter/material.dart';
import '../../../../core/components/navbar.dart';
import '../../../../core/services/report_service.dart';

class ReportPage extends StatefulWidget {
  const ReportPage({super.key});

  @override
  State<ReportPage> createState() => _ReportPageState();
}

class _ReportPageState extends State<ReportPage> {
  int criticalCount = 0;
  int occupiedBedsCount = 0;
  int availableBedsCount = 0;
  int pendingTransfersCount = 0;
  List<Map<String, dynamic>> waitingPatients = [];
  List<Map<String, dynamic>> pendingTransfers = [];

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  Future<void> _loadData() async {
    try {
      final dashboard = await ReportService.fetchDashboardData();
      final patients = await ReportService.fetchWaitingPatients();
      final transfers = await ReportService.fetchPendingTransfers();

      setState(() {
        criticalCount = dashboard['criticalCount'] ?? 0;
        occupiedBedsCount = dashboard['occupiedBedsCount'] ?? 0;
        availableBedsCount = dashboard['availableBedsCount'] ?? 0;
        pendingTransfersCount = dashboard['pendingTransfersCount'] ?? 0;
        waitingPatients = List<Map<String, dynamic>>.from(patients);
        pendingTransfers = List<Map<String, dynamic>>.from(transfers);
      });
    } catch (e) {
      // Trate o erro conforme necessário
      print('Erro ao carregar dados: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          // Navbar
          const Navbar(currentRoute: '/reports'),

          // Content
          Expanded(
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Header
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      const Text(
                        'Relatórios',
                        style: TextStyle(
                          fontSize: 28,
                          fontWeight: FontWeight.bold,
                          color: Color(0xFF111827),
                        ),
                      ),
                      Row(
                        children: [
                          ElevatedButton.icon(
                            onPressed: () {},
                            icon: const Icon(Icons.add),
                            label: const Text('Nova Transferência'),
                            style: ElevatedButton.styleFrom(
                              backgroundColor: const Color(0xFF10B981),
                              foregroundColor: Colors.white,
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                  const SizedBox(height: 24),

                  // Dashboard Cards
                  GridView.count(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics(),
                    crossAxisCount: 4,
                    crossAxisSpacing: 16,
                    mainAxisSpacing: 16,
                    childAspectRatio: 1.2,
                    children: [
                      _buildDashboardCard(
                        'Pacientes em Estado Crítico',
                        criticalCount.toString(),
                        'Total de pacientes em estado crítico',
                        Icons.person,
                        const Color(0xFFFF3B30),
                        const Color(0xFFFF3B30),
                      ),
                      _buildDashboardCard(
                        'Leitos Ocupados',
                        '$occupiedBedsCount/20',
                        '${((occupiedBedsCount / 20) * 100).toStringAsFixed(1)}% de ocupação total',
                        Icons.local_hospital,
                        const Color(0xFFFF3B30),
                        const Color(0xFFFF3B30),
                      ),
                      _buildDashboardCard(
                        'Leitos Disponíveis',
                        availableBedsCount.toString(),
                        '${((availableBedsCount / 20) * 100).toStringAsFixed(1)}% do total de leitos',
                        Icons.local_hospital,
                        const Color(0xFF34C759),
                        const Color(0xFF34C759),
                      ),
                      _buildDashboardCard(
                        'Transferências Pendentes',
                        pendingTransfersCount.toString(),
                        'Transferências aguardando aprovação',
                        Icons.arrow_forward,
                        const Color(0xFFFF9500),
                        const Color(0xFFFF9500),
                      ),
                    ],
                  ),
                  const SizedBox(height: 24),

                  // Charts Section
                  Row(
                    children: [
                      Expanded(
                        child: _buildChartCard(
                          'Ocupação por Andar',
                          _buildBarChart(),
                        ),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: _buildChartCard(
                          'Status dos Pacientes',
                          _buildPieChart(),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 24),

                  // Second Row of Charts
                  Row(
                    children: [
                      Expanded(
                        child: _buildChartCard(
                          'Tendência de Pacientes (Mensal)',
                          _buildLineChart(),
                        ),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: _buildChartCard(
                          'Utilização de Capacidade por Hospital',
                          _buildAreaChart(),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 24),

                  // Waiting Patients and QR Code
                  Row(
                    children: [
                      Expanded(flex: 1, child: _buildQRCodeCard()),
                      const SizedBox(width: 16),
                      Expanded(flex: 2, child: _buildWaitingPatientsCard()),
                    ],
                  ),
                  const SizedBox(height: 24),

                  // Pending Transfers
                  _buildPendingTransfersSection(),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildDashboardCard(
    String title,
    String value,
    String description,
    IconData icon,
    Color iconColor,
    Color borderColor,
  ) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        border: Border(left: BorderSide(color: borderColor, width: 4)),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Icon(icon, color: iconColor, size: 20),
              const Spacer(),
              Icon(Icons.trending_up, color: iconColor, size: 16),
            ],
          ),
          const SizedBox(height: 16),
          Text(
            value,
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: iconColor,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            title,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.w600,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 4),
          Text(
            description,
            style: const TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
          ),
        ],
      ),
    );
  }

  Widget _buildChartCard(String title, Widget chart) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 16),
          SizedBox(height: 320, child: chart),
        ],
      ),
    );
  }

  Widget _buildBarChart() {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xFFF3F4F6),
        borderRadius: BorderRadius.circular(8),
      ),
      child: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.bar_chart, size: 48, color: Color(0xFF9CA3AF)),
            SizedBox(height: 8),
            Text(
              'Gráfico de Barras - Ocupação por Andar',
              style: TextStyle(color: Color(0xFF6B7280)),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildPieChart() {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xFFF3F4F6),
        borderRadius: BorderRadius.circular(8),
      ),
      child: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.pie_chart, size: 48, color: Color(0xFF9CA3AF)),
            SizedBox(height: 8),
            Text(
              'Gráfico de Pizza - Status dos Pacientes',
              style: TextStyle(color: Color(0xFF6B7280)),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildLineChart() {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xFFF3F4F6),
        borderRadius: BorderRadius.circular(8),
      ),
      child: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.show_chart, size: 48, color: Color(0xFF9CA3AF)),
            SizedBox(height: 8),
            Text(
              'Gráfico de Linha - Tendência Mensal',
              style: TextStyle(color: Color(0xFF6B7280)),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildAreaChart() {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xFFF3F4F6),
        borderRadius: BorderRadius.circular(8),
      ),
      child: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.area_chart, size: 48, color: Color(0xFF9CA3AF)),
            SizedBox(height: 8),
            Text(
              'Gráfico de Área - Utilização por Hospital',
              style: TextStyle(color: Color(0xFF6B7280)),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildQRCodeCard() {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        children: [
          const Text(
            'QR Code de Espera',
            style: TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 16),
          Container(
            width: 120,
            height: 120,
            decoration: BoxDecoration(
              color: const Color(0xFFF3F4F6),
              borderRadius: BorderRadius.circular(8),
            ),
            child: const Icon(
              Icons.qr_code,
              size: 80,
              color: Color(0xFF9CA3AF),
            ),
          ),
          const SizedBox(height: 16),
          const Text(
            'Escaneie para verificar sua posição na fila',
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
          ),
        ],
      ),
    );
  }

  Widget _buildWaitingPatientsCard() {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Pacientes Aguardando Atendimento',
            style: TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 16),
          if (waitingPatients.isNotEmpty)
            ...waitingPatients.map((patient) => _buildPatientCard(patient))
          else
            const Center(
              child: Padding(
                padding: EdgeInsets.all(20),
                child: Text(
                  'Nenhum paciente aguardando atendimento',
                  style: TextStyle(color: Color(0xFF6B7280), fontSize: 14),
                ),
              ),
            ),
        ],
      ),
    );
  }

  Widget _buildPatientCard(Map<String, dynamic> patient) {
    return Container(
      margin: const EdgeInsets.only(bottom: 12),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: const Color(0xFFF9FAFB),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: const Color(0xFFE5E7EB)),
      ),
      child: Row(
        children: [
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  patient['name'],
                  style: const TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w600,
                    color: Color(0xFF111827),
                  ),
                ),
                const SizedBox(height: 4),
                Text(
                  'Aguardando desde: ${_formatTime(patient['registrationTime'])}',
                  style: const TextStyle(
                    fontSize: 12,
                    color: Color(0xFF6B7280),
                  ),
                ),
              ],
            ),
          ),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
            decoration: BoxDecoration(
              color: const Color(0xFFFF9500).withOpacity(0.1),
              borderRadius: BorderRadius.circular(12),
            ),
            child: const Text(
              'Pendente',
              style: TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w600,
                color: Color(0xFFFF9500),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildPendingTransfersSection() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Transferências Pendentes',
          style: TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
            color: Color(0xFF111827),
          ),
        ),
        const SizedBox(height: 16),
        GridView.count(
          shrinkWrap: true,
          physics: const NeverScrollableScrollPhysics(),
          crossAxisCount: 3,
          crossAxisSpacing: 16,
          mainAxisSpacing: 16,
          childAspectRatio: 1.5,
          children: [
            _buildTransferCard(
              'Transferência #001',
              'Hospital A → Hospital B',
              'Pendente',
            ),
            _buildTransferCard(
              'Transferência #002',
              'Hospital C → Hospital D',
              'Pendente',
            ),
            _buildTransferCard(
              'Transferência #003',
              'Hospital B → Hospital A',
              'Pendente',
            ),
          ],
        ),
      ],
    );
  }

  Widget _buildTransferCard(String title, String description, String status) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.05),
            blurRadius: 10,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: const TextStyle(
              fontSize: 14,
              fontWeight: FontWeight.w600,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 8),
          Text(
            description,
            style: const TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
          ),
          const Spacer(),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
            decoration: BoxDecoration(
              color: const Color(0xFFFF9500).withOpacity(0.1),
              borderRadius: BorderRadius.circular(12),
            ),
            child: Text(
              status,
              style: const TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.w600,
                color: Color(0xFFFF9500),
              ),
            ),
          ),
        ],
      ),
    );
  }

  String _formatTime(DateTime time) {
    return '${time.hour.toString().padLeft(2, '0')}:${time.minute.toString().padLeft(2, '0')}';
  }
}
