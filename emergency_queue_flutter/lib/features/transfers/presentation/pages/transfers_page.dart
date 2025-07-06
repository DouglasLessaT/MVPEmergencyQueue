import 'package:flutter/material.dart';
import '../../../../core/components/navbar.dart';
import '../widgets/transfer_details_dialog.dart';

class TransfersPage extends StatefulWidget {
  const TransfersPage({super.key});

  @override
  State<TransfersPage> createState() => _TransfersPageState();
}

class _TransfersPageState extends State<TransfersPage>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;
  Map<String, dynamic>? selectedTransfer;
  bool isDialogOpen = false;
  bool isTransferFormOpen = false;

  // Mock data
  final List<Map<String, dynamic>> mockTransfers = [
    {
      'id': '1',
      'patientId': '1',
      'fromHospitalId': '1',
      'toHospitalId': '2',
      'status': 'Pending',
      'requestDate': DateTime.now().subtract(const Duration(days: 2)),
      'completionDate': null,
    },
    {
      'id': '2',
      'patientId': '2',
      'fromHospitalId': '2',
      'toHospitalId': '1',
      'status': 'In Progress',
      'requestDate': DateTime.now().subtract(const Duration(days: 1)),
      'completionDate': null,
    },
    {
      'id': '3',
      'patientId': '3',
      'fromHospitalId': '1',
      'toHospitalId': '3',
      'status': 'Completed',
      'requestDate': DateTime.now().subtract(const Duration(days: 5)),
      'completionDate': DateTime.now().subtract(const Duration(days: 3)),
    },
    {
      'id': '4',
      'patientId': '4',
      'fromHospitalId': '3',
      'toHospitalId': '1',
      'status': 'Cancelled',
      'requestDate': DateTime.now().subtract(const Duration(days: 3)),
      'completionDate': null,
    },
  ];

  final List<Map<String, dynamic>> mockPatients = [
    {'id': '1', 'name': 'João Silva', 'diagnosis': 'Fratura no braço'},
    {'id': '2', 'name': 'Maria Santos', 'diagnosis': 'Dor no peito'},
    {'id': '3', 'name': 'Pedro Costa', 'diagnosis': 'Apendicite'},
    {'id': '4', 'name': 'Ana Oliveira', 'diagnosis': 'Trauma craniano'},
  ];

  final List<Map<String, dynamic>> mockHospitals = [
    {'id': '1', 'name': 'Hospital Santa Casa', 'address': 'Rua A, 123'},
    {'id': '2', 'name': 'Hospital Evangélico', 'address': 'Rua B, 456'},
    {'id': '3', 'name': 'Hospital Meridional', 'address': 'Rua C, 789'},
  ];

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 4, vsync: this);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  List<Map<String, dynamic>> getTransfersByStatus(String status) {
    return mockTransfers
        .where((transfer) => transfer['status'] == status)
        .toList();
  }

  Map<String, dynamic>? getPatientById(String id) {
    try {
      return mockPatients.firstWhere((patient) => patient['id'] == id);
    } catch (e) {
      return null;
    }
  }

  Map<String, dynamic>? getHospitalById(String id) {
    try {
      return mockHospitals.firstWhere((hospital) => hospital['id'] == id);
    } catch (e) {
      return null;
    }
  }

  void handleViewTransfer(Map<String, dynamic> transfer) {
    setState(() {
      selectedTransfer = transfer;
      isDialogOpen = true;
    });
  }

  void handleApproveTransfer() {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('Transferência aprovada e está em andamento.'),
        backgroundColor: Colors.green,
      ),
    );
    setState(() {
      isDialogOpen = false;
    });
  }

  void handleRejectTransfer() {
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(
        content: Text('Transferência cancelada com sucesso.'),
        backgroundColor: Colors.red,
      ),
    );
    setState(() {
      isDialogOpen = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    final pendingTransfers = getTransfersByStatus('Pending');
    final inProgressTransfers = getTransfersByStatus('In Progress');
    final completedTransfers = getTransfersByStatus('Completed');
    final cancelledTransfers = getTransfersByStatus('Cancelled');

    return Scaffold(
      body: Stack(
        children: [
          Column(
            children: [
              // Navbar
              const Navbar(currentRoute: '/transfers'),

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
                            'Transferências',
                            style: TextStyle(
                              fontSize: 28,
                              fontWeight: FontWeight.bold,
                              color: Color(0xFF111827),
                            ),
                          ),
                          ElevatedButton.icon(
                            onPressed: () {
                              setState(() {
                                isTransferFormOpen = true;
                              });
                            },
                            icon: const Icon(Icons.add),
                            label: const Text('Nova Transferência'),
                            style: ElevatedButton.styleFrom(
                              backgroundColor: const Color(0xFF2563EB),
                              foregroundColor: Colors.white,
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 24),

                      // Stats Cards
                      GridView.count(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        crossAxisCount: 4,
                        crossAxisSpacing: 16,
                        mainAxisSpacing: 16,
                        childAspectRatio: 1.5,
                        children: [
                          _buildStatCard(
                            'Pendentes',
                            pendingTransfers.length.toString(),
                            const Color(0xFFFF9500),
                            const Color(0xFFFFF3CD),
                          ),
                          _buildStatCard(
                            'Em Andamento',
                            inProgressTransfers.length.toString(),
                            const Color(0xFF2563EB),
                            const Color(0xFFDBEAFE),
                          ),
                          _buildStatCard(
                            'Concluídas',
                            completedTransfers.length.toString(),
                            const Color(0xFF10B981),
                            const Color(0xFFD1FAE5),
                          ),
                          _buildStatCard(
                            'Canceladas',
                            cancelledTransfers.length.toString(),
                            const Color(0xFF6B7280),
                            const Color(0xFFF3F4F6),
                          ),
                        ],
                      ),
                      const SizedBox(height: 24),

                      // Tabs
                      Container(
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
                            Container(
                              padding: const EdgeInsets.all(16),
                              child: TabBar(
                                controller: _tabController,
                                labelColor: const Color(0xFF2563EB),
                                unselectedLabelColor: const Color(0xFF6B7280),
                                indicatorColor: const Color(0xFF2563EB),
                                tabs: const [
                                  Tab(text: 'Pendentes'),
                                  Tab(text: 'Em Andamento'),
                                  Tab(text: 'Concluídas'),
                                  Tab(text: 'Canceladas'),
                                ],
                              ),
                            ),
                            SizedBox(
                              height: 400,
                              child: TabBarView(
                                controller: _tabController,
                                children: [
                                  _buildTransfersList(
                                    pendingTransfers,
                                    'Não há transferências pendentes',
                                  ),
                                  _buildTransfersList(
                                    inProgressTransfers,
                                    'Não há transferências em andamento',
                                  ),
                                  _buildTransfersList(
                                    completedTransfers,
                                    'Não há transferências concluídas',
                                  ),
                                  _buildTransfersList(
                                    cancelledTransfers,
                                    'Não há transferências canceladas',
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),

          // Dialog overlay
          if (isDialogOpen && selectedTransfer != null)
            Container(
              color: Colors.black.withOpacity(0.5),
              child: Center(
                child: TransferDetailsDialog(
                  transfer: selectedTransfer!,
                  patient: getPatientById(selectedTransfer!['patientId']),
                  fromHospital: getHospitalById(
                    selectedTransfer!['fromHospitalId'],
                  ),
                  toHospital: getHospitalById(
                    selectedTransfer!['toHospitalId'],
                  ),
                  onApprove: handleApproveTransfer,
                  onReject: handleRejectTransfer,
                ),
              ),
            ),
        ],
      ),
    );
  }

  Widget _buildStatCard(
    String title,
    String value,
    Color color,
    Color backgroundColor,
  ) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: backgroundColor,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w600,
              color: color,
            ),
          ),
          const SizedBox(height: 8),
          Text(
            value,
            style: TextStyle(
              fontSize: 32,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTransfersList(
    List<Map<String, dynamic>> transfers,
    String emptyMessage,
  ) {
    if (transfers.isEmpty) {
      return Container(
        padding: const EdgeInsets.all(32),
        child: Center(
          child: Text(
            emptyMessage,
            style: const TextStyle(color: Color(0xFF6B7280), fontSize: 16),
          ),
        ),
      );
    }

    return GridView.builder(
      padding: const EdgeInsets.all(16),
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 3,
        crossAxisSpacing: 16,
        mainAxisSpacing: 16,
        childAspectRatio: 1.2,
      ),
      itemCount: transfers.length,
      itemBuilder: (context, index) {
        return _buildTransferCard(transfers[index]);
      },
    );
  }

  Widget _buildTransferCard(Map<String, dynamic> transfer) {
    final patient = getPatientById(transfer['patientId']);
    final fromHospital = getHospitalById(transfer['fromHospitalId']);
    final toHospital = getHospitalById(transfer['toHospitalId']);

    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: const Color(0xFFE5E7EB)),
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
              Expanded(
                child: Text(
                  'Transferência #${transfer['id']}',
                  style: const TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w600,
                    color: Color(0xFF111827),
                  ),
                ),
              ),
              _buildStatusBadge(transfer['status']),
            ],
          ),
          const SizedBox(height: 12),
          if (patient != null)
            Text(
              'Paciente: ${patient['name']}',
              style: const TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
            ),
          const SizedBox(height: 8),
          if (fromHospital != null && toHospital != null)
            Text(
              '${fromHospital['name']} → ${toHospital['name']}',
              style: const TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
            ),
          const Spacer(),
          ElevatedButton(
            onPressed: () => handleViewTransfer(transfer),
            style: ElevatedButton.styleFrom(
              backgroundColor: const Color(0xFF2563EB),
              foregroundColor: Colors.white,
              minimumSize: const Size(double.infinity, 36),
            ),
            child: const Text('Ver Detalhes'),
          ),
        ],
      ),
    );
  }

  Widget _buildStatusBadge(String status) {
    Color color;
    Color backgroundColor;
    String text;

    switch (status) {
      case 'Pending':
        color = const Color(0xFFFF9500);
        backgroundColor = const Color(0xFFFFF3CD);
        text = 'Pendente';
        break;
      case 'In Progress':
        color = const Color(0xFF2563EB);
        backgroundColor = const Color(0xFFDBEAFE);
        text = 'Em Andamento';
        break;
      case 'Completed':
        color = const Color(0xFF10B981);
        backgroundColor = const Color(0xFFD1FAE5);
        text = 'Concluída';
        break;
      case 'Cancelled':
        color = const Color(0xFF6B7280);
        backgroundColor = const Color(0xFFF3F4F6);
        text = 'Cancelada';
        break;
      default:
        color = const Color(0xFF6B7280);
        backgroundColor = const Color(0xFFF3F4F6);
        text = 'Desconhecido';
    }

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: backgroundColor,
        borderRadius: BorderRadius.circular(12),
      ),
      child: Text(
        text,
        style: TextStyle(
          fontSize: 12,
          fontWeight: FontWeight.w600,
          color: color,
        ),
      ),
    );
  }
}
