package models

import (
	"time"

	"github.com/google/uuid"
)

// Transfer represents a patient transfer between hospitals
type Transfer struct {
	ID                      uuid.UUID `json:"id" gorm:"type:uuid;primary_key;default:uuid_generate_v4()"`
	PatientID              uuid.UUID `json:"patientId" gorm:"type:uuid;not null"`
	Patient                Patient   `json:"patient" gorm:"foreignKey:PatientID"`
	OriginHospitalID       uuid.UUID `json:"originHospitalId" gorm:"type:uuid;not null"`
	OriginHospital         Hospital  `json:"originHospital" gorm:"foreignKey:OriginHospitalID"`
	DestinationHospitalID  uuid.UUID `json:"destinationHospitalId" gorm:"type:uuid;not null"`
	DestinationHospital    Hospital  `json:"destinationHospital" gorm:"foreignKey:DestinationHospitalID"`
	AmbulanceID            uuid.UUID `json:"ambulanceId" gorm:"type:uuid;not null"`
	Ambulance              Ambulance `json:"ambulance" gorm:"foreignKey:AmbulanceID"`
	Date                   time.Time `json:"date" gorm:"not null"`
	StatusID               uuid.UUID `json:"statusId" gorm:"type:uuid;not null"`
	Status                 Status    `json:"status" gorm:"foreignKey:StatusID"`
	DestinationAddressID   uuid.UUID `json:"destinationAddressId" gorm:"type:uuid;not null"`
	DestinationAddress     Address   `json:"destinationAddress" gorm:"foreignKey:DestinationAddressID"`
	OriginAddressID        uuid.UUID `json:"originAddressId" gorm:"type:uuid;not null"`
	OriginAddress          Address   `json:"originAddress" gorm:"foreignKey:OriginAddressID"`
	CreatedAt              time.Time `json:"createdAt" gorm:"autoCreateTime"`
	UpdatedAt              time.Time `json:"updatedAt" gorm:"autoUpdateTime"`
} 