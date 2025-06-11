package models

// Patient represents the transformed patient data that will be sent to the Java API
type Patient struct {
	ID            string `json:"id"`
	Name          string `json:"name"`
	DateOfBirth   string `json:"dateOfBirth"`
	Gender        string `json:"gender"`
	DocumentType  string `json:"documentType"`
	DocumentNumber string `json:"documentNumber"`
	PhoneNumber   string `json:"phoneNumber"`
	Address       string `json:"address"`
	EmergencyContact struct {
		Name        string `json:"name"`
		PhoneNumber string `json:"phoneNumber"`
		Relationship string `json:"relationship"`
	} `json:"emergencyContact"`
	MedicalHistory []string `json:"medicalHistory"`
	Allergies      []string `json:"allergies"`
	BloodType      string   `json:"bloodType"`
}

// ERPPatient represents the raw patient data coming from different ERP systems
type ERPPatient struct {
	// Generic fields that can be mapped from different ERP systems
	RawData map[string]interface{} `json:"rawData"`
	Source  string                 `json:"source"` // Name of the ERP system
}

// Transform converts ERPPatient to Patient based on the source ERP system
func (ep *ERPPatient) Transform() (*Patient, error) {
	// TODO: Implement transformation logic based on the source ERP system
	// This will contain the mapping logic for different ERP systems
	return &Patient{}, nil
} 