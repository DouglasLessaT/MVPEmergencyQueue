package models

import (
	"time"

	"github.com/google/uuid"
)

// Company represents a company in the system
type Company struct {
	ID        uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Name      string    `json:"name" gorm:"not null;unique"`
	CNPJ      string    `json:"cnpj" gorm:"not null;unique"`
	CreatedAt time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 