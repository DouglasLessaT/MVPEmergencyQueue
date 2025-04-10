
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { toast } from "@/components/ui/use-toast";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Plus, Pencil, Trash } from "lucide-react";
import { getFromLocalStorage, saveToLocalStorage } from "@/utils/localStorage";
import { Patient } from "@/data/mockData";

// Definir o schema do formulário com base no modelo UML
const atendimentoFormSchema = z.object({
  paciente: z.string().min(2, {
    message: "Nome do paciente é obrigatório",
  }),
  leito: z.string().min(1, {
    message: "Leito é obrigatório",
  }),
  tipo: z.string().min(1, {
    message: "Tipo é obrigatório",
  }),
  status: z.string().min(1, {
    message: "Status é obrigatório",
  }),
  diasEstadia: z.coerce.number().min(1, {
    message: "Dias de estadia deve ser pelo menos 1",
  }),
  dataEntrada: z.string().min(1, {
    message: "Data de entrada é obrigatória",
  }),
  horaEntrada: z.string().min(1, {
    message: "Hora de entrada é obrigatória",
  }),
});

type AtendimentoFormValues = z.infer<typeof atendimentoFormSchema>;
type Atendimento = AtendimentoFormValues & { id: number; dataCompleta: string };

// Chaves para armazenamento no localStorage
const ATENDIMENTOS_STORAGE_KEY = "hospital_atendimentos";
const BEDS_STORAGE_KEY = "emergencybed_beds";

interface PatientOption {
  id: string;
  name: string;
}

interface BedOption {
  id: string;
  room: string;
  status: string;
}

