package models

import (
	"time"

	"github.com/google/uuid"
)

// Hospital represents a hospital in the system
type Hospital struct {
	ID              uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Name            string    `json:"name" gorm:"not null;unique"`
	Phone           string    `json:"phone" gorm:"not null"`
	Escalation      string    `json:"escalation" gorm:"not null"`
	Address         Address   `json:"address" gorm:"embedded"`
	TotalCapacity   int       `json:"totalCapacity" gorm:"not null"`
	ActiveCapacity  int       `json:"activeCapacity" gorm:"not null"`
	NumberOfBeds    int       `json:"numberOfBeds" gorm:"not null"`
	NumberOfRooms   int       `json:"numberOfRooms" gorm:"not null"`
	NumberOfBuildings int     `json:"numberOfBuildings" gorm:"not null"`
	NumberOfFloors  int       `json:"numberOfFloors" gorm:"not null"`
	CreatedAt       time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt       time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 