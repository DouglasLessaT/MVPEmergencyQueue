
import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { toast } from "@/components/ui/use-toast";
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
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Plus, Pencil, Trash } from "lucide-react";

// Definir o schema do formulário com base no modelo UML
const statusFormSchema = z.object({
  nome: z.string().min(2, {
    message: "Nome deve ter pelo menos 2 caracteres",
  }),
  codigo: z.string().min(1, {
    message: "Código é obrigatório",
  }),
  cor: z.string().min(1, {
    message: "Cor é obrigatória",
  }),
  entidade: z.enum(["Paciente", "Leito", "Transferência"], {
    required_error: "Tipo de entidade é obrigatório",
  }),
});

type StatusFormValues = z.infer<typeof statusFormSchema>;
type Status = StatusFormValues & { id: number };

const ENTITY_TYPES = ["Paciente", "Leito", "Transferência"] as const;

const COLOR_OPTIONS = {
  "bg-red-500": "Vermelho",
  "bg-yellow-500": "Amarelo",
  "bg-green-500": "Verde",
  "bg-blue-500": "Azul",
  "bg-purple-500": "Roxo",
  "bg-pink-500": "Rosa",
  "bg-orange-500": "Laranja",
  "bg-gray-500": "Cinza",
};

const StatusEntidade = () => {
  const [statuses, setStatuses] = useState<Status[]>([
    { id: 1, nome: "Crítico", codigo: "CRI", cor: "bg-red-500", entidade: "Paciente" },
    { id: 2, nome: "Estável", codigo: "EST", cor: "bg-blue-500", entidade: "Paciente" },
    { id: 3, nome: "Em Observação", codigo: "OBS", cor: "bg-yellow-500", entidade: "Paciente" },
    { id: 4, nome: "Disponível", codigo: "DISP", cor: "bg-green-500", entidade: "Leito" },
    { id: 5, nome: "Ocupado", codigo: "OCU", cor: "bg-red-500", entidade: "Leito" },
    { id: 6, nome: "Manutenção", codigo: "MAN", cor: "bg-gray-500", entidade: "Leito" },
    { id: 7, nome: "Transferindo", codigo: "TRA", cor: "bg-blue-500", entidade: "Transferência" },
    { id: 8, nome: "Aguardando", codigo: "AGU", cor: "bg-yellow-500", entidade: "Transferência" },
    { id: 9, nome: "Concluído", codigo: "CON", cor: "bg-green-500", entidade: "Transferência" },
  ]);
  
  const [isOpen, setIsOpen] = useState(false);
  const [selectedStatus, setSelectedStatus] = useState<Status | null>(null);
  const [filterEntity, setFilterEntity] = useState<string>("Todos");
  
  const form = useForm<StatusFormValues>({
    resolver: zodResolver(statusFormSchema),
    defaultValues: {
      nome: "",
      codigo: "",
      cor: "bg-gray-500",
      entidade: "Paciente",
    },
  });

  const filteredStatuses = filterEntity === "Todos" 
    ? statuses 
    : statuses.filter(status => status.entidade === filterEntity);

  function handleEditStatus(status: Status) {
    setSelectedStatus(status);
    form.reset({
      nome: status.nome,
      codigo: status.codigo,
      cor: status.cor,
      entidade: status.entidade,
    });
    setIsOpen(true);
  }

  function handleAddStatus() {
    setSelectedStatus(null);
    form.reset({
      nome: "",
      codigo: "",
      cor: "bg-gray-500",
      entidade: "Paciente",
    });
    setIsOpen(true);
  }

  function handleDeleteStatus(id: number) {
    setStatuses(statuses.filter(status => status.id !== id));
    toast({
      title: "Status removido",
      description: "O status foi removido com sucesso.",
    });
  }

  function onSubmit(values: StatusFormValues) {
    if (selectedStatus) {
      // Editar status existente
      setStatuses(statuses.map(status => 
        status.id === selectedStatus.id ? { ...status, ...values } : status
      ));
      toast({
        title: "Status atualizado",
        description: "O status foi atualizado com sucesso.",
      });
    } else {
      // Adicionar novo status
      const newId = Math.max(0, ...statuses.map(s => s.id)) + 1;
      setStatuses([...statuses, { id: newId, ...values }]);
      toast({
        title: "Status adicionado",
        description: "O novo status foi adicionado com sucesso.",
      });
    }
    
    setIsOpen(false);
  }

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Status de Entidades</h1>
        <Button onClick={handleAddStatus}>
          <Plus className="h-4 w-4 mr-2" />
          Novo Status
        </Button>
      </div>
      
      <div className="mb-6">
        <Card>
          <CardContent className="pt-6">
            <div className="flex justify-between items-center">
              <h2 className="text-lg font-semibold">Filtrar por Entidade</h2>
              <Select
                value={filterEntity}
                onValueChange={setFilterEntity}
              >
                <SelectTrigger className="w-[180px]">
                  <SelectValue placeholder="Filtrar por entidade" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="Todos">Todos</SelectItem>
                  {ENTITY_TYPES.map((type) => (
                    <SelectItem key={type} value={type}>
                      {type}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </CardContent>
        </Card>
      </div>
      
      <Card>
        <CardHeader>
          <CardTitle>Lista de Status</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Nome</TableHead>
                <TableHead>Código</TableHead>
                <TableHead>Cor</TableHead>
                <TableHead>Entidade</TableHead>
                <TableHead className="text-right">Ações</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {filteredStatuses.map(status => (
                <TableRow key={status.id}>
                  <TableCell>{status.nome}</TableCell>
                  <TableCell>{status.codigo}</TableCell>
                  <TableCell>
                    <div 
                      className={`w-8 h-8 rounded-full ${status.cor}`} 
                      title={COLOR_OPTIONS[status.cor as keyof typeof COLOR_OPTIONS] || ""}
                    />
                  </TableCell>
                  <TableCell>{status.entidade}</TableCell>
                  <TableCell className="text-right">
                    <Button variant="ghost" size="icon" onClick={() => handleEditStatus(status)}>
                      <Pencil className="h-4 w-4" />
                    </Button>
                    <Button variant="ghost" size="icon" onClick={() => handleDeleteStatus(status.id)}>
                      <Trash className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              {selectedStatus ? "Editar Status" : "Novo Status"}
            </DialogTitle>
          </DialogHeader>
          
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="nome"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nome</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="codigo"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Código</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="entidade"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Tipo de Entidade</FormLabel>
                    <Select 
                      onValueChange={field.onChange} 
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione um tipo" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {ENTITY_TYPES.map((type) => (
                          <SelectItem key={type} value={type}>
                            {type}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="cor"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Cor</FormLabel>
                    <Select 
                      onValueChange={field.onChange} 
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecione uma cor" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {Object.entries(COLOR_OPTIONS).map(([value, label]) => (
                          <SelectItem key={value} value={value}>
                            <div className="flex items-center">
                              <div className={`w-4 h-4 rounded-full ${value} mr-2`} />
                              <span>{label}</span>
                            </div>
                          </SelectItem>
                        ))}
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
                  {selectedStatus ? "Salvar" : "Adicionar"}
                </Button>
              </DialogFooter>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default StatusEntidade;
