
import { useState } from "react";
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

// Definir o schema do formulário com base no modelo UML
const atendimentoFormSchema = z.object({
  paciente: z.string().min(2, {
    message: "Nome do paciente é obrigatório",
  }),
  andar: z.string().min(1, {
    message: "Andar é obrigatório",
  }),
  sala: z.string().min(1, {
    message: "Sala é obrigatória",
  }),
  tipo: z.string().min(1, {
    message: "Tipo é obrigatório",
  }),
  status: z.string().min(1, {
    message: "Status é obrigatório",
  }),
});

type AtendimentoFormValues = z.infer<typeof atendimentoFormSchema>;
type Atendimento = AtendimentoFormValues & { id: number; data: string };

const AtendimentosUTI = () => {
  const [atendimentos, setAtendimentos] = useState<Atendimento[]>([
    { 
      id: 1, 
      paciente: "João Silva", 
      andar: "3", 
      sala: "305", 
      tipo: "Emergência", 
      status: "Crítico",
      data: "2023-05-01T14:30:00"
    },
    { 
      id: 2, 
      paciente: "Maria Oliveira", 
      andar: "3", 
      sala: "307", 
      tipo: "Observação", 
      status: "Estável",
      data: "2023-05-02T09:15:00"
    },
    { 
      id: 3, 
      paciente: "Pedro Santos", 
      andar: "4", 
      sala: "410", 
      tipo: "Cirurgia", 
      status: "Em Observação",
      data: "2023-05-02T16:45:00"
    },
  ]);
  
  const [isOpen, setIsOpen] = useState(false);
  const [selectedAtendimento, setSelectedAtendimento] = useState<Atendimento | null>(null);
  
  const form = useForm<AtendimentoFormValues>({
    resolver: zodResolver(atendimentoFormSchema),
    defaultValues: {
      paciente: "",
      andar: "",
      sala: "",
      tipo: "",
      status: "",
    },
  });

  function handleEditAtendimento(atendimento: Atendimento) {
    setSelectedAtendimento(atendimento);
    form.reset({
      paciente: atendimento.paciente,
      andar: atendimento.andar,
      sala: atendimento.sala,
      tipo: atendimento.tipo,
      status: atendimento.status,
    });
    setIsOpen(true);
  }

  function handleAddAtendimento() {
    setSelectedAtendimento(null);
    form.reset({
      paciente: "",
      andar: "",
      sala: "",
      tipo: "",
      status: "",
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
    if (selectedAtendimento) {
      // Editar atendimento existente
      setAtendimentos(atendimentos.map(atendimento => 
        atendimento.id === selectedAtendimento.id 
          ? { ...atendimento, ...values } 
          : atendimento
      ));
      toast({
        title: "Atendimento atualizado",
        description: "O atendimento foi atualizado com sucesso.",
      });
    } else {
      // Adicionar novo atendimento
      const newId = Math.max(0, ...atendimentos.map(a => a.id)) + 1;
      const now = new Date().toISOString();
      setAtendimentos([...atendimentos, { id: newId, ...values, data: now }]);
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
                <TableHead>Andar/Sala</TableHead>
                <TableHead>Tipo</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Data</TableHead>
                <TableHead className="text-right">Ações</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {atendimentos.map(atendimento => (
                <TableRow key={atendimento.id}>
                  <TableCell>{atendimento.paciente}</TableCell>
                  <TableCell>{atendimento.andar} / {atendimento.sala}</TableCell>
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
                  <TableCell>{formatDate(atendimento.data)}</TableCell>
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
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="andar"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Andar</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="sala"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Sala</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              
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
