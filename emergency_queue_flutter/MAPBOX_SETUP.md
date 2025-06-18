# OpenStreetMap Setup Guide

Este guia documenta a configuração do OpenStreetMap no projeto Flutter.

## Configuração Atual

O projeto agora usa **OpenStreetMap** através do plugin `flutter_osm_plugin`, que oferece suporte completo para:
- ✅ **Android** (API 21+)
- ✅ **iOS** (iOS 13+)
- ✅ **Web** (Chrome, Firefox, Safari)

## Dependências

O projeto utiliza as seguintes dependências para mapas:

```yaml
dependencies:
  flutter_osm_plugin: ^1.3.8
  permission_handler: ^11.3.1
```

## Funcionalidades Implementadas

### Mapa Interativo
- Mapa OpenStreetMap centrado em Vila Velha, ES
- Marcadores coloridos para hospitais (verde, laranja, vermelho)
- Controles de zoom nativos
- Clique nos marcadores para ver detalhes

### Hospitais
- Lista de hospitais com dados reais de Vila Velha
- Status das filas (baixa, moderada, alta)
- Informações detalhadas (telefone, endereço, especialidades)
- Cards informativos ao clicar nos marcadores

### Interface
- Header com legenda de status
- Botão de voltar
- Design responsivo
- Tema consistente com o resto do app

## Como Usar

1. **Instalar dependências:**
   ```bash
   flutter pub get
   ```

2. **Executar o app:**
   ```bash
   # Web
   flutter run -d chrome
   
   # Android
   flutter run -d android
   
   # iOS
   flutter run -d ios
   ```

3. **Navegar para o mapa:**
   - Acesse a rota `/map` no app
   - Ou clique no botão de mapa na interface

## Permissões

### Android
As permissões de localização já estão configuradas no `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
```

### iOS
A descrição de uso da localização está configurada no `Info.plist`:
```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>This app needs access to location to show nearby hospitals and provide navigation features.</string>
```

## Vantagens do OpenStreetMap

1. **Gratuito**: Não requer chaves de API ou pagamentos
2. **Multiplataforma**: Funciona em Android, iOS e Web
3. **Dados Abertos**: Baseado em dados da comunidade
4. **Sem Limites**: Sem restrições de uso ou requisições
5. **Customizável**: Totalmente personalizável

## Personalização

### Cores dos Marcadores
```dart
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
```

### Configuração do Mapa
```dart
OSMFlutter(
  controller: _mapController!,
  osmOption: OSMOption(
    showZoomController: true,
    zoomOption: const ZoomOption(
      initZoom: 13,
      minZoomLevel: 8,
      maxZoomLevel: 18,
      stepZoom: 1.0,
    ),
  ),
)
```

## Troubleshooting

### Problemas Comuns

1. **Mapa não carrega no web:**
   - Verifique se está usando HTTPS (recomendado)
   - Limpe o cache do navegador

2. **Marcadores não aparecem:**
   - Verifique se as coordenadas estão corretas
   - Aguarde o carregamento completo do mapa

3. **Permissões negadas:**
   - Verifique as configurações de permissão do dispositivo
   - Reinicie o app após conceder permissões

### Comandos Úteis
```bash
# Limpar cache
flutter clean

# Reinstalar dependências
flutter pub get

# Verificar dispositivos
flutter devices

# Executar testes
flutter test
```

## Próximos Passos

Para expandir a funcionalidade do mapa, considere:

1. **Navegação**: Adicionar rotas entre pontos
2. **Busca**: Implementar busca de hospitais
3. **Filtros**: Filtrar por especialidade ou status
4. **Atualizações em Tempo Real**: Sincronizar dados de filas
5. **Geolocalização**: Mostrar hospitais próximos ao usuário

## Recursos

- [Documentação do flutter_osm_plugin](https://pub.dev/packages/flutter_osm_plugin)
- [OpenStreetMap](https://www.openstreetmap.org/)
- [Flutter Documentation](https://docs.flutter.dev/) 