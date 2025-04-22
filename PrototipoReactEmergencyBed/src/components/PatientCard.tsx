
import { Patient } from "@/data/mockData";
import { Card, CardContent } from "@/components/ui/card";
import StatusBadge from "./StatusBadge";
import { Edit, Trash, User } from "lucide-react";
import { Button } from "@/components/ui/button";

interface PatientCardProps {
  patient: Patient;
  onEdit?: (patient: Patient) => void;
  onDelete?: (patient: Patient) => void;
}

const PatientCard = ({ patient, onEdit, onDelete }: PatientCardProps) => {
  return (
    <Card className="overflow-hidden transition-shadow hover:shadow-md animate-fade-in">
      <CardContent className="p-0">
        <div className="flex items-start p-4">
          <div className="flex-shrink-0 mr-4 h-16 w-16 rounded-full bg-gray-200 flex items-center justify-center">
            <User className="h-8 w-8 text-gray-500" />
          </div>
          <div className="flex-1">
            <div className="flex items-center justify-between">
              <h3 className="text-lg font-semibold">{patient.name}</h3>
              <StatusBadge status={patient.status} />
            </div>
            <div className="mt-1 text-sm text-gray-600">
              {patient.age} anos • {patient.gender}
            </div>
            <div className="mt-1 text-sm text-gray-600">
              {patient.diagnosis}
            </div>
            {patient.room && (
              <div className="mt-1 text-sm font-medium text-hospital-blue">
                Quarto: {patient.room}
              </div>
            )}
          </div>
        </div>
        <div className="border-t px-4 py-2 bg-gray-50 flex justify-end space-x-2">
          {onEdit && (
            <Button
              variant="ghost"
              size="sm"
              className="text-gray-500 hover:text-hospital-blue"
              onClick={() => onEdit(patient)}
            >
              <Edit className="h-4 w-4 mr-1" />
              Editar
            </Button>
          )}
          {onDelete && (
            <Button
              variant="ghost"
              size="sm"
              className="text-gray-500 hover:text-hospital-red"
              onClick={() => onDelete(patient)}
            >
              <Trash className="h-4 w-4 mr-1" />
              Excluir
            </Button>
          )}
        </div>
      </CardContent>
    </Card>
  );
};

export default PatientCard;
