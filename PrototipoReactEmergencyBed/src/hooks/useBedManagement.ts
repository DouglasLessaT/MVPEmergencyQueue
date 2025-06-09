import { useState, useEffect } from "react";
import { toast } from "@/components/ui/use-toast";
import { BedType, BedFormValues } from "@/components/beds/BedFormModal";
import { getFromLocalStorage, saveToLocalStorage } from "@/utils/localStorage";
import { mockBeds } from "@/data/mockData";

// Storage keys
const BEDS_STORAGE_KEY = "emergencybed_beds";
const HOSPITAL_STORAGE_KEY = "hospital_config";

export function useBedManagement() {
  // Estado para armazenar a lista de leitos
  const [beds, setBeds] = useState<BedType[]>([]);

  // Estado para armazenar a configuração do hospital
  const [hospitalConfig, setHospitalConfig] = useState<any>(null);
  
  // Estados para controlar o diálogo de cadastro/edição
  const [isOpen, setIsOpen] = useState(false);
  const [selectedBed, setSelectedBed] = useState<BedType | null>(null);

  // Carregar dados do localStorage na inicialização
  useEffect(() => {
    // Obter leitos do localStorage ou usar mockBeds como fallback
    const storedBeds = getFromLocalStorage<BedType[]>(
      BEDS_STORAGE_KEY,
      mockBeds.map(bed => ({
        id: bed.id,
        room: bed.room,
        floor: bed.floor,
        status: bed.status
      }))
    );

    setBeds(storedBeds);
    
    // Carregar configuração do hospital
    const config = getFromLocalStorage(HOSPITAL_STORAGE_KEY, null);
    if (config) {
      setHospitalConfig(config);
    }
  }, []);

  // Salvar no localStorage quando o estado de leitos mudar
  useEffect(() => {
    if (beds.length > 0) {
      saveToLocalStorage(BEDS_STORAGE_KEY, beds);
    }
  }, [beds]);

  // Função para abrir o diálogo de cadastro de novo leito
  function handleAddBed() {
    // Verificar limite de leitos configurado no hospital
    if (hospitalConfig && hospitalConfig.numeroLeitos && beds.length >= hospitalConfig.numeroLeitos) {
      toast({
        title: "Limite de leitos atingido",
        description: `O hospital tem um limite de ${hospitalConfig.numeroLeitos} leitos.`,
        variant: "destructive"
      });
      return;
    }
    
    setSelectedBed(null);
    setIsOpen(true);
  }

  // Função para abrir o diálogo de edição de leito
  function handleEditBed(bed: BedType) {
    setSelectedBed(bed);
    setIsOpen(true);
  }

  // Função para excluir um leito
  function handleDeleteBed(id: string) {
    setBeds(beds.filter(bed => bed.id !== id));
    toast({
      title: "Leito removido",
      description: "O leito foi removido com sucesso.",
    });
  }

  // Função para enviar o formulário
  function onSubmit(values: BedFormValues) {
    if (selectedBed) {
      // Editar leito existente
      setBeds(beds.map(bed => 
        bed.id === selectedBed.id ? { ...bed, ...values } : bed
      ));
      toast({
        title: "Leito atualizado",
        description: "O leito foi atualizado com sucesso.",
      });
    } else {
      // Verificar limite de leitos novamente (proteção extra)
      if (hospitalConfig && hospitalConfig.numeroLeitos && beds.length >= hospitalConfig.numeroLeitos) {
        toast({
          title: "Limite de leitos atingido",
          description: `O hospital tem um limite de ${hospitalConfig.numeroLeitos} leitos.`,
          variant: "destructive"
        });
        return;
      }
      
      // Adicionar novo leito
      const newId = `B${String(beds.length + 1).padStart(3, '0')}`;
      setBeds([...beds, { id: newId, ...values }]);
      toast({
        title: "Leito adicionado",
        description: "O novo leito foi adicionado com sucesso.",
      });
    }

    setIsOpen(false);
  }

  return {
    beds,
    isOpen,
    setIsOpen,
    selectedBed,
    handleAddBed,
    handleEditBed,
    handleDeleteBed,
    onSubmit
  };
}