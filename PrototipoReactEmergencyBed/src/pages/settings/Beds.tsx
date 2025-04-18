
import { useState } from "react";
import { Bed, getBedsByFloor, mockBeds, getPatientByBedId } from "@/data/mockData";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import BedCard from "@/components/BedCard";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import StatusBadge from "@/components/StatusBadge";
import { useToast } from "@/components/ui/use-toast";
import { Hospital, User, PlusCircle } from "lucide-react";
import { Link } from "react-router-dom";

const Beds = () => {
  const { toast } = useToast();
  const [selectedFloor, setSelectedFloor] = useState("1");
  const [selectedBed, setSelectedBed] = useState<Bed | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const beds = getBedsByFloor(parseInt(selectedFloor));
  
  const availableBeds = beds.filter(bed => bed.status === "Available");
  const occupiedBeds = beds.filter(bed => bed.status === "Occupied");
  const maintenanceBeds = beds.filter(bed => bed.status === "Maintenance");

  const patient = selectedBed?.patientId 
    ? getPatientByBedId(selectedBed.id)
    : null;

  const handleBedClick = (bed: Bed) => {
    setSelectedBed(bed);
    setIsDialogOpen(true);
  };

  const handleChangeStatus = (status: "Available" | "Occupied" | "Maintenance") => {
    // In a real app, this would update the database
    toast({
      title: `Status alterado`,
      description: `Leito ${selectedBed?.room} alterado para ${status}`,
    });
    setIsDialogOpen(false);
  };

  return (
    <div className="container mx-auto px-4 py-6 space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">Gestão de Leitos</h1>
        <Link to="/settings/bed-management">
          <Button variant="outline" className="gap-2">
            <PlusCircle className="h-5 w-5" />
            Cadastrar Leitos
          </Button>
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-hospital-green flex items-center gap-2">
              <div className="w-3 h-3 rounded-full bg-hospital-green"></div>
              Disponíveis
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold">{availableBeds.length}</p>
            <p className="text-sm text-gray-500">
              {((availableBeds.length / beds.length) * 100).toFixed(0)}% do andar
            </p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-hospital-red flex items-center gap-2">
              <div className="w-3 h-3 rounded-full bg-hospital-red"></div>
              Ocupados
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold">{occupiedBeds.length}</p>
            <p className="text-sm text-gray-500">
              {((occupiedBeds.length / beds.length) * 100).toFixed(0)}% do andar
            </p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-gray-500 flex items-center gap-2">
              <div className="w-3 h-3 rounded-full bg-gray-500"></div>
              Em Manutenção
            </CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold">{maintenanceBeds.length}</p>
            <p className="text-sm text-gray-500">
              {((maintenanceBeds.length / beds.length) * 100).toFixed(0)}% do andar
            </p>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardContent className="p-6">
          <Tabs defaultValue="1" value={selectedFloor} onValueChange={setSelectedFloor}>
            <TabsList>
              <TabsTrigger value="1">1º Andar</TabsTrigger>
              <TabsTrigger value="2">2º Andar</TabsTrigger>
            </TabsList>
            <TabsContent value="1" className="mt-6">
              <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-4">
                {beds.map((bed) => (
                  <BedCard key={bed.id} bed={bed} onClick={handleBedClick} />
                ))}
              </div>
            </TabsContent>
            <TabsContent value="2" className="mt-6">
              <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-4">
                {beds.map((bed) => (
                  <BedCard key={bed.id} bed={bed} onClick={handleBedClick} />
                ))}
              </div>
            </TabsContent>
          </Tabs>
        </CardContent>
      </Card>

      {/* Bed Details Dialog */}
      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle className="flex items-center gap-2">
              <Hospital className="h-5 w-5" />
              Leito {selectedBed?.room}
            </DialogTitle>
          </DialogHeader>
          <div className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="font-medium">Status:</span>
              <StatusBadge status={selectedBed?.status || "Available"} />
            </div>
            <div>
              <span className="font-medium">Andar:</span> {selectedBed?.floor}
            </div>
            {patient ? (
              <div className="border rounded-md p-4 mt-4 space-y-3">
                <div className="flex items-center gap-3">
                  <div className="h-10 w-10 rounded-full bg-gray-200 flex items-center justify-center">
                    <User className="h-6 w-6 text-gray-500" />
                  </div>
                  <div>
                    <h3 className="font-semibold">{patient.name}</h3>
                    <p className="text-sm text-gray-600">{patient.diagnosis}</p>
                  </div>
                </div>
                <div className="grid grid-cols-2 gap-2 text-sm">
                  <div>
                    <span className="font-medium">Idade:</span> {patient.age}
                  </div>
                  <div>
                    <span className="font-medium">Gênero:</span> {patient.gender}
                  </div>
                  <div>
                    <span className="font-medium">Admissão:</span> {patient.admissionDate}
                  </div>
                  <div>
                    <span className="font-medium">Status:</span> {patient.status}
                  </div>
                </div>
              </div>
            ) : (
              <div className="text-center p-4 bg-gray-50 rounded-md">
                <p className="text-gray-500">Leito não ocupado</p>
              </div>
            )}

            <div className="flex justify-end space-x-2 pt-4">
              {selectedBed?.status !== "Available" && (
                <Button
                  variant="outline"
                  className="border-hospital-green text-hospital-green hover:bg-hospital-green/10"
                  onClick={() => handleChangeStatus("Available")}
                >
                  Marcar como Disponível
                </Button>
              )}
              {selectedBed?.status !== "Occupied" && (
                <Button
                  variant="outline"
                  className="border-hospital-red text-hospital-red hover:bg-hospital-red/10"
                  onClick={() => handleChangeStatus("Occupied")}
                >
                  Marcar como Ocupado
                </Button>
              )}
              {selectedBed?.status !== "Maintenance" && (
                <Button
                  variant="outline"
                  className="border-gray-500 text-gray-500 hover:bg-gray-100"
                  onClick={() => handleChangeStatus("Maintenance")}
                >
                  Marcar para Manutenção
                </Button>
              )}
            </div>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default Beds;
