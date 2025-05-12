package com.DouglasLessa.emergencyBedApi.model.integration;

import com.DouglasLessa.emergencyBedApi.model.domain.Hospital;
import com.DouglasLessa.emergencyBedApi.model.domain.Patient;

import lombok.Getter;
import lombok.Setter;

import com.DouglasLessa.emergencyBedApi.model.domain.MedicalCare;

import java.util.List;

@Getter
@Setter
public class ERPData {
    private Hospital hospital;
    private List<Patient> patients;
    private List<MedicalCare> medicalCares;
}