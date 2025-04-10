
import { useEffect, useState } from "react";
import DashboardCard from "@/components/DashboardCard";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Plus, User, Hospital, ArrowRight } from "lucide-react";
import { mockBeds, mockPatients, mockTransfers } from "@/data/mockData";
import TransferCard from "@/components/TransferCard";
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
  Legend 
} from "recharts";

const Dashboard = () => {
  const [criticalCount, setCriticalCount] = useState(0);
  const [occupiedBedsCount, setOccupiedBedsCount] = useState(0);
  const [availableBedsCount, setAvailableBedsCount] = useState(0);
  const [pendingTransfersCount, setPendingTransfersCount] = useState(0);

  useEffect(() => {
    // Count critical patients
    const criticalPatients = mockPatients.filter(p => p.status === "Critical");
    setCriticalCount(criticalPatients.length);

    // Count occupied beds
    const occupiedBeds = mockBeds.filter(b => b.status === "Occupied");
    setOccupiedBedsCount(occupiedBeds.length);

    // Count available beds
    const availableBeds = mockBeds.filter(b => b.status === "Available");
    setAvailableBedsCount(availableBeds.length);

    // Count pending transfers
    const pendingTransfers = mockTransfers.filter(t => t.status === "Pending");
    setPendingTransfersCount(pendingTransfers.length);
  }, []);

  // Data for bed occupancy chart
  const bedOccupancyData = [
    { name: "1º Andar", ocupados: 3, disponíveis: 3, manutenção: 1 },
    { name: "2º Andar", ocupados: 3, disponíveis: 5, manutenção: 1 },
  ];

  // Data for patient status pie chart
  const patientStatusData = [
    { name: "Crítico", value: criticalCount, color: "#FF3B30" },
    { name: "Estável", value: mockPatients.filter(p => p.status === "Stable").length, color: "#0077B6" },
    { name: "Saudável", value: mockPatients.filter(p => p.status === "Healthy").length, color: "#34C759" },
  ];

  // Pending transfers
  const pendingTransfers = mockTransfers.filter(t => t.status === "Pending");

  return (
    <div className="container mx-auto px-4 py-6 space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between">
        <h1 className="text-2xl font-bold">Dashboard</h1>
        <Button className="mt-4 md:mt-0 bg-hospital-blue hover:bg-hospital-blue/90">
          <Plus className="mr-2 h-4 w-4" />
          Nova Transferência
        </Button>
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

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <Card className="lg:col-span-2">
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
    </div>
  );
};

export default Dashboard;
