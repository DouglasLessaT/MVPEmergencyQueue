import { useState } from "react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useToast } from "@/hooks/use-toast";
import { mockPatients, mockBeds } from "@/data/mockData";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";

const formSchema = z.object({
  patientId: z.string({
    required_error: "Por favor, selecione um paciente",
  }),
  bedId: z.string({
    required_error: "Por favor, selecione um leito",
  }),
  type: z.string({
    required_error: "Por favor, selecione o tipo de atendimento",
  }),
  status: z.string({
    required_error: "Por favor, selecione o status do paciente",
  }),
  isUTI: z.boolean().default(false),
  stayDays: z.coerce.number().min(1, {
    message: "A estadia deve ser de pelo menos 1 dia",
  }),
  admissionDate: z.string({
    required_error: "Por favor, informe a data de admissão",
  }),
  admissionTime: z.string({
    required_error: "Por favor, informe a hora de admissão",
  }),
});

type FormValues = z.infer<typeof formSchema>;

interface AttendanceFormProps {
  isOpen: boolean;
  onClose: () => void;
}

const AttendanceForm = ({ isOpen, onClose }: AttendanceFormProps) => {
  const { toast } = useToast();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [occupiedBeds, setOccupiedBeds] = useState<string[]>([]);
  
  const form = useForm<FormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      patientId: "",
      bedId: "",
      type: "",
      status: "",
      isUTI: false,
      stayDays: 1,
      admissionDate: new Date().toISOString().split('T')[0],
      admissionTime: new Date().toTimeString().slice(0, 5),
    },
  });

  // Filter unavailable beds
  const availableBeds = mockBeds
    .filter(bed => bed.status === "Available" || !occupiedBeds.includes(bed.id));

  const onSubmit = async (values: FormValues) => {
    setIsSubmitting(true);
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // Format complete date and time
    const completeDate = `${values.admissionDate}T${values.admissionTime}:00`;
    
    console.log("Registering new attendance:", {
      ...values, 
      completeDate,
      patient: mockPatients.find(p => p.id === values.patientId)?.name,
    });
    
    toast({
      title: "Atendimento registrado",
      description: `O atendimento foi registrado com sucesso ${values.isUTI ? "para UTI" : ""}.`,
    });
    
    setIsSubmitting(false);
    form.reset();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Novo Atendimento</DialogTitle>
        </DialogHeader>
        
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <FormField
              control={form.control}
              name="patientId"
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
                      {mockPatients.map(patient => (
                        <SelectItem key={patient.id} value={patient.id}>
                          {patient.name} - {patient.diagnosis}
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
              name="bedId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Leito</FormLabel>
                  <Select 
                    onValueChange={field.onChange} 
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione um leito" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {availableBeds.map(bed => (
                        <SelectItem key={bed.id} value={bed.id}>
                          {bed.id} - {bed.room ? `Quarto ${bed.room}` : "Sem quarto"}
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
                name="admissionDate"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Data de Admissão</FormLabel>
                    <FormControl>
                      <Input type="date" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="admissionTime"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Hora de Admissão</FormLabel>
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
              name="type"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Tipo de Atendimento</FormLabel>
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
                  <FormLabel>Status do Paciente</FormLabel>
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
                      <SelectItem value="Critical">Crítico</SelectItem>
                      <SelectItem value="Stable">Estável</SelectItem>
                      <SelectItem value="Observation">Em Observação</SelectItem>
                    </SelectContent>
                  </Select>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="stayDays"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Dias de Estadia Previstos</FormLabel>
                  <FormControl>
                    <Input type="number" min="1" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="isUTI"
              render={({ field }) => (
                <FormItem className="flex flex-row items-start space-x-3 space-y-0 rounded-md border p-4">
                  <FormControl>
                    <Checkbox
                      checked={field.value}
                      onCheckedChange={field.onChange}
                    />
                  </FormControl>
                  <div className="space-y-1 leading-none">
                    <FormLabel>Atendimento em UTI</FormLabel>
                  </div>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <DialogFooter>
              <Button variant="outline" type="button" onClick={onClose}>Cancelar</Button>
              <Button type="submit" disabled={isSubmitting}>
                {isSubmitting ? "Enviando..." : "Registrar Atendimento"}
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

export default AttendanceForm;