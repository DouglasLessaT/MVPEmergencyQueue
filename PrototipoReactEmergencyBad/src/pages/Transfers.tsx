
import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Button } from "@/components/ui/button";
import { Transfer, mockTransfers, getPatientById, getHospitalById } from "@/data/mockData";
import TransferCard from "@/components/TransferCard";
import TransferForm from "@/components/TransferForm";
import { Plus, Check, X } from "lucide-react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { useToast } from "@/hooks/use-toast";
import StatusBadge from "@/components/StatusBadge";

const Transfers = () => {
  const { toast } = useToast();
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedTransfer, setSelectedTransfer] = useState<Transfer | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [isTransferFormOpen, setIsTransferFormOpen] = useState(false);

  const pendingTransfers = mockTransfers.filter((t) => t.status === "Pending");
  const inProgressTransfers = mockTransfers.filter((t) => t.status === "In Progress");
  const completedTransfers = mockTransfers.filter((t) => t.status === "Completed");
  const cancelledTransfers = mockTransfers.filter((t) => t.status === "Cancelled");

  const handleViewTransfer = (transfer: Transfer) => {
    setSelectedTransfer(transfer);
    setIsDialogOpen(true);
  };

  const handleApproveTransfer = () => {
    toast({
      title: "Transferência aprovada",
      description: "A transferência foi aprovada e está em andamento.",
    });
    setIsDialogOpen(false);
  };

  const handleRejectTransfer = () => {
    toast({
      title: "Transferência cancelada",
      description: "A transferência foi cancelada com sucesso.",
      variant: "destructive",
    });
    setIsDialogOpen(false);
  };

  const openTransferForm = () => {
    setIsTransferFormOpen(true);
  };

  const closeTransferForm = () => {
    setIsTransferFormOpen(false);
  };

  return (
    <div className="container mx-auto px-4 py-6 space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between">
        <h1 className="text-2xl font-bold">Transferências</h1>
        <Button 
          className="mt-4 md:mt-0 bg-hospital-blue hover:bg-hospital-blue/90"
          onClick={openTransferForm}
        >
          <Plus className="mr-2 h-4 w-4" />
          Nova Transferência
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <Card className="bg-amber-50 border-amber-200">
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-amber-600">Pendentes</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-amber-700">{pendingTransfers.length}</p>
          </CardContent>
        </Card>
        <Card className="bg-blue-50 border-blue-200">
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-blue-600">Em Andamento</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-blue-700">{inProgressTransfers.length}</p>
          </CardContent>
        </Card>
        <Card className="bg-green-50 border-green-200">
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-green-600">Concluídas</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-green-700">{completedTransfers.length}</p>
          </CardContent>
        </Card>
        <Card className="bg-gray-50 border-gray-200">
          <CardHeader className="pb-2">
            <CardTitle className="text-lg text-gray-600">Canceladas</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold text-gray-700">{cancelledTransfers.length}</p>
          </CardContent>
        </Card>
      </div>

      <Card>
        <CardContent className="p-6">
          <Tabs defaultValue="pending" value={activeTab} onValueChange={setActiveTab}>
            <TabsList>
              <TabsTrigger value="pending">Pendentes</TabsTrigger>
              <TabsTrigger value="in-progress">Em Andamento</TabsTrigger>
              <TabsTrigger value="completed">Concluídas</TabsTrigger>
              <TabsTrigger value="cancelled">Canceladas</TabsTrigger>
            </TabsList>
            <TabsContent value="pending" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {pendingTransfers.length > 0 ? (
                  pendingTransfers.map((transfer) => (
                    <TransferCard
                      key={transfer.id}
                      transfer={transfer}
                      onView={handleViewTransfer}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Não há transferências pendentes</p>
                  </div>
                )}
              </div>
            </TabsContent>
            <TabsContent value="in-progress" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {inProgressTransfers.length > 0 ? (
                  inProgressTransfers.map((transfer) => (
                    <TransferCard
                      key={transfer.id}
                      transfer={transfer}
                      onView={handleViewTransfer}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Não há transferências em andamento</p>
                  </div>
                )}
              </div>
            </TabsContent>
            <TabsContent value="completed" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {completedTransfers.length > 0 ? (
                  completedTransfers.map((transfer) => (
                    <TransferCard
                      key={transfer.id}
                      transfer={transfer}
                      onView={handleViewTransfer}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Não há transferências concluídas</p>
                  </div>
                )}
              </div>
            </TabsContent>
            <TabsContent value="cancelled" className="mt-6">
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {cancelledTransfers.length > 0 ? (
                  cancelledTransfers.map((transfer) => (
                    <TransferCard
                      key={transfer.id}
                      transfer={transfer}
                      onView={handleViewTransfer}
                    />
                  ))
                ) : (
                  <div className="col-span-full p-8 text-center bg-gray-50 rounded-md">
                    <p className="text-gray-500">Não há transferências canceladas</p>
                  </div>
                )}
              </div>
            </TabsContent>
          </Tabs>
        </CardContent>
      </Card>

      {/* Transfer Details Dialog */}
      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent className="sm:max-w-lg">
          <DialogHeader>
            <DialogTitle>Detalhes da Transferência</DialogTitle>
          </DialogHeader>
          {selectedTransfer && (
            <div className="space-y-4">
              <div className="flex justify-between items-center">
                <span className="font-medium">Status:</span>
                <StatusBadge status={selectedTransfer.status} />
              </div>
              
              <div className="space-y-2">
                <h3 className="font-semibold">Paciente</h3>
                <div className="bg-gray-50 p-3 rounded-md">
                  <p className="font-medium">{getPatientById(selectedTransfer.patientId)?.name}</p>
                  <p className="text-sm text-gray-600">{getPatientById(selectedTransfer.patientId)?.diagnosis}</p>
                </div>
              </div>
              
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <h3 className="font-semibold">Hospital de Origem</h3>
                  <div className="bg-gray-50 p-3 rounded-md">
                    <p className="font-medium">{getHospitalById(selectedTransfer.fromHospitalId)?.name}</p>
                    <p className="text-sm text-gray-600">{getHospitalById(selectedTransfer.fromHospitalId)?.address}</p>
                  </div>
                </div>
                <div className="space-y-2">
                  <h3 className="font-semibold">Hospital de Destino</h3>
                  <div className="bg-gray-50 p-3 rounded-md">
                    <p className="font-medium">{getHospitalById(selectedTransfer.toHospitalId)?.name}</p>
                    <p className="text-sm text-gray-600">{getHospitalById(selectedTransfer.toHospitalId)?.address}</p>
                  </div>
                </div>
              </div>
              
              <div className="space-y-2">
                <h3 className="font-semibold">Datas</h3>
                <div className="bg-gray-50 p-3 rounded-md grid grid-cols-2 gap-2">
                  <div>
                    <p className="text-sm text-gray-500">Solicitação</p>
                    <p>{new Date(selectedTransfer.requestDate).toLocaleDateString('pt-BR')}</p>
                  </div>
                  {selectedTransfer.completionDate && (
                    <div>
                      <p className="text-sm text-gray-500">Conclusão</p>
                      <p>{new Date(selectedTransfer.completionDate).toLocaleDateString('pt-BR')}</p>
                    </div>
                  )}
                </div>
              </div>
              
              {selectedTransfer.status === "Pending" && (
                <div className="flex justify-end space-x-2 pt-4">
                  <Button
                    variant="outline"
                    className="border-hospital-red text-hospital-red hover:bg-hospital-red/10"
                    onClick={handleRejectTransfer}
                  >
                    <X className="mr-2 h-4 w-4" />
                    Recusar
                  </Button>
                  <Button
                    className="bg-hospital-green hover:bg-hospital-green/90"
                    onClick={handleApproveTransfer}
                  >
                    <Check className="mr-2 h-4 w-4" />
                    Aprovar
                  </Button>
                </div>
              )}
            </div>
          )}
        </DialogContent>
      </Dialog>

      {/* Transfer Form */}
      <TransferForm 
        isOpen={isTransferFormOpen} 
        onClose={closeTransferForm} 
      />
    </div>
  );
};

export default Transfers;
