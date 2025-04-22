
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Plus } from "lucide-react";
import BedFormModal from "@/components/beds/BedFormModal";
import BedListTable from "@/components/beds/BedListTable";
import { useBedManagement } from "@/hooks/useBedManagement";

const BedManagement = () => {
  const {
    beds,
    isOpen,
    setIsOpen,
    selectedBed,
    handleAddBed,
    handleEditBed,
    handleDeleteBed,
    onSubmit
  } = useBedManagement();

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Gestão de Leitos</h1>
        <Button onClick={handleAddBed}>
          <Plus className="h-4 w-4 mr-2" />
          Novo Leito
        </Button>
      </div>
      
      <Card>
        <CardHeader>
          <CardTitle>Lista de Leitos</CardTitle>
        </CardHeader>
        <CardContent>
          <BedListTable 
            beds={beds}
            onEdit={handleEditBed}
            onDelete={handleDeleteBed}
          />
        </CardContent>
      </Card>

      {/* Diálogo de cadastro/edição de leito */}
      <BedFormModal
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        selectedBed={selectedBed}
        onSubmit={onSubmit}
      />
    </div>
  );
};

export default BedManagement;
