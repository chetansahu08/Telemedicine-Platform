package com.project.telemedicine.service.impl;

import com.project.telemedicine.exception.ResourceNotFoundException;
import com.project.telemedicine.model.Prescription;
import com.project.telemedicine.repository.PrescriptionRepository;
import com.project.telemedicine.service.PrescriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository PrescriptionRepository;

    @Override
    public List<Prescription> getAllPrescriptions() {
        return PrescriptionRepository.findAll();
    }

    @Override
    public Prescription getPrescriptionById(String id) {
        return PrescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id: " + id));
    }

    @Override
    public Prescription createPrescription(Prescription Prescription) {
        return PrescriptionRepository.save(Prescription);
    }

    @Override
    public Prescription updatePrescription(String id, Prescription Prescription) {
        Prescription existingPrescription = getPrescriptionById(id);
        existingPrescription.setMedicationDetails(Prescription.getMedicationDetails());
        return PrescriptionRepository.save(existingPrescription);
    }

    @Override
    public void deletePrescription(String id) {
        Prescription existingPrescription = getPrescriptionById(id);
        PrescriptionRepository.delete(existingPrescription);
    }
}

