package models

import (
	"time"

	"github.com/google/uuid"
)

// Bedroom represents a bedroom in a hospital
type Bedroom struct {
	ID        uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Code      string    `json:"code" gorm:"not null"`
	Floor     string    `json:"floor" gorm:"not null"`
	Type      string    `json:"type" gorm:"not null"`
	HospitalID uuid.UUID `json:"hospitalId" gorm:"type:uuid;not null"`
	Hospital  Hospital  `json:"hospital" gorm:"foreignKey:HospitalID"`
	StatusID  uuid.UUID `json:"statusId" gorm:"type:uuid;not null"`
	Status    Status    `json:"status" gorm:"foreignKey:StatusID"`
	CreatedAt time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 