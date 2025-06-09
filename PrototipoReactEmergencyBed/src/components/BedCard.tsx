
import { Bed, getPatientByBedId } from "@/data/mockData";
import { Card, CardContent } from "@/components/ui/card";
import { cn } from "@/lib/utils";
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip";
import { Hospital } from "lucide-react";

interface BedCardProps {
  bed: Bed;
  onClick?: (bed: Bed) => void;
}

const BedCard = ({ bed, onClick }: BedCardProps) => {
  const patient = bed.patientId ? getPatientByBedId(bed.id) : undefined;
  
  const getBedStatusClass = () => {
    switch (bed.status) {
      case "Available":
        return "bed-available";
      case "Occupied":
        return "bed-occupied hover:animate-pulse-wave";
      case "Maintenance":
        return "bed-maintenance";
      default:
        return "";
    }
  };

  const handleClick = () => {
    if (onClick) {
      onClick(bed);
    }
  };

  return (
    <TooltipProvider>
      <Tooltip>
        <TooltipTrigger asChild>
          <Card 
            className={cn(
              "w-24 h-24 flex items-center justify-center cursor-pointer transition-transform hover:scale-105",
              getBedStatusClass()
            )}
            onClick={handleClick}
          >
            <CardContent className="p-2 text-center">
              <Hospital className="h-6 w-6 mx-auto mb-1" />
              <div className="text-sm font-bold">{bed.room}</div>
              {patient && (
                <div className="text-xs truncate max-w-full">
                  {patient.name.split(' ')[0]}
                </div>
              )}
            </CardContent>
          </Card>
        </TooltipTrigger>
        <TooltipContent>
          <div>
            <div className="font-bold">Quarto: {bed.room}</div>
            <div>Andar: {bed.floor}</div>
            <div>Status: {bed.status}</div>
            {patient && (
              <div>Paciente: {patient.name}</div>
            )}
          </div>
        </TooltipContent>
      </Tooltip>
    </TooltipProvider>
  );
};

export default BedCard;
