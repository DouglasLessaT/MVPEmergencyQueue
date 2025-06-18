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

type ERPPatient struct {
	RawData map[string]interface{} `json:"rawData"`
	Source  string                 `json:"source"` 
}

func (ep *ERPPatient) Transform() (*Patient, error) {
	return &Patient{}, nil
} 