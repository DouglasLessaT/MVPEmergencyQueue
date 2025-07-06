package models

import (
	"time"

	"github.com/google/uuid"
)

// ServicePhase represents a phase in the medical service process
type ServicePhase struct {
	ID          uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Name        string    `json:"name" gorm:"not null;unique"`
	Code        string    `json:"code" gorm:"not null;unique"`
	Color       string    `json:"color" gorm:"not null"`
	Description string    `json:"description" gorm:"not null"`
	CreatedAt   time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt   time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 