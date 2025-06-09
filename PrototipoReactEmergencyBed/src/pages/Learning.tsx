import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { Hospital, User, Bed, ArrowRight, Settings, ArrowDown } from "lucide-react";
import HospitalMap from "@/components/HospitalMap";
import DashboardCard from "@/components/DashboardCard";
import { useEffect, useState } from "react";

const Learning = () => {
  const navigate = useNavigate();
  const [waitingCount, setWaitingCount] = useState(0);

  useEffect(() => {
    const patients = JSON.parse(localStorage.getItem('waitingPatients') || '[]');
    setWaitingCount(patients.length);
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-blue-600 to-blue-800 text-white">
        <div className="container mx-auto px-4 py-16 md:py-24 flex flex-col items-center text-center">
          <Hospital className="h-20 w-20 mb-6" />
          <h1 className="text-4xl md:text-5xl font-bold mb-4">EmergencyBed</h1>
          <p className="text-xl md:text-2xl max-w-2xl mb-8">
            Sistema inteligente de gerenciamento de leitos hospitalares
          </p>
          <Button 
            size="lg" 
            className="bg-white text-blue-700 hover:bg-gray-100 font-bold"
            onClick={() => navigate("/login")}
          >
            Acessar o Sistema
          </Button>
          <ArrowDown className="mt-12 h-8 w-8 animate-bounce" />
        </div>
      </section>

      {/* Map Section */}
      <section className="py-16 md:py-20 container mx-auto px-4">
        <HospitalMap />
      </section>

      {/* Features Section */}
      <section className="py-16 md:py-20 container mx-auto px-4">
        <h2 className="text-3xl md:text-4xl font-bold text-center mb-12">Status do Hospital</h2>
        
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-12">
          <DashboardCard
            title="Pacientes Aguardando"
            value={waitingCount}
            description="Pacientes na fila de espera"
            icon={<User className="text-amber-500" />}
            className="border-l-4 border-amber-500"
          />
          <FeatureCard 
            icon={<Hospital />}
            title="Dashboard"
            description="Visualize em tempo real a ocupação de leitos e dados importantes para tomada de decisão."
          />
          <FeatureCard 
            icon={<User />}
            title="Pacientes"
            description="Gerencie informações de pacientes e acompanhe todo o histórico de internação."
          />
        </div>

        <h2 className="text-3xl md:text-4xl font-bold text-center mb-12">Funcionalidades Principais</h2>
        
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <FeatureCard 
            icon={<Hospital />}
            title="Dashboard"
            description="Visualize em tempo real a ocupação de leitos e dados importantes para tomada de decisão."
          />
          <FeatureCard 
            icon={<User />}
            title="Pacientes"
            description="Gerencie informações de pacientes e acompanhe todo o histórico de internação."
          />
          <FeatureCard 
            icon={<Bed />}
            title="Leitos"
            description="Cadastre, edite e monitore o status de todos os leitos disponíveis na unidade."
          />
          <FeatureCard 
            icon={<ArrowRight />}
            title="Transferências"
            description="Organize e acompanhe transferências entre setores e unidades de saúde."
          />
        </div>
      </section>

      {/* Benefits Section */}
      <section className="py-16 bg-gray-100">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl md:text-4xl font-bold text-center mb-12">Benefícios</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <BenefitCard 
              number="01"
              title="Otimização de Recursos"
              description="Reduza o tempo de espera por leitos e otimize a utilização dos recursos hospitalares."
            />
            <BenefitCard 
              number="02"
              title="Decisões Informadas"
              description="Acesse dados em tempo real para tomar decisões rápidas e precisas."
            />
            <BenefitCard 
              number="03"
              title="Experiência do Paciente"
              description="Melhore a experiência do paciente com maior eficiência nos processos de internação."
            />
          </div>
        </div>
      </section>

      {/* Settings Section */}
      <section className="py-16 md:py-20 container mx-auto px-4">
        <h2 className="text-3xl md:text-4xl font-bold text-center mb-12">Configurações Avançadas</h2>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div className="bg-white rounded-lg shadow-lg p-8 flex flex-col items-center text-center">
            <Settings className="h-12 w-12 text-blue-600 mb-4" />
            <h3 className="text-xl font-bold mb-3">Ajustes Institucionais</h3>
            <p className="text-gray-600">
              Configure dados do hospital, status de entidades e gerencie atendimentos de UTI de forma personalizada.
            </p>
          </div>
          <div className="bg-white rounded-lg shadow-lg p-8 flex flex-col items-center text-center">
            <Bed className="h-12 w-12 text-blue-600 mb-4" />
            <h3 className="text-xl font-bold mb-3">Gestão de Leitos</h3>
            <p className="text-gray-600">
              Cadastre, edite e organize seus leitos hospitalares com um sistema intuitivo e eficiente.
            </p>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-16 bg-gradient-to-r from-blue-600 to-blue-800 text-white">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-3xl md:text-4xl font-bold mb-6">Comece Agora</h2>
          <p className="text-xl max-w-2xl mx-auto mb-8">
            Experimente o EmergencyBed e transforme a gestão de leitos do seu hospital
          </p>
          <Button 
            size="lg" 
            className="bg-white text-blue-700 hover:bg-gray-100 font-bold"
            onClick={() => navigate("/login")}
          >
            Acessar o Sistema
          </Button>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-8">
        <div className="container mx-auto px-4 text-center">
          <Hospital className="h-8 w-8 mx-auto mb-4" />
          <p className="mb-2">EmergencyBed © 2025</p>
          <p className="text-gray-400 text-sm">Sistema de Gestão Hospitalar de Emergência</p>
        </div>
      </footer>
    </div>
  );
};

interface FeatureCardProps {
  icon: React.ReactNode;
  title: string;
  description: string;
}

const FeatureCard = ({ icon, title, description }: FeatureCardProps) => (
  <div className="bg-white rounded-lg shadow-lg p-6 flex flex-col items-center text-center">
    <div className="h-12 w-12 text-blue-600 mb-4 flex items-center justify-center">
      {icon}
    </div>
    <h3 className="text-xl font-bold mb-3">{title}</h3>
    <p className="text-gray-600">{description}</p>
  </div>
);

interface BenefitCardProps {
  number: string;
  title: string;
  description: string;
}

const BenefitCard = ({ number, title, description }: BenefitCardProps) => (
  <div className="bg-white rounded-lg shadow-lg p-8">
    <div className="text-3xl font-bold text-blue-600 mb-4">{number}</div>
    <h3 className="text-xl font-bold mb-3">{title}</h3>
    <p className="text-gray-600">{description}</p>
  </div>
);

export default Learning;