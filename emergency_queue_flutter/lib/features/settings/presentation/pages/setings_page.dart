import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:uuid/uuid.dart';
import '../../../../core/models/hospital.dart' as hospital_models;
import '../../../../core/services/integrador_service.dart';
import '../../../../core/services/login_service.dart';
import '../../../../core/components/navbar_admin.dart';

class SettingsPage extends StatefulWidget {
  final String? hospitalId;

  const SettingsPage({super.key, this.hospitalId});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  int _selectedIndex = 0;
  hospital_models.Hospital? currentHospital;
  List<hospital_models.Hospital> hospitals = [];
  bool isSuperAdmin = false;

  @override
  void initState() {
    super.initState();
    _loadHospitals();
    _loadCurrentHospital();
  }

  Future<void> _loadHospitals() async {
    // Carregar lista de hospitais (se for super admin)
    if (isSuperAdmin) {
      // TODO: Implement hospital loading
      hospitals = [];
    }
  }

  Future<void> _loadCurrentHospital() async {
    if (widget.hospitalId != null) {
      // TODO: Implement hospital loading
      currentHospital = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          // Navbar
          Navbar(currentRoute: '/settings'),

          // Main content
          Expanded(
            child: Row(
              children: [
                // Sidebar
                Container(
                  width: 280,
                  decoration: BoxDecoration(
                    color: Colors.white,
                    border: Border(
                      right: BorderSide(color: Colors.grey.withOpacity(0.2)),
                    ),
                  ),
                  child: _buildSidebar(),
                ),

                // Content
                Expanded(child: _buildContent()),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSidebar() {
    final menuItems = [
      {
        'title': 'Configurações do Hospital',
        'icon': Icons.local_hospital,
        'index': 0,
      },
      {'title': 'Gerenciar Usuários', 'icon': Icons.people, 'index': 1},
      {'title': 'Integração Go API', 'icon': Icons.code, 'index': 2},
      {'title': 'Logs do Sistema', 'icon': Icons.analytics, 'index': 3},
    ];

    return ListView(
      padding: const EdgeInsets.all(16),
      children: menuItems.map((item) {
        final isSelected = _selectedIndex == item['index'];

        return Container(
          margin: const EdgeInsets.only(bottom: 8),
          child: ListTile(
            onTap: () {
              setState(() {
                _selectedIndex = item['index'] as int;
              });
            },
            leading: Icon(
              item['icon'] as IconData,
              color: isSelected
                  ? const Color(0xFF2563EB)
                  : const Color(0xFF6B7280),
            ),
            title: Text(
              item['title'] as String,
              style: TextStyle(
                color: isSelected
                    ? const Color(0xFF2563EB)
                    : const Color(0xFF111827),
                fontWeight: isSelected ? FontWeight.w600 : FontWeight.normal,
              ),
            ),
            tileColor: isSelected
                ? const Color(0xFF2563EB).withOpacity(0.1)
                : null,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8),
            ),
          ),
        );
      }).toList(),
    );
  }

  Widget _buildContent() {
    switch (_selectedIndex) {
      case 0:
        return _buildHospitalSettings();
      case 1:
        return _buildUserManagement();
      case 2:
        return _buildGoApiIntegration();
      case 3:
        return _buildSystemLogs();
      default:
        return const Center(child: Text('Selecione uma opção'));
    }
  }

  Widget _buildHospitalSettings() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(24),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Configurações do Hospital',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 24),

          // Informações Básicas
          _buildSection('Informações Básicas', [
            _buildTextField('Nome do Hospital', 'Hospital Municipal'),
            _buildTextField('CNPJ', '12.345.678/0001-90'),
            _buildTextField('Telefone', '(11) 1234-5678'),
            _buildTextField('Email', 'contato@hospital.com'),
          ]),

          const SizedBox(height: 24),

          // Endereço
          _buildSection('Endereço', [
            _buildTextField('Rua', 'Rua das Flores, 123'),
            _buildTextField('Bairro', 'Centro'),
            _buildTextField('Cidade', 'São Paulo'),
            _buildTextField('Estado', 'SP'),
            _buildTextField('CEP', '01234-567'),
          ]),

          const SizedBox(height: 24),

          // Configurações do Sistema
          _buildSection('Configurações do Sistema', [
            _buildTextField('Capacidade de Leitos', '150'),
            _buildTextField('Tempo Médio de Atendimento (min)', '30'),
            _buildSwitch('Modo de Emergência', true),
            _buildSwitch('Notificações Push', true),
          ]),

          const SizedBox(height: 32),

          // Botões de Ação
          Row(
            children: [
              ElevatedButton.icon(
                onPressed: () {
                  // Salvar configurações
                },
                icon: const Icon(Icons.save),
                label: const Text('Salvar Configurações'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFF2563EB),
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(
                    horizontal: 24,
                    vertical: 12,
                  ),
                ),
              ),
              const SizedBox(width: 12),
              OutlinedButton.icon(
                onPressed: () {
                  // Restaurar padrões
                },
                icon: const Icon(Icons.restore),
                label: const Text('Restaurar Padrões'),
                style: OutlinedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(
                    horizontal: 24,
                    vertical: 12,
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildUserManagement() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(24),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              const Text(
                'Gerenciar Usuários',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Color(0xFF111827),
                ),
              ),
              ElevatedButton.icon(
                onPressed: () {
                  _showAddUserDialog();
                },
                icon: const Icon(Icons.add),
                label: const Text('Adicionar Usuário'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: const Color(0xFF10B981),
                  foregroundColor: Colors.white,
                ),
              ),
            ],
          ),
          const SizedBox(height: 24),

          // Lista de Usuários
          Container(
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(8),
              border: Border.all(color: Colors.grey.withOpacity(0.2)),
            ),
            child: Column(
              children: [
                _buildUserTableHeader(),
                _buildUserTableRow(
                  'João Silva',
                  'joao@hospital.com',
                  'Admin',
                  true,
                ),
                _buildUserTableRow(
                  'Maria Santos',
                  'maria@hospital.com',
                  'Médico',
                  true,
                ),
                _buildUserTableRow(
                  'Pedro Costa',
                  'pedro@hospital.com',
                  'Enfermeiro',
                  false,
                ),
                _buildUserTableRow(
                  'Ana Oliveira',
                  'ana@hospital.com',
                  'Recepcionista',
                  true,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildGoApiIntegration() {
    if (currentHospital == null) {
      return const Center(child: Text('Selecione um hospital'));
    }

    return SingleChildScrollView(
      padding: const EdgeInsets.all(24),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Header com informações do hospital
          Container(
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              color: const Color(0xFF2563EB).withOpacity(0.1),
              borderRadius: BorderRadius.circular(8),
            ),
            child: Row(
              children: [
                const Icon(Icons.local_hospital, color: Color(0xFF2563EB)),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        currentHospital!.name,
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      Text(
                        'ID: ${currentHospital!.id}',
                        style: const TextStyle(
                          color: Color(0xFF6B7280),
                          fontSize: 12,
                        ),
                      ),
                      Text(
                        'ERP: ${currentHospital!.erpSystem}',
                        style: const TextStyle(color: Color(0xFF6B7280)),
                      ),
                    ],
                  ),
                ),
                Container(
                  padding: const EdgeInsets.symmetric(
                    horizontal: 8,
                    vertical: 4,
                  ),
                  decoration: BoxDecoration(
                    color: currentHospital!.active ? Colors.green : Colors.red,
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Text(
                    currentHospital!.active ? 'Ativo' : 'Inativo',
                    style: const TextStyle(color: Colors.white, fontSize: 12),
                  ),
                ),
              ],
            ),
          ),

          const SizedBox(height: 24),

          // Status da API específica do hospital
          FutureBuilder<Map<String, dynamic>>(
            future: IntegradorService.getStatus(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const Center(child: CircularProgressIndicator());
              }

              if (snapshot.hasError) {
                return _buildErrorCard(
                  'Erro ao carregar status: ${snapshot.error}',
                );
              }

              final status = snapshot.data!;
              return _buildStatusSection(status);
            },
          ),

          const SizedBox(height: 24),

          // Scripts específicos do hospital
          FutureBuilder<List<Map<String, dynamic>>>(
            future: IntegradorService.getScripts(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const Center(child: CircularProgressIndicator());
              }

              if (snapshot.hasError) {
                return _buildErrorCard(
                  'Erro ao carregar scripts: ${snapshot.error}',
                );
              }

              final scripts = snapshot.data!;
              return _buildScriptsSection(scripts);
            },
          ),
        ],
      ),
    );
  }

  Widget _buildSystemLogs() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(24),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Logs do Sistema',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 24),

          // Filtros
          Row(
            children: [
              Expanded(
                child: DropdownButtonFormField<String>(
                  decoration: const InputDecoration(
                    labelText: 'Nível de Log',
                    border: OutlineInputBorder(),
                  ),
                  value: 'Todos',
                  items: ['Todos', 'Error', 'Warning', 'Info', 'Debug']
                      .map(
                        (level) =>
                            DropdownMenuItem(value: level, child: Text(level)),
                      )
                      .toList(),
                  onChanged: (value) {},
                ),
              ),
              const SizedBox(width: 16),
              Expanded(
                child: TextFormField(
                  decoration: const InputDecoration(
                    labelText: 'Buscar',
                    border: OutlineInputBorder(),
                    prefixIcon: Icon(Icons.search),
                  ),
                ),
              ),
            ],
          ),

          const SizedBox(height: 24),

          // Lista de Logs
          Container(
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(8),
              border: Border.all(color: Colors.grey.withOpacity(0.2)),
            ),
            child: Column(
              children: [
                _buildLogEntry(
                  '2024-01-15 10:30:15',
                  'INFO',
                  'Sistema iniciado',
                ),
                _buildLogEntry(
                  '2024-01-15 10:30:16',
                  'WARNING',
                  'Conexão lenta detectada',
                ),
                _buildLogEntry(
                  '2024-01-15 10:30:17',
                  'ERROR',
                  'Falha na sincronização',
                ),
                _buildLogEntry(
                  '2024-01-15 10:30:18',
                  'INFO',
                  'Backup realizado',
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  // Widgets auxiliares
  Widget _buildSection(String title, List<Widget> children) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.grey.withOpacity(0.2)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.w600,
              color: Color(0xFF111827),
            ),
          ),
          const SizedBox(height: 16),
          ...children,
        ],
      ),
    );
  }

  Widget _buildTextField(String label, String value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: TextFormField(
        initialValue: value,
        decoration: InputDecoration(
          labelText: label,
          border: const OutlineInputBorder(),
        ),
      ),
    );
  }

  Widget _buildSwitch(String label, bool value) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 16),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(label),
          Switch(value: value, onChanged: (newValue) {}),
        ],
      ),
    );
  }

  Widget _buildUserTableHeader() {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: const Color(0xFFF9FAFB),
        border: Border(bottom: BorderSide(color: Colors.grey.withOpacity(0.2))),
      ),
      child: const Row(
        children: [
          Expanded(
            flex: 2,
            child: Text('Nome', style: TextStyle(fontWeight: FontWeight.w600)),
          ),
          Expanded(
            flex: 2,
            child: Text('Email', style: TextStyle(fontWeight: FontWeight.w600)),
          ),
          Expanded(
            flex: 1,
            child: Text(
              'Perfil',
              style: TextStyle(fontWeight: FontWeight.w600),
            ),
          ),
          Expanded(
            flex: 1,
            child: Text(
              'Status',
              style: TextStyle(fontWeight: FontWeight.w600),
            ),
          ),
          Expanded(
            flex: 1,
            child: Text('Ações', style: TextStyle(fontWeight: FontWeight.w600)),
          ),
        ],
      ),
    );
  }

  Widget _buildUserTableRow(
    String name,
    String email,
    String role,
    bool active,
  ) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        border: Border(bottom: BorderSide(color: Colors.grey.withOpacity(0.1))),
      ),
      child: Row(
        children: [
          Expanded(flex: 2, child: Text(name)),
          Expanded(flex: 2, child: Text(email)),
          Expanded(flex: 1, child: Text(role)),
          Expanded(
            flex: 1,
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
              decoration: BoxDecoration(
                color: active
                    ? Colors.green.withOpacity(0.1)
                    : Colors.red.withOpacity(0.1),
                borderRadius: BorderRadius.circular(12),
              ),
              child: Text(
                active ? 'Ativo' : 'Inativo',
                style: TextStyle(
                  color: active ? Colors.green : Colors.red,
                  fontSize: 12,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ),
          ),
          Expanded(
            flex: 1,
            child: Row(
              children: [
                IconButton(
                  onPressed: () {},
                  icon: const Icon(Icons.edit, size: 18),
                ),
                IconButton(
                  onPressed: () {},
                  icon: const Icon(Icons.delete, size: 18, color: Colors.red),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildStatusSection(Map<String, dynamic> status) {
    return _buildSection('Status da Integração', [
      _buildStatusCard(
        'Go API',
        status['goApiRunning'] ? 'Online' : 'Offline',
        status['goApiRunning'] ? Colors.green : Colors.red,
      ),
      _buildStatusCard('ERP System', status['erpSystem'], Colors.blue),
      _buildStatusCard(
        'Porta API',
        status['goApiPort'] ?? 'N/A',
        Colors.orange,
      ),
      _buildStatusCard(
        'Caminho API',
        status['goApiPath'] ?? 'N/A',
        Colors.purple,
      ),
    ]);
  }

  Widget _buildScriptsSection(List<Map<String, dynamic>> scripts) {
    return _buildSection(
      'Scripts de Integração',
      scripts
          .map(
            (script) => _buildScriptCard(
              script['name'],
              script['path'],
              script['size'],
            ),
          )
          .toList(),
    );
  }

  Widget _buildErrorCard(String message) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.red.withOpacity(0.1),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.red.withOpacity(0.3)),
      ),
      child: Row(
        children: [
          const Icon(Icons.error, color: Colors.red),
          const SizedBox(width: 12),
          Expanded(
            child: Text(message, style: const TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }

  Widget _buildStatusCard(String title, String status, Color color) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: color.withOpacity(0.1),
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: color.withOpacity(0.3)),
      ),
      child: Row(
        children: [
          Icon(Icons.circle, color: color, size: 12),
          const SizedBox(width: 8),
          Text(title, style: const TextStyle(fontWeight: FontWeight.w600)),
          const Spacer(),
          Text(
            status,
            style: TextStyle(color: color, fontWeight: FontWeight.w600),
          ),
        ],
      ),
    );
  }

  Widget _buildScriptCard(String name, String path, String size) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.grey.withOpacity(0.2)),
      ),
      child: Row(
        children: [
          const Icon(Icons.code, color: Color(0xFF2563EB)),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(name, style: const TextStyle(fontWeight: FontWeight.w600)),
                Text(
                  path,
                  style: const TextStyle(
                    color: Color(0xFF6B7280),
                    fontSize: 12,
                  ),
                ),
              ],
            ),
          ),
          IconButton(onPressed: () {}, icon: const Icon(Icons.visibility)),
        ],
      ),
    );
  }

  Widget _buildLogEntry(String timestamp, String level, String message) {
    Color levelColor;
    switch (level) {
      case 'ERROR':
        levelColor = Colors.red;
        break;
      case 'WARNING':
        levelColor = Colors.orange;
        break;
      case 'INFO':
        levelColor = Colors.blue;
        break;
      default:
        levelColor = Colors.grey;
    }

    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        border: Border(bottom: BorderSide(color: Colors.grey.withOpacity(0.1))),
      ),
      child: Row(
        children: [
          Text(
            timestamp,
            style: const TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
          ),
          const SizedBox(width: 16),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
            decoration: BoxDecoration(
              color: levelColor.withOpacity(0.1),
              borderRadius: BorderRadius.circular(4),
            ),
            child: Text(
              level,
              style: TextStyle(
                color: levelColor,
                fontSize: 12,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
          const SizedBox(width: 16),
          Expanded(child: Text(message)),
        ],
      ),
    );
  }

  void _showAddUserDialog() {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Adicionar Usuário'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            TextFormField(decoration: const InputDecoration(labelText: 'Nome')),
            TextFormField(
              decoration: const InputDecoration(labelText: 'Email'),
            ),
            DropdownButtonFormField<String>(
              decoration: const InputDecoration(labelText: 'Perfil'),
              items: ['Admin', 'Médico', 'Enfermeiro', 'Recepcionista']
                  .map(
                    (role) => DropdownMenuItem(value: role, child: Text(role)),
                  )
                  .toList(),
              onChanged: (value) {},
            ),
          ],
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Cancelar'),
          ),
          ElevatedButton(
            onPressed: () => Navigator.of(context).pop(),
            child: const Text('Adicionar'),
          ),
        ],
      ),
    );
  }
}
