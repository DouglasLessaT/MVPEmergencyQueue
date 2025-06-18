import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class Navbar extends StatefulWidget {
  final String currentRoute;

  const Navbar({super.key, required this.currentRoute});

  @override
  State<Navbar> createState() => _NavbarState();
}

class _NavbarState extends State<Navbar> {
  bool _isMobileMenuOpen = false;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          decoration: BoxDecoration(
            color: Colors.white.withOpacity(0.95),
            border: Border(
              bottom: BorderSide(color: Colors.grey.withOpacity(0.2), width: 1),
            ),
            boxShadow: [
              BoxShadow(
                color: Colors.black.withOpacity(0.05),
                blurRadius: 10,
                offset: const Offset(0, 2),
              ),
            ],
          ),
          child: SafeArea(
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
              child: Row(
                children: [
                  // Logo
                  _buildLogo(),
                  const Spacer(),

                  // Desktop Navigation
                  if (MediaQuery.of(context).size.width > 768)
                    _buildDesktopNavigation(),

                  // Mobile Menu Button
                  if (MediaQuery.of(context).size.width <= 768)
                    _buildMobileMenuButton(),
                ],
              ),
            ),
          ),
        ),

        // Mobile Menu Overlay
        if (_isMobileMenuOpen && MediaQuery.of(context).size.width <= 768)
          _buildMobileMenu(),
      ],
    );
  }

  Widget _buildLogo() {
    return GestureDetector(
      onTap: () => context.go('/'),
      child: Row(
        children: [
          Container(
            padding: const EdgeInsets.all(8),
            decoration: BoxDecoration(
              gradient: const LinearGradient(
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
                colors: [Color(0xFF2563EB), Color(0xFF1D4ED8)],
              ),
              borderRadius: BorderRadius.circular(12),
              boxShadow: [
                BoxShadow(
                  color: const Color(0xFF2563EB).withOpacity(0.3),
                  blurRadius: 8,
                  offset: const Offset(0, 2),
                ),
              ],
            ),
            child: const Icon(
              Icons.local_hospital,
              color: Colors.white,
              size: 24,
            ),
          ),
          const SizedBox(width: 12),
          if (MediaQuery.of(context).size.width > 640)
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                ShaderMask(
                  shaderCallback: (bounds) => const LinearGradient(
                    colors: [Color(0xFF2563EB), Color(0xFF1D4ED8)],
                  ).createShader(bounds),
                  child: const Text(
                    'HealthQueue',
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
                const Text(
                  'Sistema de Filas',
                  style: TextStyle(fontSize: 12, color: Color(0xFF6B7280)),
                ),
              ],
            ),
        ],
      ),
    );
  }

  Widget _buildDesktopNavigation() {
    return Row(children: _buildNavLinks());
  }

  Widget _buildMobileMenuButton() {
    return IconButton(
      onPressed: () {
        setState(() {
          _isMobileMenuOpen = !_isMobileMenuOpen;
        });
      },
      icon: Icon(
        _isMobileMenuOpen ? Icons.close : Icons.menu,
        color: const Color(0xFF6B7280),
      ),
    );
  }

  List<Widget> _buildNavLinks() {
    final navItems = [
      {'title': 'Dashboard', 'route': '/dashboard', 'icon': Icons.dashboard},
      {
        'title': 'Hospitais',
        'route': '/hospitals',
        'icon': Icons.local_hospital,
      },
      {'title': 'Relatórios', 'route': '/reports', 'icon': Icons.analytics},
      {'title': 'Configurações', 'route': '/settings', 'icon': Icons.settings},
    ];

    return navItems.map((item) {
      final isActive = widget.currentRoute == item['route'];

      return Padding(
        padding: const EdgeInsets.symmetric(horizontal: 4),
        child: TextButton(
          onPressed: () => context.go(item['route'] as String),
          style: TextButton.styleFrom(
            backgroundColor: isActive
                ? const Color(0xFF2563EB).withOpacity(0.1)
                : Colors.transparent,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8),
            ),
          ),
          child: Row(
            children: [
              Icon(
                item['icon'] as IconData,
                size: 18,
                color: isActive
                    ? const Color(0xFF2563EB)
                    : const Color(0xFF6B7280),
              ),
              const SizedBox(width: 8),
              Text(
                item['title'] as String,
                style: TextStyle(
                  color: isActive
                      ? const Color(0xFF2563EB)
                      : const Color(0xFF6B7280),
                  fontWeight: isActive ? FontWeight.w600 : FontWeight.normal,
                ),
              ),
            ],
          ),
        ),
      );
    }).toList();
  }

  Widget _buildMobileMenu() {
    return Positioned(
      top: 0,
      right: 0,
      child: Container(
        width: 280,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
          color: Colors.white,
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              blurRadius: 10,
              offset: const Offset(-2, 0),
            ),
          ],
        ),
        child: Column(
          children: [
            // Mobile Menu Header
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                border: Border(
                  bottom: BorderSide(
                    color: Colors.grey.withOpacity(0.2),
                    width: 1,
                  ),
                ),
              ),
              child: Row(
                children: [
                  Container(
                    padding: const EdgeInsets.all(8),
                    decoration: BoxDecoration(
                      gradient: const LinearGradient(
                        begin: Alignment.topLeft,
                        end: Alignment.bottomRight,
                        colors: [Color(0xFF2563EB), Color(0xFF1D4ED8)],
                      ),
                      borderRadius: BorderRadius.circular(12),
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
                        'HealthQueue',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                          color: Color(0xFF111827),
                        ),
                      ),
                      const Text(
                        'Sistema de Filas',
                        style: TextStyle(
                          fontSize: 12,
                          color: Color(0xFF6B7280),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),

            // Mobile Navigation Links
            Expanded(
              child: ListView(
                padding: const EdgeInsets.all(16),
                children: _buildMobileNavLinks(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _buildMobileNavLinks() {
    final navItems = [
      {'title': 'Dashboard', 'route': '/dashboard', 'icon': Icons.dashboard},
      {
        'title': 'Hospitais',
        'route': '/hospitals',
        'icon': Icons.local_hospital,
      },
      {'title': 'Relatórios', 'route': '/reports', 'icon': Icons.analytics},
      {'title': 'Configurações', 'route': '/settings', 'icon': Icons.settings},
    ];

    return navItems.map((item) {
      final isActive = widget.currentRoute == item['route'];

      return Container(
        margin: const EdgeInsets.only(bottom: 8),
        child: ListTile(
          onTap: () {
            context.go(item['route'] as String);
            setState(() {
              _isMobileMenuOpen = false;
            });
          },
          leading: Icon(
            item['icon'] as IconData,
            color: isActive ? const Color(0xFF2563EB) : const Color(0xFF6B7280),
          ),
          title: Text(
            item['title'] as String,
            style: TextStyle(
              color: isActive
                  ? const Color(0xFF2563EB)
                  : const Color(0xFF111827),
              fontWeight: isActive ? FontWeight.w600 : FontWeight.normal,
            ),
          ),
          tileColor: isActive ? const Color(0xFF2563EB).withOpacity(0.1) : null,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
        ),
      );
    }).toList();
  }
}
