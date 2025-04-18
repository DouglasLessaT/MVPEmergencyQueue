
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

// Schema para o formulário de quarto
const roomFormSchema = z.object({
  codigo: z.string().min(1, {
    message: "Código do quarto é obrigatório",
  }),
  andar: z.string().min(1, {
    message: "Andar é obrigatório",
  }),
  predio: z.string().min(1, {
    message: "Prédio é obrigatório",
  }),
  tipoQuarto: z.string().min(1, {
    message: "Tipo de quarto é obrigatório",
  }),
  capacidadeLeitos: z.coerce.number().min(1, {
    message: "Capacidade de leitos deve ser pelo menos 1",
  }),
});

type RoomFormValues = z.infer<typeof roomFormSchema>;
type Room = RoomFormValues & { id: number; leitosAssociados: string[] };

// Chaves para armazenamento no localStorage
const ROOMS_STORAGE_KEY = "hospital_rooms";
const HOSPITAL_STORAGE_KEY = "hospital_config";

const RoomManagement = () => {
  const [rooms, setRooms] = useState<Room[]>([
    {
      id: 1,
      codigo: "Q101",
      andar: "1",
      predio: "A",
      tipoQuarto: "Enfermaria",
      capacidadeLeitos: 4,
      leitosAssociados: ["L001", "L002", "L003", "L004"]
    },
    {
      id: 2,
      codigo: "Q201",
      andar: "2",
      predio: "A",
      tipoQuarto: "Semi-Privativo",
      capacidadeLeitos: 2,
      leitosAssociados: ["L005", "L006"]
    },
    {
      id: 3,
      codigo: "Q301",
      andar: "3",
      predio: "B",
      tipoQuarto: "Privativo",
      capacidadeLeitos: 1,
      leitosAssociados: ["L007"]
    },
  ]);
  
  const [isOpen, setIsOpen] = useState(false);
  const [selectedRoom, setSelectedRoom] = useState<Room | null>(null);
  const [hospitalConfig, setHospitalConfig] = useState<any>(null);
  
  const form = useForm<RoomFormValues>({
    resolver: zodResolver(roomFormSchema),
    defaultValues: {
      codigo: "",
      andar: "",
      predio: "",
      tipoQuarto: "",
      capacidadeLeitos: 1
    },
  });

  // Carregar dados armazenados
  useEffect(() => {
    const storedRooms = getFromLocalStorage<Room[]>(ROOMS_STORAGE_KEY, []);
    if (storedRooms.length > 0) {
      setRooms(storedRooms);
    }

    const config = getFromLocalStorage(HOSPITAL_STORAGE_KEY, null);
    if (config) {
      setHospitalConfig(config);
    }
  }, []);

  // Salvar quartos quando atualizados
  useEffect(() => {
    if (rooms.length > 0) {
      saveToLocalStorage(ROOMS_STORAGE_KEY, rooms);
    }
  }, [rooms]);

  function handleEditRoom(room: Room) {
    setSelectedRoom(room);
    form.reset({
      codigo: room.codigo,
      andar: room.andar,
      predio: room.predio,
      tipoQuarto: room.tipoQuarto,
      capacidadeLeitos: room.capacidadeLeitos
    });
    setIsOpen(true);
  }

  function handleAddRoom() {
    setSelectedRoom(null);
    form.reset({
      codigo: "",
      andar: "",
      predio: "",
      tipoQuarto: "Enfermaria",
      capacidadeLeitos: 1
    });
    setIsOpen(true);
  }

  function handleDeleteRoom(id: number) {
    setRooms(rooms.filter(room => room.id !== id));
    toast({
      title: "Quarto removido",
      description: "O quarto foi removido com sucesso.",
    });
  }

  function onSubmit(values: RoomFormValues) {
    if (selectedRoom) {
      // Editar quarto existente
      setRooms(rooms.map(room => 
        room.id === selectedRoom.id 
          ? { ...room, ...values } 
          : room
      ));
      toast({
        title: "Quarto atualizado",
        description: "O quarto foi atualizado com sucesso.",
      });
    } else {
      // Verificar limitações do hospital
      let totalRooms = rooms.length + 1;
      let maxRooms = hospitalConfig?.numeroQuartos || 150;
      
      if (totalRooms > maxRooms) {
        toast({
          title: "Limite excedido",
          description: `O hospital tem um limite de ${maxRooms} quartos.`,
          variant: "destructive"
        });
        return;
      }

      // Adicionar novo quarto
      const newId = Math.max(0, ...rooms.map(r => r.id)) + 1;
      setRooms([...rooms, { id: newId, ...values, leitosAssociados: [] }]);
      toast({
        title: "Quarto adicionado",
        description: "O novo quarto foi adicionado com sucesso.",
      });
    }
    
    setIsOpen(false);
  }

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Gestão de Quartos</h1>
        <Button onClick={handleAddRoom}>
          <Plus className="h-4 w-4 mr-2" />
          Novo Quarto
        </Button>
      </div>
      
      <Card>
        <CardHeader>
          <CardTitle>Lista de Quartos</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Código</TableHead>
                <TableHead>Prédio/Andar</TableHead>
                <TableHead>Tipo</TableHead>
                <TableHead>Capacidade</TableHead>
                <TableHead>Leitos Associados</TableHead>
                <TableHead className="text-right">Ações</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {rooms.map(room => (
                <TableRow key={room.id}>
                  <TableCell>{room.codigo}</TableCell>
                  <TableCell>Prédio {room.predio} / Andar {room.andar}</TableCell>
                  <TableCell>{room.tipoQuarto}</TableCell>
                  <TableCell>{room.capacidadeLeitos} leitos</TableCell>
                  <TableCell>
                    <div className="flex flex-wrap gap-1">
                      {room.leitosAssociados.map(leito => (
                        <span key={leito} className="bg-gray-100 px-2 py-1 rounded text-xs">
                          {leito}
                        </span>
                      ))}
                      {room.leitosAssociados.length === 0 && 
                        <span className="text-gray-400">Nenhum leito</span>
                      }
                    </div>
                  </TableCell>
                  <TableCell className="text-right">
                    <Button variant="ghost" size="icon" onClick={() => handleEditRoom(room)}>
                      <Pencil className="h-4 w-4" />
                    </Button>
                    <Button variant="ghost" size="icon" onClick={() => handleDeleteRoom(room.id)}>
                      <Trash className="h-4 w-4" />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
              {rooms.length === 0 && (
                <TableRow>
                  <TableCell colSpan={6} className="text-center py-4 text-gray-500">
                    Nenhum quarto cadastrado
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
              {selectedRoom ? "Editar Quarto" : "Novo Quarto"}
            </DialogTitle>
          </DialogHeader>
          
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="codigo"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Código do Quarto</FormLabel>
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
                  name="predio"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Prédio</FormLabel>
                      <FormControl>
                        <Input {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
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
              </div>
              
              <FormField
                control={form.control}
                name="tipoQuarto"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Tipo de Quarto</FormLabel>
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
                        <SelectItem value="Privativo">Privativo</SelectItem>
                        <SelectItem value="Semi-Privativo">Semi-Privativo</SelectItem>
                        <SelectItem value="Enfermaria">Enfermaria</SelectItem>
                        <SelectItem value="UTI">UTI</SelectItem>
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="capacidadeLeitos"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Capacidade de Leitos</FormLabel>
                    <FormControl>
                      <Input type="number" min="1" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <DialogFooter>
                <Button type="button" variant="outline" onClick={() => setIsOpen(false)}>
                  Cancelar
                </Button>
                <Button type="submit">
                  {selectedRoom ? "Salvar" : "Adicionar"}
                </Button>
              </DialogFooter>
            </form>
          </Form>
          
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default RoomManagement;
