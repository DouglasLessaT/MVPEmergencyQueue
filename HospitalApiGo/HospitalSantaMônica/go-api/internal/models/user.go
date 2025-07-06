package models

import (
	"time"

	"github.com/google/uuid"
)

// User represents a user in the system
type User struct {
	ID        uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	Login     string    `json:"login" gorm:"not null;unique"`
	Password  string    `json:"-" gorm:"not null"` // "-" means this field won't be included in JSON
	HospitalID uuid.UUID `json:"hospitalId" gorm:"type:uuid;not null"`
	Hospital  Hospital  `json:"hospital" gorm:"foreignKey:HospitalID"`
	Permissions []string `json:"permissions" gorm:"type:text[];not null;default:'{}'"`
	CreatedAt time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 