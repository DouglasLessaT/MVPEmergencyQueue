import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:flutter_osm_plugin/flutter_osm_plugin.dart';

class MapPage extends StatefulWidget {
  const MapPage({super.key});

  @override
  State<MapPage> createState() => _MapPageState();
}

class _MapPageState extends State<MapPage> {
  MapController? _mapController;
  Hospital? _selectedHospital;

  // Hospital data
  final List<Hospital> _hospitais = [
    Hospital(
      id: 1,
      nome: "Hospital Estadual Antônio Bezerra de Faria",
      endereco: "R. Liberalino Lima, s/n - Olaria, Vila Velha - ES, 29123-180",
      telefone: "(27) 3636-3514",
      status: HospitalStatus.moderada,
      filaEmergencia: 15,
      tempoEspera: "45 min",
      especialidades: ["Emergência", "Cardiologia", "Ortopedia"],
      lat: -20.3328,
      lng: -40.2990,
    ),
    Hospital(
      id: 2,
      nome: "Hospital Evangélico de Vila Velha",
      endereco: "Av. Champagnat, 555 - Centro, Vila Velha - ES, 29101-220",
      telefone: "(27) 2121-1000",
      status: HospitalStatus.baixa,
      filaEmergencia: 8,
      tempoEspera: "20 min",
      especialidades: ["Emergência", "Pediatria", "Ginecologia"],
      lat: -20.34605,
      lng: -40.34327,
    ),
    Hospital(
      id: 3,
      nome: "Hospital Meridional Praia da Costa",
      endereco:
          "R. Prof. Telmo de Souza Torres, 116 - Praia da Costa, Vila Velha - ES, 29101-295",
      telefone: "(27) 2121-0200",
      status: HospitalStatus.alta,
      filaEmergencia: 28,
      tempoEspera: "80 min",
      especialidades: ["Emergência", "Neurologia", "UTI"],
      lat: -20.3342,
      lng: -40.2877,
    ),
    Hospital(
      id: 4,
      nome: "UPA Vila Velha Centro",
      endereco: "Rua Santa Rosa, 150 - Centro, Vila Velha - ES, 29100-040",
      telefone: "(27) 3149-3456",
      status: HospitalStatus.moderada,
      filaEmergencia: 12,
      tempoEspera: "35 min",
      especialidades: ["Emergência", "Clínica Geral"],
      lat: -20.3399,
      lng: -40.30690,
    ),
    Hospital(
      id: 5,
      nome: "Hospital Santa Mônica",
      endereco:
          "Rod. do Sol, s/n - Km 01 - Praia de Itaparica, Vila Velha - ES, 29102-020",
      telefone: "(27) 3320-3500",
      status: HospitalStatus.baixa,
      filaEmergencia: 5,
      tempoEspera: "15 min",
      especialidades: ["Emergência", "Cardiologia", "Oncologia"],
      lat: -20.3601,
      lng: -40.2960,
    ),
  ];

  @override
  void initState() {
    super.initState();
    _mapController = MapController(
      initPosition: GeoPoint(latitude: -20.3328, longitude: -40.2990),
    );
  }

  Future<void> _addMarkers() async {
    for (final hospital in _hospitais) {
      await _mapController?.addMarker(
        GeoPoint(latitude: hospital.lat, longitude: hospital.lng),
        markerIcon: MarkerIcon(
          icon: Icon(
            Icons.local_hospital,
            color: _getStatusColor(hospital.status),
            size: 36,
          ),
        ),
      );
    }
  }