const AtendimentosUTI = () => {
  const [atendimentos, setAtendimentos] = useState<Atendimento[]>([
    { 
      id: 1, 
      paciente: "João Silva", 
      leito: "L001", 
      tipo: "Emergência", 
      status: "Crítico",
      diasEstadia: 5,
      dataEntrada: "2023-05-01",
      horaEntrada: "14:30",
      dataCompleta: "2023-05-01T14:30:00"
    },
    { 
      id: 2, 
      paciente: "Maria Oliveira", 
      leito: "L003", 
      tipo: "Observação", 
      status: "Estável",
      diasEstadia: 3,
      dataEntrada: "2023-05-02",
      horaEntrada: "09:15",
      dataCompleta: "2023-05-02T09:15:00"
    },
    { 
      id: 3, 
      paciente: "Pedro Santos", 
      leito: "L007", 
      tipo: "Cirurgia", 
      status: "Em Observação",
      diasEstadia: 7,
      dataEntrada: "2023-05-02",
      horaEntrada: "16:45",
      dataCompleta: "2023-05-02T16:45:00"
    },
  ]);
  
  const [isOpen, setIsOpen] = useState(false);
  const [selectedAtendimento, setSelectedAtendimento] = useState<Atendimento | null>(null);
  const [patientOptions, setPatientOptions] = useState<PatientOption[]>([]);
  const [bedOptions, setBedOptions] = useState<BedOption[]>([]);
  const [occupiedBeds, setOccupiedBeds] = useState<string[]>([]);
  
  const form = useForm<AtendimentoFormValues>({
    resolver: zodResolver(atendimentoFormSchema),
    defaultValues: {
      paciente: "",
      leito: "",
      tipo: "",
      status: "",
      diasEstadia: 1,
      dataEntrada: new Date().toISOString().split('T')[0],
      horaEntrada: new Date().toTimeString().slice(0, 5)
    },
  });

  // Carregar dados armazenados
  useEffect(() => {
    // Carregar atendimentos
    const storedAtendimentos = getFromLocalStorage<Atendimento[]>(ATENDIMENTOS_STORAGE_KEY, []);
    if (storedAtendimentos.length > 0) {
      setAtendimentos(storedAtendimentos);
      
      // Extrair leitos ocupados
      const ocupados = storedAtendimentos.map(atendimento => atendimento.leito);
      setOccupiedBeds(ocupados);
    }

    // Carregar pacientes (mock data para demonstração)
    const mockPatients = [
      { id: "P001", name: "João Silva" },
      { id: "P002", name: "Maria Oliveira" },
      { id: "P003", name: "Pedro Santos" },
      { id: "P004", name: "Ana Costa" },
      { id: "P005", name: "Carlos Ferreira" }
    ];
    setPatientOptions(mockPatients);
    
    // Carregar leitos
    const storedBeds = getFromLocalStorage<any[]>(BEDS_STORAGE_KEY, []);
    if (storedBeds.length > 0) {
      const formattedBeds = storedBeds.map(bed => ({
        id: bed.id,
        room: bed.room || "Sem quarto",
        status: bed.status
      }));
      setBedOptions(formattedBeds);
    } else {
      // Leitos mockados se não houver dados
      setBedOptions([
        { id: "L001", room: "Q101", status: "Disponível" },
        { id: "L002", room: "Q101", status: "Disponível" },
        { id: "L003", room: "Q101", status: "Disponível" },
        { id: "L004", room: "Q101", status: "Disponível" },
        { id: "L005", room: "Q201", status: "Disponível" },
        { id: "L006", room: "Q201", status: "Disponível" },
        { id: "L007", room: "Q301", status: "Disponível" }
      ]);
    }
  }, []);

  // Salvar atendimentos quando atualizados
  useEffect(() => {
    if (atendimentos.length > 0) {
      saveToLocalStorage(ATENDIMENTOS_STORAGE_KEY, atendimentos);
      
      // Atualizar leitos ocupados
      const ocupados = atendimentos.map(atendimento => atendimento.leito);
      setOccupiedBeds(ocupados);
    }
  }, [atendimentos]);

  function handleEditAtendimento(atendimento: Atendimento) {
    setSelectedAtendimento(atendimento);
    
    // Extrair data e hora
    const dataEntrada = atendimento.dataEntrada || atendimento.dataCompleta.split('T')[0];
    const horaEntrada = atendimento.horaEntrada || atendimento.dataCompleta.split('T')[1].substring(0, 5);
    
    form.reset({
      paciente: atendimento.paciente,
      leito: atendimento.leito,
      tipo: atendimento.tipo,
      status: atendimento.status,
      diasEstadia: atendimento.diasEstadia,
      dataEntrada,
      horaEntrada
    });
    setIsOpen(true);
  }

  function handleAddAtendimento() {
    setSelectedAtendimento(null);
    form.reset({
      paciente: "",
      leito: "",
      tipo: "",
      status: "",
      diasEstadia: 1,
      dataEntrada: new Date().toISOString().split('T')[0],
      horaEntrada: new Date().toTimeString().slice(0, 5)
    });
    setIsOpen(true);
  }

  function handleDeleteAtendimento(id: number) {
    setAtendimentos(atendimentos.filter(atendimento => atendimento.id !== id));
    toast({
      title: "Atendimento removido",
      description: "O atendimento foi removido com sucesso.",
    });
  }

  function onSubmit(values: AtendimentoFormValues) {
    // Verificar se o leito já está ocupado (exceto se for edição do mesmo atendimento)
    const leitoJaOcupado = occupiedBeds.includes(values.leito) && 
      (!selectedAtendimento || selectedAtendimento.leito !== values.leito);
    
    if (leitoJaOcupado) {
      toast({
        title: "Leito ocupado",
        description: "Este leito já está ocupado por outro paciente.",
        variant: "destructive"
      });
      return;
    }

    // Combinar data e hora para formar dataCompleta
    const dataCompleta = `${values.dataEntrada}T${values.horaEntrada}:00`;

    if (selectedAtendimento) {
      // Editar atendimento existente
      setAtendimentos(atendimentos.map(atendimento => 
        atendimento.id === selectedAtendimento.id 
          ? { ...atendimento, ...values, dataCompleta } 
          : atendimento
      ));
      toast({
        title: "Atendimento atualizado",
        description: "O atendimento foi atualizado com sucesso.",
      });
    } else {
      // Adicionar novo atendimento
      const newId = Math.max(0, ...atendimentos.map(a => a.id)) + 1;
      setAtendimentos([...atendimentos, { id: newId, ...values, dataCompleta }]);
      toast({
        title: "Atendimento adicionado",
        description: "O novo atendimento foi adicionado com sucesso.",
      });
    }
    
    setIsOpen(false);
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

  // Filtra leitos disponíveis (não ocupados por outros atendimentos)
  const getAvailableBeds = () => {
    if (!selectedAtendimento) {
      // Para novo atendimento, mostrar apenas leitos não ocupados
      return bedOptions.filter(bed => !occupiedBeds.includes(bed.id));
    } else {
      // Para edição, mostrar o leito atual e leitos não ocupados
      return bedOptions.filter(bed => 
        !occupiedBeds.includes(bed.id) || bed.id === selectedAtendimento.leito
      );
    }
  };

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Atendimentos UTI</h1>
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
                  <TableCell>{atendimento.tipo}</TableCell>
                  <TableCell>
                    <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                      atendimento.status === "Crítico" ? "bg-red-100 text-red-800" :
                      atendimento.status === "Estável" ? "bg-green-100 text-green-800" :
                      "bg-yellow-100 text-yellow-800"
                    }`}>
                      {atendimento.status}
                    </span>
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
                  <TableCell colSpan={7} className="text-center py-4 text-gray-500">
                    Nenhum atendimento cadastrado
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              {selectedAtendimento ? "Editar Atendimento" : "Novo Atendimento"}
            </DialogTitle>
          </DialogHeader>
          
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="paciente"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Paciente</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione o paciente" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {patientOptions.map(patient => (
                          <SelectItem key={patient.id} value={patient.name}>{patient.name}</SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="leito"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Leito</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione o leito" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {getAvailableBeds().map(bed => (
                          <SelectItem key={bed.id} value={bed.id}>
                            {bed.id} (Quarto: {bed.room})
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="dataEntrada"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Data de Entrada</FormLabel>
                      <FormControl>
                        <Input type="date" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="horaEntrada"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Hora de Entrada</FormLabel>
                      <FormControl>
                        <Input type="time" {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              
              <FormField
                control={form.control}
                name="diasEstadia"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Dias de Estadia</FormLabel>
                    <FormControl>
                      <Input type="number" min="1" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="tipo"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Tipo</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione o tipo" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="Emergência">Emergência</SelectItem>
                        <SelectItem value="Observação">Observação</SelectItem>
                        <SelectItem value="Cirurgia">Cirurgia</SelectItem>
                        <SelectItem value="Pós-Operatório">Pós-Operatório</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="status"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Status</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione o status" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        <SelectItem value="Crítico">Crítico</SelectItem>
                        <SelectItem value="Estável">Estável</SelectItem>
                        <SelectItem value="Em Observação">Em Observação</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <DialogFooter>
                <Button type="button" variant="outline" onClick={() => setIsOpen(false)}>
                  Cancelar
                </Button>
                <Button type="submit">
                  {selectedAtendimento ? "Salvar" : "Adicionar"}
                </Button>
              </DialogFooter>
            </form>
          </Form>
          
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default AtendimentosUTI;