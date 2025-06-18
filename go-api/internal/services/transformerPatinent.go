package services

import (
	"context"
	"fmt"
	"github.com/DouglasLessa/emergency-queue-go-api/internal/models"
	"github.com/go-resty/resty/v2"
)

type TransformerService struct {
	javaAPIURL string
	client     *resty.Client
}

func NewTransformerService(javaAPIURL string) *TransformerService {
	return &TransformerService{
		javaAPIURL: javaAPIURL,
		client:     resty.New(),
	}
}

func (s *TransformerService) ForwardToJavaAPI(ctx context.Context, patient *models.Patient) error {
	resp, err := s.client.R().
		SetContext(ctx).
		SetBody(patient).
		Post(fmt.Sprintf("%s/api/patients", s.javaAPIURL))

	if err != nil {
		return fmt.Errorf("failed to forward patient data to Java API: %w", err)
	}

	if resp.StatusCode() >= 400 {
		return fmt.Errorf("Java API returned error status: %d", resp.StatusCode())
	}

	return nil
}

func (s *TransformerService) GetPatientFromJavaAPI(ctx context.Context, patientID string) (*models.Patient, error) {
	var patient models.Patient
	
	resp, err := s.client.R().
		SetContext(ctx).
		SetResult(&patient).
		Get(fmt.Sprintf("%s/api/patients/%s", s.javaAPIURL, patientID))

	if err != nil {
		return nil, fmt.Errorf("failed to get patient from Java API: %w", err)
	}

	if resp.StatusCode() == 404 {
		return nil, fmt.Errorf("patient not found")
	}

	if resp.StatusCode() >= 400 {
		return nil, fmt.Errorf("Java API returned error status: %d", resp.StatusCode())
	}

	return &patient, nil
}

func (s *TransformerService) UpdatePatientInJavaAPI(ctx context.Context, patientID string, patient *models.Patient) error {
	resp, err := s.client.R().
		SetContext(ctx).
		SetBody(patient).
		Put(fmt.Sprintf("%s/api/patients/%s", s.javaAPIURL, patientID))

	if err != nil {
		return fmt.Errorf("failed to update patient in Java API: %w", err)
	}

	if resp.StatusCode() == 404 {
		return fmt.Errorf("patient not found")
	}

	if resp.StatusCode() >= 400 {
		return fmt.Errorf("Java API returned error status: %d", resp.StatusCode())
	}

	return nil
} 