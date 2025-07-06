package models

import (
	"time"

	"github.com/google/uuid"
)

type MedicalCare struct {
	ID            uuid.UUID    `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Floor         string       `json:"floor" gorm:"not null"`
	RoomNumber    string       `json:"roomNumber" gorm:"not null"`
	IsICU         bool         `json:"isICU" gorm:"not null"`
	PatientID     uuid.UUID    `json:"patientId" gorm:"type:uuid;not null"`
	Patient       Patient      `json:"patient" gorm:"foreignKey:PatientID"`
	StatusID      uuid.UUID    `json:"statusId" gorm:"type:uuid;not null"`
	Status        Status       `json:"status" gorm:"foreignKey:StatusID"`
	ServicePhaseID uuid.UUID   `json:"servicePhaseId" gorm:"type:uuid;not null"`
	ServicePhase  ServicePhase `json:"servicePhase" gorm:"foreignKey:ServicePhaseID"`
	CreatedAt     time.Time    `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt     time.Time    `json:"updatedAt" gorm:"autoUpdateTime"`
} 