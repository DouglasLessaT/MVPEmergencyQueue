import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:flutter_osm_plugin/flutter_osm_plugin.dart';
import '../../../../core/services/map_service.dart';

class MapPageWithAPI extends StatefulWidget {
  const MapPageWithAPI({super.key});

  @override
  State<MapPageWithAPI> createState() => _MapPageWithAPIState();
}

class _MapPageWithAPIState extends State<MapPageWithAPI> {
  MapController? _mapController;
  Map<String, dynamic>? _selectedHospital;
  List<Map<String, dynamic>> _hospitals = [];
  bool _isLoading = true;
  String? _errorMessage;

  @override
  void initState() {
    super.initState();
    print('🗺️ [DEBUG] MapPageWithAPI - initState iniciado');
    _mapController = MapController(
      initPosition: GeoPoint(latitude: -20.3328, longitude: -40.2990),
    );
    print(
      '🗺️ [DEBUG] MapController criado com posição inicial: -20.3328, -40.2990',
    );
    _loadHospitals();
  }

  Future<void> _loadHospitals() async {
    print('🏥 [DEBUG] Iniciando carregamento de hospitais...');
    try {
      setState(() {
        _isLoading = true;
        _errorMessage = null;
      });
      print('🏥 [DEBUG] Estado de loading definido como true');

      print('🏥 [DEBUG] Chamando MapService.getHospitalsWithMapData()...');
      final hospitalsData = await MapService.getHospitalsWithMapData();
      print(
        '🏥 [DEBUG] Dados brutos recebidos da API: ${hospitalsData.length} hospitais',
      );

      // Log detalhado dos dados brutos
      for (int i = 0; i < hospitalsData.length; i++) {
        final hospital = hospitalsData[i];
        print('🏥 [DEBUG] Hospital $i - Dados brutos:');
        print('   ID: ${hospital['id']}');
        print('   Nome: ${hospital['name']}');
        print('   Latitude: ${hospital['latitude']}');
        print('   Longitude: ${hospital['longitude']}');
        print('   Status: ${hospital['status']}');
        print('   Fila: ${hospital['queueSize']}');
      }

      print('🏥 [DEBUG] Convertendo dados para formato Flutter...');
      final hospitals = hospitalsData
          .map((data) => MapService.convertToFlutterHospitalFormat(data))
          .toList();

      print('🏥 [DEBUG] Dados convertidos: ${hospitals.length} hospitais');

      // Log detalhado dos dados convertidos
      for (int i = 0; i < hospitals.length; i++) {
        final hospital = hospitals[i];
        print('🏥 [DEBUG] Hospital $i - Dados convertidos:');
        print('   ID: ${hospital['id']}');
        print('   Nome: ${hospital['nome']}');
        print('   Lat: ${hospital['lat']}');
        print('   Lng: ${hospital['lng']}');
        print('   Status: ${hospital['status']}');
        print('   Fila: ${hospital['filaEmergencia']}');
        print('   Endereço: ${hospital['endereco']}');
      }

      setState(() {
        _hospitals = hospitals;
        _isLoading = false;
      });
      print('🏥 [DEBUG] Estado atualizado com ${hospitals.length} hospitais');

      // Adicionar marcadores após carregar os dados
      print('🏥 [DEBUG] Iniciando adição de marcadores no mapa...');
      await _addMarkers();
      print('🏥 [DEBUG] Marcadores adicionados com sucesso!');
    } catch (e) {
      print('❌ [DEBUG] Erro ao carregar hospitais: $e');
      setState(() {
        _errorMessage = 'Erro ao carregar hospitais: $e';
        _isLoading = false;
      });
    }
  }

