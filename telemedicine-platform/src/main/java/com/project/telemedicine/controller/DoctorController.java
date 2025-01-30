package com.project.telemedicine.controller;

import com.project.telemedicine.model.Doctor;
import com.project.telemedicine.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService DoctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return DoctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable String id) {
        return ResponseEntity.ok(DoctorService.getDoctorById(id));
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor Doctor) {
        return ResponseEntity.ok(DoctorService.createDoctor(Doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable String id, @RequestBody Doctor Doctor) {
        return ResponseEntity.ok(DoctorService.updateDoctor(id, Doctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        DoctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}

