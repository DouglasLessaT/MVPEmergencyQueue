package models

import (
	"time"

	"github.com/google/uuid"
)

// Ambulance represents an ambulance in the system
type Ambulance struct {
	ID           uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	LicensePlate string    `json:"licensePlate" gorm:"not null;unique"`
	Model        string    `json:"model" gorm:"not null"`
	Type         string    `json:"type" gorm:"not null"`
	Renavam      string    `json:"renavam" gorm:"not null;unique"`
	HospitalID   uuid.UUID `json:"hospitalId" gorm:"type:uuid;not null"`
	Hospital     Hospital  `json:"hospital" gorm:"foreignKey:HospitalID"`
	CreatedAt    time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt    time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 