  Color _getStatusColor(HospitalStatus status) {
    switch (status) {
      case HospitalStatus.baixa:
        return Colors.green;
      case HospitalStatus.moderada:
        return Colors.orange;
      case HospitalStatus.alta:
        return Colors.red;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          // Header
          _buildHeader(),

          // Map
          Expanded(
            child: Stack(
              children: [
                OSMFlutter(
                  controller: _mapController!,
                  onMapIsReady: (isReady) async {
                    if (isReady) {
                      await _addMarkers();
                    }
                  },
                  onGeoPointClicked: (geoPoint) {
                    final hospital = _hospitais.firstWhere(
                      (h) =>
                          h.lat == geoPoint.latitude &&
                          h.lng == geoPoint.longitude,
                      orElse: () => _hospitais.first,
                    );
                    setState(() {
                      _selectedHospital = hospital;
                    });
                  },
                  osmOption: OSMOption(
                    showZoomController: true,
                    userTrackingOption: const UserTrackingOption(
                      enableTracking: false,
                      unFollowUser: false,
                    ),
                    zoomOption: const ZoomOption(
                      initZoom: 13,
                      minZoomLevel: 8,
                      maxZoomLevel: 18,
                      stepZoom: 1.0,
                    ),
                    userLocationMarker: UserLocationMaker(
                      personMarker: MarkerIcon(
                        icon: Icon(
                          Icons.person_pin_circle,
                          color: Colors.blue,
                          size: 48,
                        ),
                      ),
                      directionArrowMarker: MarkerIcon(
                        icon: Icon(Icons.double_arrow, size: 48),
                      ),
                    ),
                  ),
                ),

                // Selected Hospital Card
                if (_selectedHospital != null)
                  Positioned(
                    bottom: 16,
                    left: 16,
                    right: 16,
                    child: _buildHospitalCard(_selectedHospital!),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildHeader() {
    return Container(
      color: Colors.white,
      child: SafeArea(
        child: Container(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              Row(
                children: [
                  // Back Button
                  Expanded(
                    child: OutlinedButton.icon(
                      onPressed: () => context.go('/'),
                      icon: const Icon(Icons.arrow_back, size: 16),
                      label: const Text('Voltar'),
                      style: OutlinedButton.styleFrom(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 16,
                          vertical: 8,
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 16),

                  // Logo and Title
                  Row(
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
                          size: 20,
                        ),
                      ),
                      const SizedBox(width: 12),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'Mapa Público',
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              color: Color(0xFF111827),
                            ),
                          ),
                          const Text(
                            'Filas Hospitalares - Vila Velha',
                            style: TextStyle(
                              fontSize: 12,
                              color: Color(0xFF6B7280),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ],
              ),

              const SizedBox(height: 12),

              // Status Legend
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  _buildStatusLegend('Baixa', Colors.green),
                  const SizedBox(width: 16),
                  _buildStatusLegend('Moderada', Colors.orange),
                  const SizedBox(width: 16),
                  _buildStatusLegend('Alta', Colors.red),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildStatusLegend(String label, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: Colors.grey[50],
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
            width: 12,
            height: 12,
            decoration: BoxDecoration(color: color, shape: BoxShape.circle),
          ),
          const SizedBox(width: 4),
          Text(
            label,
            style: const TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
          ),
        ],
      ),
    );
  }

  Widget _buildHospitalCard(Hospital hospital) {
    return Card(
      elevation: 8,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
      child: Container(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Header with status indicator
            Row(
              children: [
                Container(
                  width: 12,
                  height: 12,
                  decoration: BoxDecoration(
                    color: _getStatusColor(hospital.status),
                    shape: BoxShape.circle,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: Text(
                    hospital.nome,
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Color(0xFF111827),
                    ),
                  ),
                ),
                IconButton(
                  onPressed: () {
                    setState(() {
                      _selectedHospital = null;
                    });
                  },
                  icon: const Icon(Icons.close, color: Color(0xFF9CA3AF)),
                  padding: EdgeInsets.zero,
                  constraints: const BoxConstraints(),
                ),
              ],
            ),

            const SizedBox(height: 12),

            // Status and Queue Info
            Row(
              children: [
                Text(
                  'Status: ',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[700],
                  ),
                ),
                Text(
                  hospital.status.name.toUpperCase(),
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    color: _getStatusColor(hospital.status),
                  ),
                ),
                const SizedBox(width: 16),
                Text(
                  'Fila: ',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[700],
                  ),
                ),
                Text(
                  '${hospital.filaEmergencia}',
                  style: const TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    color: Color(0xFF111827),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 8),

            // Phone
            Row(
              children: [
                Text(
                  'Telefone: ',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[700],
                  ),
                ),
                Text(
                  hospital.telefone,
                  style: const TextStyle(
                    fontSize: 14,
                    fontFamily: 'monospace',
                    color: Color(0xFF111827),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 8),

            // Address
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Endereço: ',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[700],
                  ),
                ),
                Expanded(
                  child: Text(
                    hospital.endereco,
                    style: const TextStyle(
                      fontSize: 14,
                      color: Color(0xFF111827),
                    ),
                  ),
                ),
              ],
            ),

            const SizedBox(height: 8),

            // Specialties
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Especialidades: ',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w500,
                    color: Colors.grey[700],
                  ),
                ),
                Expanded(
                  child: Wrap(
                    spacing: 4,
                    runSpacing: 4,
                    children: hospital.especialidades.map((especialidade) {
                      return Container(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 8,
                          vertical: 2,
                        ),
                        decoration: BoxDecoration(
                          color: Colors.grey[100],
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Text(
                          especialidade,
                          style: const TextStyle(
                            fontSize: 12,
                            color: Color(0xFF6B7280),
                          ),
                        ),
                      );
                    }).toList(),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

// Hospital Model
class Hospital {
  final int id;
  final String nome;
  final String endereco;
  final String telefone;
  final HospitalStatus status;
  final int filaEmergencia;
  final String tempoEspera;
  final List<String> especialidades;
  final double lat;
  final double lng;

  Hospital({
    required this.id,
    required this.nome,
    required this.endereco,
    required this.telefone,
    required this.status,
    required this.filaEmergencia,
    required this.tempoEspera,
    required this.especialidades,
    required this.lat,
    required this.lng,
  });
}

enum HospitalStatus { baixa, moderada, alta }
