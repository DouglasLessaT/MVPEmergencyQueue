
import { Transfer, getPatientById, getHospitalById } from "@/data/mockData";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import StatusBadge from "./StatusBadge";
import { Button } from "@/components/ui/button";
import { ArrowRight } from "lucide-react";

interface TransferCardProps {
  transfer: Transfer;
  onView?: (transfer: Transfer) => void;
}

const TransferCard = ({ transfer, onView }: TransferCardProps) => {
  const patient = getPatientById(transfer.patientId);
  const fromHospital = getHospitalById(transfer.fromHospitalId);
  const toHospital = getHospitalById(transfer.toHospitalId);

  if (!patient || !fromHospital || !toHospital) return null;

  return (
    <Card className="overflow-hidden transition-shadow hover:shadow-md animate-fade-in">
      <CardContent className="p-4">
        <div className="flex justify-between items-start">
          <div>
            <h3 className="text-lg font-semibold">{patient.name}</h3>
            <p className="text-sm text-gray-600">{patient.diagnosis}</p>
          </div>
          <StatusBadge status={transfer.status} />
        </div>
        
        <div className="flex items-center justify-between mt-4">
          <div className="text-sm">
            <p className="font-medium">{fromHospital.name}</p>
            <p className="text-gray-600 text-xs">{fromHospital.address}</p>
          </div>
          <ArrowRight className="mx-2 text-gray-400" />
          <div className="text-sm text-right">
            <p className="font-medium">{toHospital.name}</p>
            <p className="text-gray-600 text-xs">{toHospital.address}</p>
          </div>
        </div>
        
        <div className="mt-2 text-xs text-gray-600">
          <p>Solicitado em: {new Date(transfer.requestDate).toLocaleDateString('pt-BR')}</p>
          {transfer.completionDate && (
            <p>Concluído em: {new Date(transfer.completionDate).toLocaleDateString('pt-BR')}</p>
          )}
        </div>
      </CardContent>
      
      {onView && (
        <CardFooter className="p-2 border-t bg-gray-50">
          <Button 
            variant="ghost" 
            size="sm" 
            className="ml-auto text-hospital-blue"
            onClick={() => onView(transfer)}
          >
            Ver detalhes
          </Button>
        </CardFooter>
      )}
    </Card>
  );
};

export default TransferCard;
