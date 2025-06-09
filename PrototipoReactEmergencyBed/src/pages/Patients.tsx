
import { useState } from "react";
import { Patient, mockPatients } from "@/data/mockData";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import PatientCard from "@/components/PatientCard";
import { Search, Plus, User } from "lucide-react";
import { useToast } from "@/components/ui/use-toast";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";

const Patients = () => {
  const { toast } = useToast();
  const [searchQuery, setSearchQuery] = useState("");
  const [activeTab, setActiveTab] = useState("all");
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [selectedPatient, setSelectedPatient] = useState<Patient | null>(null);

  const filteredPatients = mockPatients.filter((patient) => {
    const matchesSearch = patient.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
                        patient.diagnosis.toLowerCase().includes(searchQuery.toLowerCase());
    
    if (activeTab === "all") return matchesSearch;
    if (activeTab === "critical") return matchesSearch && patient.status === "Critical";
    if (activeTab === "stable") return matchesSearch && patient.status === "Stable";
    if (activeTab === "healthy") return matchesSearch && patient.status === "Healthy";
    
    return matchesSearch;
  });

  const handleEdit = (patient: Patient) => {
    setSelectedPatient(patient);
    setIsDialogOpen(true);
  };

  const handleDelete = (patient: Patient) => {
    toast({
      title: "Paciente excluído",
      description: `${patient.name} foi removido com sucesso.`,
    });
  };

  return (
    <div className="container mx-auto px-4 py-6 space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between">
        <h1 className="text-2xl font-bold">Pacientes</h1>
        <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
          <DialogTrigger asChild>
            <Button className="mt-4 md:mt-0 bg-hospital-blue hover:bg-hospital-blue/90">
              <Plus className="mr-2 h-4 w-4" />
              Novo Paciente
            </Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-lg">
            <DialogHeader>
              <DialogTitle>{selectedPatient ? 'Editar Paciente' : 'Novo Paciente'}</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="flex justify-center mb-4">
                <div className="h-24 w-24 rounded-full bg-gray-200 flex items-center justify-center">
                  <User className="h-12 w-12 text-gray-500" />
                </div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <label htmlFor="name" className="text-sm font-medium">Nome</label>
                  <Input id="name" defaultValue={selectedPatient?.name} />
                </div>
                <div className="space-y-2">
                  <label htmlFor="age" className="text-sm font-medium">Idade</label>
                  <Input id="age" type="number" defaultValue={selectedPatient?.age} />
                </div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <label htmlFor="gender" className="text-sm font-medium">Gênero</label>
                  <select id="gender" className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                    <option value="Male" selected={selectedPatient?.gender === "Male"}>Masculino</option>
                    <option value="Female" selected={selectedPatient?.gender === "Female"}>Feminino</option>
                    <option value="Other" selected={selectedPatient?.gender === "Other"}>Outro</option>
                  </select>
                </div>
                <div className="space-y-2">
                  <label htmlFor="status" className="text-sm font-medium">Status</label>
                  <select id="status" className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50">
                    <option value="Critical" selected={selectedPatient?.status === "Critical"}>Crítico</option>
                    <option value="Stable" selected={selectedPatient?.status === "Stable"}>Estável</option>
                    <option value="Healthy" selected={selectedPatient?.status === "Healthy"}>Saudável</option>
                  </select>
                </div>
              </div>
              <div className="space-y-2">
                <label htmlFor="diagnosis" className="text-sm font-medium">Diagnóstico</label>
                <Input id="diagnosis" defaultValue={selectedPatient?.diagnosis} />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <label htmlFor="room" className="text-sm font-medium">Quarto</label>
                  <Input id="room" defaultValue={selectedPatient?.room} />
                </div>
                <div className="space-y-2">
                  <label htmlFor="admission" className="text-sm font-medium">Data de Admissão</label>
                  <Input id="admission" type="date" defaultValue={selectedPatient?.admissionDate} />
                </div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <label htmlFor="contact" className="text-sm font-medium">Contato</label>
                  <Input id="contact" defaultValue={selectedPatient?.contactNumber} />
                </div>
                <div className="space-y-2">
                  <label htmlFor="emergency" className="text-sm font-medium">Contato de Emergência</label>
                  <Input id="emergency" defaultValue={selectedPatient?.emergencyContact} />
                </div>
              </div>
            </div>
            <div className="flex justify-end space-x-2">
              <Button 
                variant="outline" 
                onClick={() => setIsDialogOpen(false)}
              >
                Cancelar
              </Button>
              <Button 
                className="bg-hospital-blue hover:bg-hospital-blue/90"
                onClick={() => {
                  toast({
                    title: selectedPatient ? "Paciente atualizado" : "Paciente adicionado",
                    description: selectedPatient 
                      ? `${selectedPatient.name} foi atualizado com sucesso.` 
                      : "Novo paciente adicionado com sucesso.",
                  });
                  setIsDialogOpen(false);
                }}
              >
                {selectedPatient ? 'Salvar Alterações' : 'Adicionar Paciente'}
              </Button>
            </div>
          </DialogContent>
        </Dialog>
      </div>

      <Card>
        <CardContent className="pt-6">
          <div className="flex items-center space-x-2 mb-6">
            <div className="relative flex-1">
              <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-500" />
              <Input
                type="search"
                placeholder="Buscar pacientes por nome ou diagnóstico..."
                className="pl-8"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </div>
          </div>

          <Tabs defaultValue="all" value={activeTab} onValueChange={setActiveTab}>
            <TabsList>
              <TabsTrigger value="all">Todos</TabsTrigger>
              <TabsTrigger value="critical" className="text-hospital-red">Críticos</TabsTrigger>
              <TabsTrigger value="stable" className="text-hospital-blue">Estáveis</TabsTrigger>
              <TabsTrigger value="healthy" className="text-hospital-green">Saudáveis</TabsTrigger>
            </TabsList>
            <TabsContent value="all" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
                {filteredPatients.length > 0 ? (
                  filteredPatients.map((patient) => (
                    <PatientCard
                      key={patient.id}
                      patient={patient}
                      onEdit={handleEdit}
                      onDelete={handleDelete}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Nenhum paciente encontrado</p>
                  </div>
                )}
              </div>
            </TabsContent>
            <TabsContent value="critical" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
                {filteredPatients.length > 0 ? (
                  filteredPatients.map((patient) => (
                    <PatientCard
                      key={patient.id}
                      patient={patient}
                      onEdit={handleEdit}
                      onDelete={handleDelete}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Nenhum paciente crítico encontrado</p>
                  </div>
                )}
              </div>
            </TabsContent>
            <TabsContent value="stable" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
                {filteredPatients.length > 0 ? (
                  filteredPatients.map((patient) => (
                    <PatientCard
                      key={patient.id}
                      patient={patient}
                      onEdit={handleEdit}
                      onDelete={handleDelete}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Nenhum paciente estável encontrado</p>
                  </div>
                )}
              </div>
            </TabsContent>
            <TabsContent value="healthy" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-4">
                {filteredPatients.length > 0 ? (
                  filteredPatients.map((patient) => (
                    <PatientCard
                      key={patient.id}
                      patient={patient}
                      onEdit={handleEdit}
                      onDelete={handleDelete}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Nenhum paciente saudável encontrado</p>
                  </div>
                )}
              </div>
            </TabsContent>
          </Tabs>
        </CardContent>
      </Card>
    </div>
  );
};

export default Patients;
