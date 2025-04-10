
import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Pencil, Trash } from "lucide-react";
import StatusBadge from "@/components/StatusBadge";
import { BedType } from "./BedFormModal";

interface BedListTableProps {
  beds: BedType[];
  onEdit: (bed: BedType) => void;
  onDelete: (id: string) => void;
}

const BedListTable = ({ beds, onEdit, onDelete }: BedListTableProps) => {
  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>Quarto</TableHead>
          <TableHead>Andar</TableHead>
          <TableHead>Status</TableHead>
          <TableHead className="text-right">Ações</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {beds.map(bed => (
          <TableRow key={bed.id}>
            <TableCell>{bed.id}</TableCell>
            <TableCell>{bed.room}</TableCell>
            <TableCell>{bed.floor}</TableCell>
            <TableCell>
              <StatusBadge status={bed.status} />
            </TableCell>
            <TableCell className="text-right">
              <Button variant="ghost" size="icon" onClick={() => onEdit(bed)}>
                <Pencil className="h-4 w-4" />
              </Button>
              <Button variant="ghost" size="icon" onClick={() => onDelete(bed.id)}>
                <Trash className="h-4 w-4" />
              </Button>
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
};

export default BedListTable;
