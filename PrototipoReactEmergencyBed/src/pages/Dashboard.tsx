
import { useEffect, useState } from "react";
import DashboardCard from "@/components/DashboardCard";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Plus, User, Hospital, ArrowRight, FileText } from "lucide-react";
import { mockBeds, mockPatients, mockTransfers } from "@/data/mockData";
import TransferCard from "@/components/TransferCard";
import TransferForm from "@/components/TransferForm";
import AttendanceForm from "@/components/AttendanceForm";
import StatusBadge from "@/components/StatusBadge";
import { 
  BarChart, 
  Bar, 
  XAxis, 
  YAxis, 
  CartesianGrid, 
  Tooltip, 
  ResponsiveContainer, 
  PieChart, 
  Pie, 
  Cell, 
  Legend,
  LineChart,
  Line,
  AreaChart,
  Area
} from "recharts";
import WaitingQRCode from "@/components/WaitingQRCode";

const Dashboard = () => {
  const [criticalCount, setCriticalCount] = useState(0);
  const [occupiedBedsCount, setOccupiedBedsCount] = useState(0);
  const [availableBedsCount, setAvailableBedsCount] = useState(0);
  const [pendingTransfersCount, setPendingTransfersCount] = useState(0);
  const [isTransferFormOpen, setIsTransferFormOpen] = useState(false);
  const [isAttendanceFormOpen, setIsAttendanceFormOpen] = useState(false);
  const [waitingPatients, setWaitingPatients] = useState([]);

  useEffect(() => {
    const criticalPatients = mockPatients.filter(p => p.status === "Critical");
    setCriticalCount(criticalPatients.length);

    const occupiedBeds = mockBeds.filter(b => b.status === "Occupied");
    setOccupiedBedsCount(occupiedBeds.length);

    const availableBeds = mockBeds.filter(b => b.status === "Available");
    setAvailableBedsCount(availableBeds.length);

    const pendingTransfers = mockTransfers.filter(t => t.status === "Pending");
    setPendingTransfersCount(pendingTransfers.length);

    const loadWaitingPatients = () => {
      const patients = JSON.parse(localStorage.getItem('waitingPatients') || '[]');
      setWaitingPatients(patients);
    };

    loadWaitingPatients();
    const interval = setInterval(loadWaitingPatients, 5000);
    return () => clearInterval(interval);
  }, []);

  const bedOccupancyData = [
    { name: "1º Andar", ocupados: 3, disponíveis: 3, manutenção: 1 },
    { name: "2º Andar", ocupados: 3, disponíveis: 5, manutenção: 1 },
    { name: "3º Andar", ocupados: 5, disponíveis: 2, manutenção: 0 },
    { name: "4º Andar", ocupados: 2, disponíveis: 6, manutenção: 2 },
  ];

  const patientStatusData = [
    { name: "Crítico", value: criticalCount, color: "#FF3B30" },
    { name: "Estável", value: mockPatients.filter(p => p.status === "Stable").length, color: "#0077B6" },
    { name: "Saudável", value: mockPatients.filter(p => p.status === "Healthy").length, color: "#34C759" },
  ];

  const monthlyPatientData = [
    { month: 'Jan', pacientes: 65, altas: 40, transferencias: 15 },
    { month: 'Fev', pacientes: 59, altas: 35, transferencias: 12 },
    { month: 'Mar', pacientes: 80, altas: 52, transferencias: 20 },
    { month: 'Abr', pacientes: 81, altas: 55, transferencias: 21 },
    { month: 'Mai', pacientes: 56, altas: 36, transferencias: 14 },
    { month: 'Jun', pacientes: 55, altas: 45, transferencias: 10 },
  ];

  const capacityData = [
    { name: 'Hospital A', utilização: 78 },
    { name: 'Hospital B', utilização: 65 },
    { name: 'Hospital C', utilização: 83 },
    { name: 'Hospital D', utilização: 45 },
  ];

  const pendingTransfers = mockTransfers.filter(t => t.status === "Pending");

  const openTransferForm = () => {
    setIsTransferFormOpen(true);
  };

  const closeTransferForm = () => {
    setIsTransferFormOpen(false);
  };

  const openAttendanceForm = () => {
    setIsAttendanceFormOpen(true);
  };

  const closeAttendanceForm = () => {
    setIsAttendanceFormOpen(false);
  };

  return (
    <div className="container mx-auto px-4 py-6 space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between">
        <h1 className="text-2xl font-bold">Dashboard</h1>
        <div className="flex flex-col sm:flex-row gap-3 mt-4 md:mt-0">
          <Button 
            className="bg-hospital-blue hover:bg-hospital-blue/90"
            onClick={openTransferForm}
          >
            <Plus className="mr-2 h-4 w-4" />
            Nova Transferência
          </Button>
          <Button 
            className="bg-hospital-green hover:bg-hospital-green/90"
            onClick={openAttendanceForm}
          >
            <FileText className="mr-2 h-4 w-4" />
            Novo Atendimento
          </Button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <DashboardCard
          title="Pacientes em Estado Crítico"
          value={criticalCount}
          description="Total de pacientes em estado crítico"
          icon={<User className="text-hospital-red h-4 w-4" />}
          className="border-l-4 border-hospital-red"
        />
        <DashboardCard
          title="Leitos Ocupados"
          value={`${occupiedBedsCount}/${mockBeds.length}`}
          description={`${((occupiedBedsCount / mockBeds.length) * 100).toFixed(1)}% de ocupação total`}
          icon={<Hospital className="text-hospital-red h-4 w-4" />}
          className="border-l-4 border-hospital-red"
        />
        <DashboardCard
          title="Leitos Disponíveis"
          value={availableBedsCount}
          description={`${((availableBedsCount / mockBeds.length) * 100).toFixed(1)}% do total de leitos`}
          icon={<Hospital className="text-hospital-green h-4 w-4" />}
          className="border-l-4 border-hospital-green"
        />
        <DashboardCard
          title="Transferências Pendentes"
          value={pendingTransfersCount}
          description="Transferências aguardando aprovação"
          icon={<ArrowRight className="text-amber-500 h-4 w-4" />}
          className="border-l-4 border-amber-500"
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card className="">
          <CardHeader>
            <CardTitle className="text-lg">Ocupação por Andar</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-80">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart
                  data={bedOccupancyData}
                  margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar dataKey="ocupados" stackId="a" fill="#FF3B30" />
                  <Bar dataKey="disponíveis" stackId="a" fill="#34C759" />
                  <Bar dataKey="manutenção" stackId="a" fill="#8E9196" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-lg">Status dos Pacientes</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-80">
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={patientStatusData}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                    label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                  >
                    {patientStatusData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="text-lg">Tendência de Pacientes (Mensal)</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-80">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart
                  data={monthlyPatientData}
                  margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="pacientes" stroke="#0077B6" activeDot={{ r: 8 }} />
                  <Line type="monotone" dataKey="altas" stroke="#34C759" />
                  <Line type="monotone" dataKey="transferencias" stroke="#FF9500" />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-lg">Utilização de Capacidade por Hospital</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-80">
              <ResponsiveContainer width="100%" height="100%">
                <AreaChart
                  data={capacityData}
                  margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Tooltip />
                  <Area type="monotone" dataKey="utilização" fill="#8884d8" stroke="#8884d8" />
                </AreaChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <WaitingQRCode />
        <Card className="lg:col-span-2">
          <CardHeader>
            <CardTitle>Pacientes Aguardando Atendimento</CardTitle>
          </CardHeader>
          <CardContent>
            {waitingPatients.length > 0 ? (
              <div className="space-y-4">
                {waitingPatients.map((patient) => (
                  <Card key={patient.id}>
                    <CardContent className="flex justify-between items-center p-4">
                      <div>
                        <p className="font-medium">{patient.name}</p>
                        <p className="text-sm text-muted-foreground">
                          Aguardando desde: {new Date(patient.registrationTime).toLocaleTimeString()}
                        </p>
                      </div>
                      <StatusBadge status="Pending" />
                    </CardContent>
                  </Card>
                ))}
              </div>
            ) : (
              <p className="text-muted-foreground text-center py-4">
                Nenhum paciente aguardando atendimento
              </p>
            )}
          </CardContent>
        </Card>
      </div>

      <div>
        <h2 className="text-xl font-semibold mb-4">Transferências Pendentes</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {pendingTransfers.map((transfer) => (
            <TransferCard
              key={transfer.id}
              transfer={transfer}
              onView={() => {}}
            />
          ))}
          {pendingTransfers.length === 0 && (
            <Card className="col-span-full p-6 text-center">
              <p>Não há transferências pendentes no momento.</p>
            </Card>
          )}
        </div>
      </div>

      <TransferForm 
        isOpen={isTransferFormOpen} 
        onClose={closeTransferForm} 
      />
      <AttendanceForm
        isOpen={isAttendanceFormOpen}
        onClose={closeAttendanceForm}
      />
    </div>
  );
};

export default Dashboard;