  Future<void> _addMarkers() async {
    print('📍 [DEBUG] _addMarkers() iniciado');
    print('📍 [DEBUG] Total de hospitais para adicionar: ${_hospitals.length}');

    if (_mapController == null) {
      print(
        '❌ [DEBUG] MapController é null, não é possível adicionar marcadores',
      );
      return;
    }

    for (int i = 0; i < _hospitals.length; i++) {
      final hospital = _hospitals[i];
      final lat = hospital['lat'];
      final lng = hospital['lng'];
      final nome = hospital['nome'];
      final status = hospital['status'];

      print('📍 [DEBUG] Adicionando marcador $i:');
      print('   Nome: $nome');
      print('   Lat: $lat');
      print('   Lng: $lng');
      print('   Status: $status');
      print('   Cor: ${_getStatusColor(status)}');

      try {
        await _mapController!.addMarker(
          GeoPoint(latitude: lat, longitude: lng),
          markerIcon: MarkerIcon(
            icon: Icon(
              Icons.local_hospital,
              color: _getStatusColor(status),
              size: 36,
            ),
          ),
        );
        print('✅ [DEBUG] Marcador $i adicionado com sucesso para $nome');
      } catch (e) {
        print('❌ [DEBUG] Erro ao adicionar marcador $i para $nome: $e');
      }
    }

    print('📍 [DEBUG] _addMarkers() concluído');
  }

  Color _getStatusColor(String status) {
    Color color;
    switch (status) {
      case 'baixa':
        color = Colors.green;
        break;
      case 'moderada':
        color = Colors.orange;
        break;
      case 'alta':
        color = Colors.red;
        break;
      default:
        color = Colors.grey;
        break;
    }
    print('🎨 [DEBUG] Status "$status" mapeado para cor: $color');
    return color;
  }

