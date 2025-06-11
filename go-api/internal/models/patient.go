package models

import (
	"time"

	"github.com/google/uuid"
)

// Patient represents a patient in the system
type Patient struct {
	ID              uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Name            string    `json:"name" gorm:"not null"`
	Gender          string    `json:"gender" gorm:"not null"`
	Age             int       `json:"age" gorm:"not null"`
	Insurance       string    `json:"insurance"`
	HealthPlan      string    `json:"healthPlan"`
	CPF             string    `json:"cpf" gorm:"unique;not null"`
	RG              string    `json:"rg" gorm:"unique;not null"`
	MotherName      string    `json:"motherName" gorm:"not null"`
	FatherName      string    `json:"fatherName"`
	MedicalRecordPDF string    `json:"medicalRecordPDF"`
	CreatedAt       time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt       time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
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