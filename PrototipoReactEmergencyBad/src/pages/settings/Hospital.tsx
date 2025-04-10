
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

// Definir o schema do formulário com base no modelo UML
const hospitalFormSchema = z.object({
  nome: z.string().min(2, {
    message: "Nome deve ter pelo menos 2 caracteres",
  }),
  telefone: z.string().min(8, {
    message: "Telefone deve ter pelo menos 8 caracteres",
  }),
  escalonamento: z.string().optional(),
  capacidadeTotal: z.coerce.number().positive({
    message: "Capacidade deve ser um número positivo",
  }),
  capacidadeAtiva: z.coerce.number().positive({
    message: "Capacidade ativa deve ser um número positivo",
  }),
});

type HospitalFormValues = z.infer<typeof hospitalFormSchema>;

const Hospital = () => {
  const [isEditing, setIsEditing] = useState(false);
  
  // Valores default do hospital (simulando dados salvos)
  const defaultValues: HospitalFormValues = {
    nome: "Hospital Emergência Central",
    telefone: "(11) 3456-7890",
    escalonamento: "Nível 1",
    capacidadeTotal: 300,
    capacidadeAtiva: 250,
  };

  const form = useForm<HospitalFormValues>({
    resolver: zodResolver(hospitalFormSchema),
    defaultValues,
  });

  function onSubmit(values: HospitalFormValues) {
    // Aqui você implementaria a lógica para salvar os dados
    console.log(values);
    toast({
      title: "Dados do hospital atualizados",
      description: "As informações do hospital foram atualizadas com sucesso.",
    });
    setIsEditing(false);
  }

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-6">Configurações do Hospital</h1>
      
      <Card className="w-full max-w-2xl mx-auto">
        <CardHeader>
          <CardTitle>Dados do Hospital</CardTitle>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
              <FormField
                control={form.control}
                name="nome"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nome</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={!isEditing} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="telefone"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Telefone</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={!isEditing} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <FormField
                control={form.control}
                name="escalonamento"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Escalonamento</FormLabel>
                    <FormControl>
                      <Input {...field} disabled={!isEditing} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="capacidadeTotal"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Capacidade Total</FormLabel>
                      <FormControl>
                        <Input 
                          type="number" 
                          {...field} 
                          disabled={!isEditing}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                
                <FormField
                  control={form.control}
                  name="capacidadeAtiva"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Capacidade Ativa</FormLabel>
                      <FormControl>
                        <Input 
                          type="number" 
                          {...field} 
                          disabled={!isEditing}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              
              <div className="flex justify-end gap-4">
                {isEditing ? (
                  <>
                    <Button type="button" variant="outline" onClick={() => setIsEditing(false)}>
                      Cancelar
                    </Button>
                    <Button type="submit">Salvar</Button>
                  </>
                ) : (
                  <Button type="button" onClick={() => setIsEditing(true)}>
                    Editar
                  </Button>
                )}
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
};

export default Hospital;