  @override
  Widget build(BuildContext context) {
    print(
      '🏗️ [DEBUG] build() chamado - isLoading: $_isLoading, hospitais: ${_hospitals.length}',
    );

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
                    print(
                      '🗺️ [DEBUG] onMapIsReady chamado - isReady: $isReady, isLoading: $_isLoading',
                    );
                    if (isReady && !_isLoading) {
                      print(
                        '🗺️ [DEBUG] Mapa pronto e dados carregados, adicionando marcadores...',
                      );
                      await _addMarkers();
                    } else if (isReady && _isLoading) {
                      print(
                        '🗺️ [DEBUG] Mapa pronto mas dados ainda carregando...',
                      );
                    } else {
                      print('🗺️ [DEBUG] Mapa não está pronto ainda...');
                    }
                  },
                  onGeoPointClicked: (geoPoint) {
                    print(
                      '👆 [DEBUG] GeoPoint clicado: ${geoPoint.latitude}, ${geoPoint.longitude}',
                    );

                    final hospital = _hospitals.firstWhere(
                      (h) =>
                          h['lat'] == geoPoint.latitude &&
                          h['lng'] == geoPoint.longitude,
                      orElse: () =>
                          _hospitals.isNotEmpty ? _hospitals.first : {},
                    );

                    if (hospital.isNotEmpty) {
                      print(
                        '🏥 [DEBUG] Hospital encontrado: ${hospital['nome']}',
                      );
                      setState(() {
                        _selectedHospital = hospital;
                      });
                    } else {
                      print(
                        '❌ [DEBUG] Nenhum hospital encontrado para as coordenadas clicadas',
                      );
                    }
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

                // Loading indicator
                if (_isLoading)
                  Container(
                    color: Colors.black54,
                    child: const Center(
                      child: CircularProgressIndicator(color: Colors.white),
                    ),
                  ),

                // Error message
                if (_errorMessage != null)
                  Positioned(
                    top: 16,
                    left: 16,
                    right: 16,
                    child: Container(
                      padding: const EdgeInsets.all(16),
                      decoration: BoxDecoration(
                        color: Colors.red[100],
                        borderRadius: BorderRadius.circular(8),
                        border: Border.all(color: Colors.red),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            children: [
                              Icon(Icons.error, color: Colors.red[700]),
                              const SizedBox(width: 8),
                              Expanded(
                                child: Text(
                                  'Erro de Conexão',
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    color: Colors.red[700],
                                  ),
                                ),
                              ),
                              IconButton(
                                onPressed: () {
                                  print('🔄 [DEBUG] Botão refresh pressionado');
                                  _loadHospitals();
                                },
                                icon: Icon(
                                  Icons.refresh,
                                  color: Colors.red[700],
                                ),
                                padding: EdgeInsets.zero,
                                constraints: const BoxConstraints(),
                              ),
                            ],
                          ),
                          const SizedBox(height: 4),
                          Text(
                            _errorMessage!,
                            style: TextStyle(color: Colors.red[700]),
                          ),
                        ],
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

              // Refresh button
              if (!_isLoading)
                Padding(
                  padding: const EdgeInsets.only(top: 8),
                  child: Row(
                    children: [
                      Expanded(
                        child: OutlinedButton.icon(
                          onPressed: () {
                            print(
                              '🔄 [DEBUG] Botão "Atualizar Dados" pressionado',
                            );
                            _loadHospitals();
                          },
                          icon: const Icon(Icons.refresh, size: 16),
                          label: const Text('Atualizar Dados'),
                          style: OutlinedButton.styleFrom(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 16,
                              vertical: 8,
                            ),
                          ),
                        ),
                      ),
                      const SizedBox(width: 8),
                      OutlinedButton.icon(
                        onPressed: () async {
                          print(
                            '🧪 [DEBUG] Botão "Testar Conexão" pressionado',
                          );
                          try {
                            final result = await MapService.testConnection();
                            print(
                              '✅ [DEBUG] Teste de conexão bem-sucedido: $result',
                            );
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(
                                content: Text(
                                  'Conexão OK: ${result['message']}',
                                ),
                                backgroundColor: Colors.green,
                              ),
                            );
                          } catch (e) {
                            print('❌ [DEBUG] Erro no teste de conexão: $e');
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(
                                content: Text('Erro de conexão: $e'),
                                backgroundColor: Colors.red,
                              ),
                            );
                          }
                        },
                        icon: const Icon(Icons.wifi, size: 16),
                        label: const Text('Testar'),
                        style: OutlinedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(
                            horizontal: 16,
                            vertical: 8,
                          ),
                        ),
                      ),
                    ],
                  ),
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

  Widget _buildHospitalCard(Map<String, dynamic> hospital) {
    print('🏥 [DEBUG] Construindo card do hospital: ${hospital['nome']}');

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
                    color: _getStatusColor(hospital['status']),
                    shape: BoxShape.circle,
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: Text(
                    hospital['nome'] ?? 'Hospital',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Color(0xFF111827),
                    ),
                  ),
                ),
                IconButton(
                  onPressed: () {
                    print('❌ [DEBUG] Fechando card do hospital');
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
                  (hospital['status'] ?? 'moderada').toUpperCase(),
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                    color: _getStatusColor(hospital['status'] ?? 'moderada'),
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
                  '${hospital['filaEmergencia'] ?? 0}',
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
                  hospital['telefone'] ?? 'N/A',
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
                    hospital['endereco'] ?? 'Endereço não disponível',
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
                    children:
                        (hospital['especialidades'] as List<String>? ??
                                ['Emergência'])
                            .map((especialidade) {
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
                            })
                            .toList(),
                  ),
                ),
              ],
            ),

            // Additional info from API
            if (hospital['capacidadeTotal'] != null)
              Padding(
                padding: const EdgeInsets.only(top: 8),
                child: Row(
                  children: [
                    Text(
                      'Capacidade: ',
                      style: TextStyle(
                        fontSize: 14,
                        fontWeight: FontWeight.w500,
                        color: Colors.grey[700],
                      ),
                    ),
                    Text(
                      '${hospital['capacidadeAtiva'] ?? 0}/${hospital['capacidadeTotal']}',
                      style: const TextStyle(
                        fontSize: 14,
                        fontWeight: FontWeight.bold,
                        color: Color(0xFF111827),
                      ),
                    ),
                  ],
                ),
              ),
          ],
        ),
      ),
    );
  }
}
