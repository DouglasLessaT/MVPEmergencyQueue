
import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Hospital, User, List, LogIn, ArrowRight, Settings } from "lucide-react";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from "@/components/ui/navigation-menu";
import { cn } from "@/lib/utils";

const AppNavbar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isOpen, setIsOpen] = useState(false);

  const links = [
    { name: "Dashboard", path: "/dashboard", icon: <Hospital className="mr-2 h-4 w-4" /> },
    { name: "Pacientes", path: "/patients", icon: <User className="mr-2 h-4 w-4" /> },
    { name: "Atendimentos na Emergencia", path: "/atendimento", icon: <Hospital className="mr-2 h-4 w-4" /> },
    { name: "Transferências", path: "/transfers", icon: <ArrowRight className="mr-2 h-4 w-4" /> }
  ];

  const ajusteLinks = [
    { name: "Hospital", path: "/settings/hospital", icon: <Hospital className="mr-2 h-4 w-4" /> },
    { name: "Status Entidade", path: "/settings/status", icon: <User className="mr-2 h-4 w-4" /> },
    { name: "Quartos", path: "/settings/RoomManagement", icon: <Hospital className="mr-2 h-4 w-4" /> },
    { name: "Leitos", path: "/beds", icon: <Hospital className="mr-2 h-4 w-4" /> }
  ];

  const isActive = (path: string) => {
    return location.pathname === path ||
      (path.startsWith("/settings") && location.pathname.startsWith("/settings"));
  };

  const navigateTo = (path: string) => {
    navigate(path);
    setIsOpen(false);
  };

  // Hide navbar on login page
  if (location.pathname === "/" || location.pathname === "/login") {
    return null;
  }

  return (
    <header className="bg-white border-b sticky top-0 z-10">
      <div className="container mx-auto px-4 flex h-16 items-center justify-between">
        <div className="flex items-center">
          <Hospital className="h-6 w-6 text-hospital-blue mr-2" />
          <span className="font-bold text-xl text-hospital-blue">EmergencyBed</span>
        </div>

        {/* Desktop Navigation */}
        <nav className="hidden md:flex space-x-4">
          {links.map((link) => (
            <Button
              key={link.path}
              variant={isActive(link.path) ? "default" : "ghost"}
              className={isActive(link.path) ? "bg-hospital-blue text-white" : ""}
              onClick={() => navigateTo(link.path)}
            >
              {link.icon}
              {link.name}
            </Button>
          ))}

          {/* Ajustes Dropdown Menu */}
          <NavigationMenu>
            <NavigationMenuList>
              <NavigationMenuItem>
                <NavigationMenuTrigger
                  className={`px-4 py-2 flex items-center ${location.pathname.startsWith("/settings") ? "bg-hospital-blue text-white" : ""}`}
                >
                  <Settings className="mr-2 h-4 w-4" />
                  Ajustes
                </NavigationMenuTrigger>
                <NavigationMenuContent>
                  <ul className="grid w-[200px] gap-2 p-4">
                    {ajusteLinks.map((link) => (
                      <li key={link.path}>
                        <NavigationMenuLink asChild>
                          <Button
                            variant="ghost"
                            className="w-full justify-start"
                            onClick={() => navigateTo(link.path)}
                          >
                            {link.icon}
                            {link.name}
                          </Button>
                        </NavigationMenuLink>
                      </li>
                    ))}
                  </ul>
                </NavigationMenuContent>
              </NavigationMenuItem>
            </NavigationMenuList>
          </NavigationMenu>

          <Button variant="outline" onClick={() => navigateTo("/")}>
            <LogIn className="mr-2 h-4 w-4" />
            Sair
          </Button>
        </nav>

        {/* Mobile Navigation */}
        <Sheet open={isOpen} onOpenChange={setIsOpen}>
          <SheetTrigger asChild className="md:hidden">
            <Button variant="ghost" size="icon">
              <List className="h-6 w-6" />
            </Button>
          </SheetTrigger>
          <SheetContent side="right">
            <div className="flex flex-col space-y-4 mt-8">
              {links.map((link) => (
                <Button
                  key={link.path}
                  variant={isActive(link.path) ? "default" : "ghost"}
                  className={isActive(link.path) ? "bg-hospital-blue text-white justify-start" : "justify-start"}
                  onClick={() => navigateTo(link.path)}
                >
                  {link.icon}
                  {link.name}
                </Button>
              ))}

              <div className="py-2 px-2 font-semibold text-sm">AJUSTES</div>

              {ajusteLinks.map((link) => (
                <Button
                  key={link.path}
                  variant={isActive(link.path) ? "default" : "ghost"}
                  className={isActive(link.path) ? "bg-hospital-blue text-white justify-start ml-4" : "justify-start ml-4"}
                  onClick={() => navigateTo(link.path)}
                >
                  {link.icon}
                  {link.name}
                </Button>
              ))}

              <Button variant="outline" onClick={() => navigateTo("/")} className="justify-start">
                <LogIn className="mr-2 h-4 w-4" />
                Sair
              </Button>
            </div>
          </SheetContent>
        </Sheet>
      </div>
    </header>
  );
};

export default AppNavbar;
