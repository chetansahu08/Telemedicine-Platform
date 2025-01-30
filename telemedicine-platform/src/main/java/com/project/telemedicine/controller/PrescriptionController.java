package com.project.telemedicine.controller;

import com.project.telemedicine.model.Prescription;
import com.project.telemedicine.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService PrescriptionService;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return PrescriptionService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String id) {
        return ResponseEntity.ok(PrescriptionService.getPrescriptionById(id));
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription Prescription) {
        return ResponseEntity.ok(PrescriptionService.createPrescription(Prescription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable String id, @RequestBody Prescription Prescription) {
        return ResponseEntity.ok(PrescriptionService.updatePrescription(id, Prescription));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable String id) {
        PrescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}

