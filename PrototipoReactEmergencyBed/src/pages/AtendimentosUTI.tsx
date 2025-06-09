
import { useState, useEffect } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { toast } from "@/components/ui/use-toast";
import { Button } from "@/components/ui/button";
import { Plus, Pencil, Trash } from "lucide-react";
import { getFromLocalStorage, saveToLocalStorage } from "@/utils/localStorage";
import AttendanceForm from "@/components/AttendanceForm";

// Storage key
const ATENDIMENTOS_STORAGE_KEY = "hospital_atendimentos";

interface Atendimento {
  id: number;
  patientId: string;
  bedId: string;
  type: string;
  status: string;
  isUTI: boolean;
  stayDays: number;
  admissionDate: string;
  admissionTime: string;
  dataCompleta: string;
  // Additional fields for display purposes
  paciente: string;
  leito: string;
  diasEstadia: number;
}

const AtendimentosUTI = () => {
  const [atendimentos, setAtendimentos] = useState<Atendimento[]>([
    { 
      id: 1, 
      patientId: "P001",
      bedId: "L001", 
      paciente: "João Silva", 
      leito: "L001", 
      type: "Emergência", 
      status: "Critical",
      isUTI: true,
      stayDays: 5,
      diasEstadia: 5,
      admissionDate: "2023-05-01",
      admissionTime: "14:30",
      dataCompleta: "2023-05-01T14:30:00"
    },
    { 
      id: 2, 
      patientId: "P002",
      bedId: "L003", 
      paciente: "Maria Oliveira", 
      leito: "L003", 
      type: "Observação", 
      status: "Stable",
      isUTI: false,
      stayDays: 3,
      diasEstadia: 3,
      admissionDate: "2023-05-02",
      admissionTime: "09:15",
      dataCompleta: "2023-05-02T09:15:00"
    },
    { 
      id: 3, 
      patientId: "P003",
      bedId: "L007", 
      paciente: "Pedro Santos", 
      leito: "L007", 
      type: "Cirurgia", 
      status: "Observation",
      isUTI: true,
      stayDays: 7,
      diasEstadia: 7,
      admissionDate: "2023-05-02",
      admissionTime: "16:45",
      dataCompleta: "2023-05-02T16:45:00"
    },
  ]);
  
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [selectedAtendimento, setSelectedAtendimento] = useState<Atendimento | null>(null);
  const [occupiedBeds, setOccupiedBeds] = useState<string[]>([]);
  
  // Carregar dados armazenados
  useEffect(() => {
    // Carregar atendimentos
    const storedAtendimentos = getFromLocalStorage<Atendimento[]>(ATENDIMENTOS_STORAGE_KEY, []);
    if (storedAtendimentos.length > 0) {
      setAtendimentos(storedAtendimentos);
      
      // Extrair leitos ocupados
      const ocupados = storedAtendimentos.map(atendimento => atendimento.bedId);
      setOccupiedBeds(ocupados);
    }
  }, []);

  // Salvar atendimentos quando atualizados
  useEffect(() => {
    if (atendimentos.length > 0) {
      saveToLocalStorage(ATENDIMENTOS_STORAGE_KEY, atendimentos);
      
      // Atualizar leitos ocupados
      const ocupados = atendimentos.map(atendimento => atendimento.bedId);
      setOccupiedBeds(ocupados);
    }
  }, [atendimentos]);

  function handleEditAtendimento(atendimento: Atendimento) {
    setSelectedAtendimento(atendimento);
    setIsDialogOpen(true);
  }

  function handleAddAtendimento() {
    setSelectedAtendimento(null);
    setIsDialogOpen(true);
  }

  function handleDeleteAtendimento(id: number) {
    setAtendimentos(atendimentos.filter(atendimento => atendimento.id !== id));
    toast({
      title: "Atendimento removido",
      description: "O atendimento foi removido com sucesso.",
    });
  }

  function handleFormClose() {
    setIsDialogOpen(false);
  }

  function handleFormSubmit(values: any) {
    // Map from patient name to patient ID if needed
    const dataCompleta = `${values.admissionDate}T${values.admissionTime}:00`;
    
    // Get patient name from patientId
    const patient = values.patientId.split(' - ')[0];
    
    if (selectedAtendimento) {
      // Editar atendimento existente
      setAtendimentos(atendimentos.map(atendimento => 
        atendimento.id === selectedAtendimento.id 
          ? { 
              ...atendimento, 
              ...values, 
              paciente: patient,
              leito: values.bedId,
              diasEstadia: values.stayDays,
              dataCompleta 
            } 
          : atendimento
      ));
      toast({
        title: "Atendimento atualizado",
        description: "O atendimento foi atualizado com sucesso.",
      });
    } else {
      // Adicionar novo atendimento
      const newId = Math.max(0, ...atendimentos.map(a => a.id)) + 1;
      setAtendimentos([
        ...atendimentos, 
        { 
          id: newId, 
          ...values, 
          paciente: patient,
          leito: values.bedId,
          diasEstadia: values.stayDays,
          dataCompleta 
        }
      ]);
      toast({
        title: "Atendimento adicionado",
        description: "O novo atendimento foi adicionado com sucesso.",
      });
    }
    
    setIsDialogOpen(false);
  }

  function formatDate(dateString: string) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    }).format(date);
  }

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Cadastro de Atendimentos</h1>
        <Button onClick={handleAddAtendimento}>
          <Plus className="h-4 w-4 mr-2" />
          Novo Atendimento
        </Button>
      </div>
      
      <Card>
        <CardHeader>
          <CardTitle>Lista de Atendimentos</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Paciente</TableHead>
                <TableHead>Leito</TableHead>
                <TableHead>Tipo</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>UTI</TableHead>
                <TableHead>Estadia</TableHead>
                <TableHead>Entrada</TableHead>
                <TableHead className="text-right">Ações</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {atendimentos.map(atendimento => (
                <TableRow key={atendimento.id}>
                  <TableCell>{atendimento.paciente}</TableCell>
                  <TableCell>{atendimento.leito}</TableCell>
                  <TableCell>{atendimento.type}</TableCell>
                  <TableCell>
                    <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                      atendimento.status === "Critical" ? "bg-red-100 text-red-800" :
                      atendimento.status === "Stable" ? "bg-green-100 text-green-800" :
                      "bg-yellow-100 text-yellow-800"
                    }`}>
                      {atendimento.status}
                    </span>
                  </TableCell>
                  <TableCell>
                    {atendimento.isUTI ? (
                      <span className="px-2 py-1 rounded-full text-xs font-medium bg-purple-100 text-purple-800">
                        Sim
                      </span>
                    ) : (
                      <span className="px-2 py-1 rounded-full text-xs font-medium bg-gray-100 text-gray-800">
                        Não
                      </span>
                    )}
                  </TableCell>
                  <TableCell>{atendimento.diasEstadia} dias</TableCell>
                  <TableCell>{formatDate(atendimento.dataCompleta)}</TableCell>
                  <TableCell className="text-right">
                    <Button variant="ghost" size="icon" onClick={() => handleEditAtendimento(atendimento)}>
                      <Pencil className="h-4 w-4" />
                    </Button>
                    <Button variant="ghost" size="icon" onClick={() => handleDeleteAtendimento(atendimento.id)}>
                      <Trash className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
              {atendimentos.length === 0 && (
                <TableRow>
                  <TableCell colSpan={8} className="text-center py-4 text-gray-500">
                    Nenhum atendimento cadastrado
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {/* Using the AttendanceForm component */}
      <AttendanceForm 
        isOpen={isDialogOpen} 
        onClose={handleFormClose} 
      />
    </div>
  );
};

export default AtendimentosUTI;