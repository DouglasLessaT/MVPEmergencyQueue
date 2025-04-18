
import React, { useState, useCallback } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { MapPin } from "lucide-react";
import { mockHospitals } from "@/data/mockData";
import { GoogleMap, useJsApiLoader, Marker, InfoWindow } from "@react-google-maps/api";

interface HospitalMapProps {
  className?: string;
}

// Center on São Paulo, Brazil
const center = {
  lat: -23.55052,
  lng: -46.633308
};

const containerStyle = {
  width: '100%',
  height: '100%',
  minHeight: '400px'
};

const googleMapsApiKey = ""; // You will need to provide your Google Maps API key

const HospitalMap: React.FC<HospitalMapProps> = ({ className }) => {
  const [selectedHospital, setSelectedHospital] = useState<string | null>(null);
  const [map, setMap] = useState<google.maps.Map | null>(null);
  
  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: googleMapsApiKey
  });

  const onLoad = useCallback(function callback(map: google.maps.Map) {
    setMap(map);
  }, []);

  const onUnmount = useCallback(function callback() {
    setMap(null);
  }, []);

  // Calculate occupancy percentage for each hospital
  const getOccupancyPercentage = (hospitalId: string) => {
    const hospital = mockHospitals.find(h => h.id === hospitalId);
    if (!hospital) return 0;
    return Math.round(((hospital.totalBeds - hospital.availableBeds) / hospital.totalBeds) * 100);
  };

  // Determine badge color based on occupancy percentage
  const getBadgeColor = (percentage: number) => {
    if (percentage < 50) return "bg-green-500";
    if (percentage < 80) return "bg-yellow-500";
    return "bg-red-500";
  };

  // Distribute hospitals around São Paulo with unique positions
  const getHospitalPosition = (hospitalId: string, index: number) => {
    // Create a distribution of hospitals around São Paulo
    const radius = 0.02; // ~2km radius
    const hospital = mockHospitals.find(h => h.id === hospitalId);
    if (!hospital) return center;
    
    // Create a unique position for each hospital based on index
    const angle = (index / mockHospitals.length) * Math.PI * 2;
    return {
      lat: center.lat + Math.sin(angle) * radius * (1 + index % 3),
      lng: center.lng + Math.cos(angle) * radius * (1 + index % 3)
    };
  };

  // Check if Google Maps is loaded
  if (!isLoaded) {
    return (
      <div className={`w-full ${className}`}>
        <div className="mb-6 text-center">
          <h3 className="text-2xl font-bold mb-2">Mapa de Ocupação Hospitalar</h3>
          <p className="text-gray-600">Carregando mapa...</p>
        </div>
      </div>
    );
  }

  return (
    <div className={`w-full ${className}`}>
      <div className="mb-6 text-center">
        <h3 className="text-2xl font-bold mb-2">Mapa de Ocupação Hospitalar</h3>
        <p className="text-gray-600">Consulte a lotação em tempo real dos hospitais da rede</p>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Google Maps container */}
        <div className="col-span-2 bg-gray-100 rounded-lg overflow-hidden relative min-h-[300px] md:min-h-[400px] shadow-md">
          <GoogleMap
            mapContainerStyle={containerStyle}
            center={center}
            zoom={13}
            onLoad={onLoad}
            onUnmount={onUnmount}
            options={{
              fullscreenControl: false,
              zoomControl: true,
              streetViewControl: false,
              mapTypeControl: false
            }}
          >
            {/* Hospital markers */}
            {mockHospitals.map((hospital, index) => {
              const occupancy = getOccupancyPercentage(hospital.id);
              const position = getHospitalPosition(hospital.id, index);
              
              return (
                <Marker
                  key={hospital.id}
                  position={position}
                  onClick={() => setSelectedHospital(hospital.id)}
                  icon={{
                    path: "M-1.5,-5.7C-1.5,-5.7,-5,-1.1,-5,-1.1C-5,-1.1,-5,5.7,-5,5.7C-5,5.7,5,5.7,5,5.7C5,5.7,5,-1.1,5,-1.1C5,-1.1,1.5,-5.7,1.5,-5.7C1.5,-5.7,-1.5,-5.7,-1.5,-5.7",
                    fillColor: getBadgeColor(occupancy).replace('bg-', '').replace('-500', ''),
                    fillOpacity: 1,
                    strokeColor: '#FFFFFF',
                    strokeWeight: 2,
                    scale: 2,
                    anchor: new google.maps.Point(0, 0)
                  }}
                >
                  {selectedHospital === hospital.id && (
                    <InfoWindow
                      position={position}
                      onCloseClick={() => setSelectedHospital(null)}
                    >
                      <div className="p-2 min-w-[150px]">
                        <h4 className="font-bold">{hospital.name}</h4>
                        <div className="flex items-center justify-between mt-1">
                          <span>Ocupação:</span>
                          <Badge className={getBadgeColor(occupancy)}>{occupancy}%</Badge>
                        </div>
                      </div>
                    </InfoWindow>
                  )}
                </Marker>
              );
            })}
          </GoogleMap>
        </div>
        
        {/* Hospital information panel */}
        <div className="col-span-1">
          <Card className="h-full">
            <CardContent className="p-4">
              {selectedHospital ? (
                (() => {
                  const hospital = mockHospitals.find(h => h.id === selectedHospital);
                  if (!hospital) return <p>Hospital não encontrado</p>;
                  
                  const occupancy = getOccupancyPercentage(hospital.id);
                  const badgeColor = getBadgeColor(occupancy);
                  
                  return (
                    <div>
                      <h4 className="text-xl font-bold mb-3">{hospital.name}</h4>
                      <p className="text-gray-600 mb-2">{hospital.address}</p>
                      <p className="text-gray-600 mb-4">{hospital.contactNumber}</p>
                      
                      <div className="space-y-3 mt-6">
                        <div className="flex justify-between">
                          <span className="font-medium">Ocupação:</span>
                          <Badge className={badgeColor}>{occupancy}%</Badge>
                        </div>
                        <div className="flex justify-between">
                          <span className="font-medium">Leitos Totais:</span>
                          <span>{hospital.totalBeds}</span>
                        </div>
                        <div className="flex justify-between">
                          <span className="font-medium">Leitos Disponíveis:</span>
                          <span className="text-green-600 font-semibold">{hospital.availableBeds}</span>
                        </div>
                      </div>
                    </div>
                  );
                })()
              ) : (
                <div className="h-full flex flex-col items-center justify-center text-center p-4">
                  <MapPin className="h-12 w-12 text-blue-500 mb-4" />
                  <h4 className="text-lg font-bold mb-2">Selecione um Hospital</h4>
                  <p className="text-gray-500">Clique em um dos marcadores no mapa para ver informações detalhadas sobre a ocupação</p>
                </div>
              )}
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default HospitalMap;
