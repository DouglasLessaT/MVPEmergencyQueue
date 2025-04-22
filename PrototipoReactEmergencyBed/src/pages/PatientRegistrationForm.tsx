
import { useState } from "react";
import { Form, FormField, FormItem, FormLabel, FormControl } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Card, CardHeader, CardContent, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useForm } from "react-hook-form";
import { toast } from "@/components/ui/use-toast";

interface PatientFormData {
  name: string;
  cpf: string;
  gender: string;
}

const PatientRegistrationForm = () => {
  const form = useForm<PatientFormData>();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const onSubmit = async (data: PatientFormData) => {
    setIsSubmitting(true);
    try {
      // Store in localStorage for now (this should be replaced with a proper backend)
      const patients = JSON.parse(localStorage.getItem('waitingPatients') || '[]');
      const newPatient = {
        ...data,
        id: Date.now(),
        status: "Waiting",
        registrationTime: new Date().toISOString(),
      };
      patients.push(newPatient);
      localStorage.setItem('waitingPatients', JSON.stringify(patients));
      
      toast({
        title: "Registro realizado com sucesso!",
        description: "Você está na lista de espera para atendimento.",
      });
      form.reset();
    } catch (error) {
      toast({
        title: "Erro no registro",
        description: "Ocorreu um erro ao registrar seus dados.",
        variant: "destructive",
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Card className="w-full max-w-md mx-auto mt-8">
      <CardHeader>
        <CardTitle>Registro de Paciente</CardTitle>
      </CardHeader>
      <CardContent>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <FormField
              control={form.control}
              name="name"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nome Completo</FormLabel>
                  <FormControl>
                    <Input required placeholder="Digite seu nome completo" {...field} />
                  </FormControl>
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="cpf"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>CPF</FormLabel>
                  <FormControl>
                    <Input required placeholder="Digite seu CPF" {...field} />
                  </FormControl>
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="gender"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Sexo</FormLabel>
                  <Select onValueChange={field.onChange} defaultValue={field.value}>
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Selecione seu sexo" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      <SelectItem value="male">Masculino</SelectItem>
                      <SelectItem value="female">Feminino</SelectItem>
                      <SelectItem value="other">Outro</SelectItem>
                    </SelectContent>
                  </Select>
                </FormItem>
              )}
            />

            <Button type="submit" className="w-full" disabled={isSubmitting}>
              {isSubmitting ? "Registrando..." : "Registrar"}
            </Button>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
};

export default PatientRegistrationForm;
