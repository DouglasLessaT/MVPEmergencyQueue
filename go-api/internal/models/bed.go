package models

import (
	"time"

	"github.com/google/uuid"
)

// Bed represents a bed in a bedroom
type Bed struct {
	ID          uuid.UUID  `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Code        string     `json:"code" gorm:"not null"`
	Floor       string     `json:"floor" gorm:"not null"`
	Type        string     `json:"type" gorm:"not null"`
	StatusID    uuid.UUID  `json:"statusId" gorm:"type:uuid;not null"`
	Status      Status     `json:"status" gorm:"foreignKey:StatusID"`
	PatientID   *uuid.UUID `json:"patientId" gorm:"type:uuid"`
	Patient     *Patient   `json:"patient" gorm:"foreignKey:PatientID"`
	BedroomID   uuid.UUID  `json:"bedroomId" gorm:"type:uuid;not null"`
	Bedroom     Bedroom    `json:"bedroom" gorm:"foreignKey:BedroomID"`
	EntryPatient *time.Time `json:"entryPatient"`
	ExitPatient  *time.Time `json:"exitPatient"`
	CreatedAt   time.Time  `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt   time.Time  `json:"updatedAt" gorm:"autoUpdateTime"`
} 