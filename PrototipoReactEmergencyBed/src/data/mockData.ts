
export interface Patient {
  id: string;
  name: string;
  age: number;
  gender: 'Male' | 'Female' | 'Other';
  status: 'Critical' | 'Stable' | 'Healthy';
  room?: string;
  admissionDate: string;
  diagnosis: string;
  contactNumber: string;
  emergencyContact: string;
}

export interface Bed {
  id: string;
  room: string;
  floor: number;
  status: 'Available' | 'Occupied' | 'Maintenance';
  patientId?: string;
}

export interface Hospital {
  id: string;
  name: string;
  address: string;
  contactNumber: string;
  totalBeds: number;
  availableBeds: number;
}

export interface Transfer {
  id: string;
  patientId: string;
  fromHospitalId: string;
  toHospitalId: string;
  status: 'Pending' | 'In Progress' | 'Completed' | 'Cancelled';
  ambulanceId?: string;
  requestDate: string;
  completionDate?: string;
}

export const mockPatients: Patient[] = [
  {
    id: "P001",
    name: "João Silva",
    age: 45,
    gender: "Male",
    status: "Critical",
    room: "ICU-101",
    admissionDate: "2023-11-15",
    diagnosis: "Cardiac Arrest",
    contactNumber: "(11) 98765-4321",
    emergencyContact: "(11) 91234-5678"
  },
  {
    id: "P002",
    name: "Maria Oliveira",
    age: 32,
    gender: "Female",
    status: "Stable",
    room: "203",
    admissionDate: "2023-11-10",
    diagnosis: "Pneumonia",
    contactNumber: "(11) 97654-3210",
    emergencyContact: "(11) 90123-4567"
  },
  {
    id: "P003",
    name: "Carlos Santos",
    age: 67,
    gender: "Male",
    status: "Stable",
    room: "105",
    admissionDate: "2023-11-12",
    diagnosis: "Hip Fracture",
    contactNumber: "(11) 96543-2109",
    emergencyContact: "(11) 99012-3456"
  },
  {
    id: "P004",
    name: "Ana Costa",
    age: 29,
    gender: "Female",
    status: "Healthy",
    admissionDate: "2023-11-08",
    diagnosis: "Appendicitis (Post-Op)",
    contactNumber: "(11) 95432-1098",
    emergencyContact: "(11) 98901-2345"
  },
  {
    id: "P005",
    name: "Paulo Ferreira",
    age: 78,
    gender: "Male",
    status: "Critical",
    room: "ICU-103",
    admissionDate: "2023-11-14",
    diagnosis: "Stroke",
    contactNumber: "(11) 94321-0987",
    emergencyContact: "(11) 97890-1234"
  },
  {
    id: "P006",
    name: "Fernanda Lima",
    age: 41,
    gender: "Female",
    status: "Stable",
    room: "210",
    admissionDate: "2023-11-09",
    diagnosis: "Diabetes Complications",
    contactNumber: "(11) 93210-9876",
    emergencyContact: "(11) 96789-0123"
  },
  {
    id: "P007",
    name: "Roberto Alves",
    age: 55,
    gender: "Male",
    status: "Healthy",
    admissionDate: "2023-11-05",
    diagnosis: "Gallstones (Post-Op)",
    contactNumber: "(11) 92109-8765",
    emergencyContact: "(11) 95678-9012"
  },
  {
    id: "P008",
    name: "Luciana Martins",
    age: 36,
    gender: "Female",
    status: "Stable",
    room: "215",
    admissionDate: "2023-11-11",
    diagnosis: "Asthma Attack",
    contactNumber: "(11) 91098-7654",
    emergencyContact: "(11) 94567-8901"
  }
];

