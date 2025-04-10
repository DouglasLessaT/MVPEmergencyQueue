
import { cn } from "@/lib/utils";

interface StatusBadgeProps {
  status: "Critical" | "Stable" | "Healthy" | "Available" | "Occupied" | "Maintenance" | "Pending" | "In Progress" | "Completed" | "Cancelled";
  className?: string;
}

const StatusBadge = ({ status, className }: StatusBadgeProps) => {
  const getStatusClasses = () => {
    switch (status) {
      case "Critical":
        return "bg-hospital-red text-white font-medium";
      case "Stable":
        return "bg-hospital-blue text-white font-medium";
      case "Healthy":
        return "bg-hospital-green text-white font-medium";
      case "Available":
        return "bg-hospital-green text-white font-medium";
      case "Occupied":
        return "bg-hospital-red text-white font-medium";
      case "Maintenance":
        return "bg-gray-500 text-white font-medium";
      case "Pending":
        return "bg-amber-500 text-white font-medium";
      case "In Progress":
        return "bg-blue-500 text-white font-medium";
      case "Completed":
        return "bg-hospital-green text-white font-medium";
      case "Cancelled":
        return "bg-gray-500 text-white font-medium";
      default:
        return "bg-gray-500 text-white font-medium";
    }
  };

  return (
    <span 
      className={cn(
        "inline-flex px-2.5 py-1 text-xs rounded-full",
        getStatusClasses(), 
        className
      )}
    >
      {status}
    </span>
  );
};

export default StatusBadge;
