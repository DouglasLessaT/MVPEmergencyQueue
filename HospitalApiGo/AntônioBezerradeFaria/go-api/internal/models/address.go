package models

import (
	"time"

	"github.com/google/uuid"
)

// Address represents a physical address
type Address struct {
	ID           uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Street       string    `json:"street" gorm:"not null"`
	Number       string    `json:"number" gorm:"not null"`
	Neighborhood string    `json:"neighborhood" gorm:"not null"`
	Country      string    `json:"country" gorm:"not null"`
	ZipCode      string    `json:"zipCode" gorm:"not null"`
	State        string    `json:"state" gorm:"not null"`
	City         string    `json:"city" gorm:"not null"`
	CreatedAt    time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt    time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 