
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";

interface DashboardCardProps {
  title: string;
  value: string | number;
  description?: string;
  icon?: React.ReactNode;
  className?: string;
  trend?: {
    value: number;
    isPositive: boolean;
  };
}

const DashboardCard = ({ 
  title, 
  value, 
  description, 
  icon, 
  className,
  trend 
}: DashboardCardProps) => {
  return (
    <Card className={cn("h-full transition-all hover:shadow-md", className)}>
      <CardHeader className="flex flex-row items-center justify-between pb-2 space-y-0">
        <CardTitle className="text-sm font-medium">{title}</CardTitle>
        {icon && <div className="w-4 h-4 text-muted-foreground">{icon}</div>}
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold">
          {value}
          {trend && (
            <span className={cn(
              "text-xs ml-2",
              trend.isPositive ? "text-hospital-green" : "text-hospital-red"
            )}>
              {trend.isPositive ? "+" : "-"}{trend.value}%
            </span>
          )}
        </div>
        {description && (
          <p className="text-xs text-muted-foreground mt-1">{description}</p>
        )}
      </CardContent>
    </Card>
  );
};

export default DashboardCard;
