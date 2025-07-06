class ReportService {
  static Future<Map<String, dynamic>> fetchDashboardData() async {
    // TODO: Implement API call to fetch dashboard data
    await Future.delayed(
      const Duration(milliseconds: 500),
    ); // Simulate API delay

    return {
      'criticalCount': 5,
      'occupiedBedsCount': 15,
      'availableBedsCount': 5,
      'pendingTransfersCount': 3,
    };
  }

  static Future<List<Map<String, dynamic>>> fetchWaitingPatients() async {
    // TODO: Implement API call to fetch waiting patients
    await Future.delayed(const Duration(milliseconds: 300));

    return [
      {
        'name': 'João Silva',
        'registrationTime': DateTime.now().subtract(const Duration(hours: 2)),
      },
      {
        'name': 'Maria Santos',
        'registrationTime': DateTime.now().subtract(const Duration(hours: 1)),
      },
    ];
  }

  static Future<List<Map<String, dynamic>>> fetchPendingTransfers() async {
    // TODO: Implement API call to fetch pending transfers
    await Future.delayed(const Duration(milliseconds: 300));

    return [
      {
        'id': '001',
        'from': 'Hospital A',
        'to': 'Hospital B',
        'status': 'Pendente',
      },
    ];
  }
}
