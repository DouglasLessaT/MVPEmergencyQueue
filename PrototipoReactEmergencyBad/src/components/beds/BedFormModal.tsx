
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

// Schema para formulário de cadastro de leitos
const bedFormSchema = z.object({
  room: z.string().min(1, {
    message: "Número do quarto é obrigatório",
  }),
  floor: z.coerce.number().int().positive({
    message: "Andar deve ser um número inteiro positivo",
  }),
  status: z.enum(["Available", "Occupied", "Maintenance"], {
    required_error: "Status é obrigatório",
  }),
});

export type BedFormValues = z.infer<typeof bedFormSchema>;
export type BedType = BedFormValues & { id: string };

interface BedFormModalProps {
  isOpen: boolean;
  setIsOpen: (open: boolean) => void;
  selectedBed: BedType | null;
  onSubmit: (values: BedFormValues) => void;
}

const BedFormModal = ({ 
  isOpen, 
  setIsOpen, 
  selectedBed, 
  onSubmit 
}: BedFormModalProps) => {
  // Configuração do formulário
  const form = useForm<BedFormValues>({
    resolver: zodResolver(bedFormSchema),
    defaultValues: {
      room: selectedBed?.room || "",
      floor: selectedBed?.floor || 1,
      status: selectedBed?.status || "Available",
    },
  });

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            {selectedBed ? "Editar Leito" : "Novo Leito"}
          </DialogTitle>
        </DialogHeader>
        
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
            <FormField
              control={form.control}
              name="room"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Número do Quarto</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="floor"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Andar</FormLabel>
                  <FormControl>
                    <Input type="number" {...field} />
                  </FormControl>
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
                        <SelectValue placeholder="Selecione um status" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      <SelectItem value="Available">Disponível</SelectItem>
                      <SelectItem value="Occupied">Ocupado</SelectItem>
                      <SelectItem value="Maintenance">Manutenção</SelectItem>
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
                {selectedBed ? "Salvar" : "Adicionar"}
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

export default BedFormModal;
