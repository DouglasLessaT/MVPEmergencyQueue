
import { useState } from "react";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useToast } from "@/hooks/use-toast";
import { mockPatients, mockHospitals } from "@/data/mockData";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";

const formSchema = z.object({
  patientId: z.string({
    required_error: "Por favor, selecione um paciente",
  }),
  fromHospitalId: z.string({
    required_error: "Por favor, selecione o hospital de origem",
  }),
  toHospitalId: z.string({
    required_error: "Por favor, selecione o hospital de destino",
  }),
  isUTI: z.boolean().default(false),
}).refine(data => data.fromHospitalId !== data.toHospitalId, {
  message: "Hospital de origem e destino não podem ser iguais",
  path: ["toHospitalId"],
});

type FormValues = z.infer<typeof formSchema>;

interface TransferFormProps {
  isOpen: boolean;
  onClose: () => void;
}

const TransferForm = ({ isOpen, onClose }: TransferFormProps) => {
  const { toast } = useToast();
  const [isSubmitting, setIsSubmitting] = useState(false);
  
  const form = useForm<FormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      patientId: "",
      fromHospitalId: "",
      toHospitalId: "",
      isUTI: false,
    },
  });

  const onSubmit = async (values: FormValues) => {
    setIsSubmitting(true);
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    toast({
      title: "Transferência solicitada",
      description: `A transferência foi registrada com sucesso ${values.isUTI ? "para UTI" : ""} e está aguardando aprovação.`,
    });
    
    setIsSubmitting(false);
    form.reset();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Nova Transferência</DialogTitle>
        </DialogHeader>
        
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
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
              name="fromHospitalId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Hospital de Origem</FormLabel>
                  <Select 
                    onValueChange={field.onChange} 
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione o hospital de origem" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {mockHospitals.map(hospital => (
                        <SelectItem key={hospital.id} value={hospital.id}>
                          {hospital.name}
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
              name="toHospitalId"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Hospital de Destino</FormLabel>
                  <Select 
                    onValueChange={field.onChange} 
                    defaultValue={field.value}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione o hospital de destino" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {mockHospitals.map(hospital => (
                        <SelectItem key={hospital.id} value={hospital.id}>
                          {hospital.name}
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
                    <FormLabel>Transferência para UTI</FormLabel>
                  </div>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <DialogFooter>
              <Button variant="outline" type="button" onClick={onClose}>Cancelar</Button>
              <Button type="submit" disabled={isSubmitting}>
                {isSubmitting ? "Enviando..." : "Enviar"}
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

export default TransferForm;
