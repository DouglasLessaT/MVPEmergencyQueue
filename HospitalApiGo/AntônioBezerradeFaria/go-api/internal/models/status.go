package models

import (
	"time"

	"github.com/google/uuid"
)

// Status represents the status of various entities in the system
type Status struct {
	ID          uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Name        string    `json:"name" gorm:"not null;unique"`
	Code        string    `json:"code" gorm:"not null;unique"`
	Color       string    `json:"color" gorm:"not null"`
	Description string    `json:"description" gorm:"not null"`
	CreatedAt   time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt   time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 