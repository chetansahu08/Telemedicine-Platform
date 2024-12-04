package com.project.telemedicine.service;

import com.project.telemedicine.model.Prescription;

import java.util.List;


public interface PrescriptionService {
    List<Prescription> getAllPrescriptions();

    Prescription getPrescriptionById(String id);

    Prescription createPrescription(Prescription Prescription);

    Prescription updatePrescription(String id, Prescription Prescription);

    void deletePrescription(String id);
}