export const mockBeds: Bed[] = [
  { id: "B001", room: "ICU-101", floor: 1, status: "Occupied", patientId: "P001" },
  { id: "B002", room: "ICU-102", floor: 1, status: "Available" },
  { id: "B003", room: "ICU-103", floor: 1, status: "Occupied", patientId: "P005" },
  { id: "B004", room: "ICU-104", floor: 1, status: "Maintenance" },
  { id: "B005", room: "101", floor: 1, status: "Available" },
  { id: "B006", room: "102", floor: 1, status: "Available" },
  { id: "B007", room: "103", floor: 1, status: "Available" },
  { id: "B008", room: "104", floor: 1, status: "Available" },
  { id: "B009", room: "105", floor: 1, status: "Occupied", patientId: "P003" },
  { id: "B010", room: "201", floor: 2, status: "Available" },
  { id: "B011", room: "202", floor: 2, status: "Available" },
  { id: "B012", room: "203", floor: 2, status: "Occupied", patientId: "P002" },
  { id: "B013", room: "204", floor: 2, status: "Available" },
  { id: "B014", room: "205", floor: 2, status: "Maintenance" },
  { id: "B015", room: "210", floor: 2, status: "Occupied", patientId: "P006" },
  { id: "B016", room: "211", floor: 2, status: "Available" },
  { id: "B017", room: "212", floor: 2, status: "Available" },
  { id: "B018", room: "215", floor: 2, status: "Occupied", patientId: "P008" }
];

export const mockHospitals: Hospital[] = [
  {
    id: "H001",
    name: "Hospital São Paulo",
    address: "Av. Paulista, 100",
    contactNumber: "(11) 3123-4567",
    totalBeds: 200,
    availableBeds: 85
  },
  {
    id: "H002",
    name: "Hospital Santa Maria",
    address: "Rua Augusta, 500",
    contactNumber: "(11) 3234-5678",
    totalBeds: 150,
    availableBeds: 62
  },
  {
    id: "H003",
    name: "Hospital Albert Einstein",
    address: "Av. Brasil, 300",
    contactNumber: "(11) 3345-6789",
    totalBeds: 300,
    availableBeds: 120
  }
];

export const mockTransfers: Transfer[] = [
  {
    id: "T001",
    patientId: "P004",
    fromHospitalId: "H001",
    toHospitalId: "H003",
    status: "Completed",
    ambulanceId: "A001",
    requestDate: "2023-11-10",
    completionDate: "2023-11-11"
  },
  {
    id: "T002",
    patientId: "P007",
    fromHospitalId: "H002",
    toHospitalId: "H001",
    status: "Completed",
    ambulanceId: "A003",
    requestDate: "2023-11-12",
    completionDate: "2023-11-13"
  },
  {
    id: "T003",
    patientId: "P003",
    fromHospitalId: "H003",
    toHospitalId: "H001",
    status: "Pending",
    requestDate: "2023-11-15"
  }
];

export const getPatientById = (id: string): Patient | undefined => {
  return mockPatients.find(patient => patient.id === id);
};

export const getBedById = (id: string): Bed | undefined => {
  return mockBeds.find(bed => bed.id === id);
};

export const getHospitalById = (id: string): Hospital | undefined => {
  return mockHospitals.find(hospital => hospital.id === id);
};

export const getTransferById = (id: string): Transfer | undefined => {
  return mockTransfers.find(transfer => transfer.id === id);
};

export const getPatientByBedId = (bedId: string): Patient | undefined => {
  const bed = getBedById(bedId);
  if (bed && bed.patientId) {
    return getPatientById(bed.patientId);
  }
  return undefined;
};

export const getBedsByFloor = (floor: number): Bed[] => {
  return mockBeds.filter(bed => bed.floor === floor);
};

export const getAvailableBeds = (): Bed[] => {
  return mockBeds.filter(bed => bed.status === "Available");
};

export const getOccupiedBeds = (): Bed[] => {
  return mockBeds.filter(bed => bed.status === "Occupied");
};

export const getMaintenanceBeds = (): Bed[] => {
  return mockBeds.filter(bed => bed.status === "Maintenance");
};

export const getCriticalPatients = (): Patient[] => {
  return mockPatients.filter(patient => patient.status === "Critical");
};

export const getPendingTransfers = (): Transfer[] => {
  return mockTransfers.filter(transfer => transfer.status === "Pending");
};
