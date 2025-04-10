
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Hospital } from "lucide-react";
import { useToast } from "@/components/ui/use-toast";

const loginSchema = z.object({
  email: z.string().email("Email inválido"),
  password: z.string().min(6, "A senha deve ter pelo menos 6 caracteres"),
});

type LoginFormValues = z.infer<typeof loginSchema>;

const Login = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [isLoading, setIsLoading] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const onSubmit = async (data: LoginFormValues) => {
    setIsLoading(true);
    
    // Simulate API call
    setTimeout(() => {
      setIsLoading(false);
      if (data.email === "admin@hospital.com" && data.password === "123456") {
        toast({
          title: "Login realizado com sucesso",
          description: "Bem-vindo(a) ao sistema EmergencyBed",
        });
        navigate("/dashboard");
      } else {
        toast({
          title: "Erro de autenticação",
          description: "Email ou senha incorretos",
          variant: "destructive",
        });
      }
    }, 1500);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-100 to-gray-200 p-4">
      <Card className="w-full max-w-md animate-fade-in">
        <CardHeader className="space-y-1 text-center">
          <div className="flex justify-center mb-2">
            <Hospital className="h-12 w-12 text-hospital-blue" />
          </div>
          <CardTitle className="text-2xl font-bold">EmergencyBed</CardTitle>
          <CardDescription>
            Sistema de Gestão Hospitalar
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                placeholder="seu.email@hospital.com"
                {...register("email")}
              />
              {errors.email && (
                <p className="text-sm text-red-500">{errors.email.message}</p>
              )}
            </div>
            <div className="space-y-2">
              <div className="flex items-center justify-between">
                <Label htmlFor="password">Senha</Label>
                <Button
                  type="button"
                  variant="link"
                  className="text-xs font-normal text-hospital-blue h-auto p-0"
                >
                  Esqueci minha senha
                </Button>
              </div>
              <Input
                id="password"
                type="password"
                placeholder="••••••"
                {...register("password")}
              />
              {errors.password && (
                <p className="text-sm text-red-500">{errors.password.message}</p>
              )}
            </div>
            <Button
              type="submit"
              className="w-full bg-hospital-blue hover:bg-hospital-blue/90"
              disabled={isLoading}
            >
              {isLoading ? "Entrando..." : "Entrar"}
            </Button>
            <div className="text-center text-sm text-gray-500 mt-4">
              <p>Para fins de demonstração, use:</p>
              <p>Email: admin@hospital.com</p>
              <p>Senha: 123456</p>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default Login;
