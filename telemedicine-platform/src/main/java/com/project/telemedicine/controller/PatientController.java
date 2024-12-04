package com.project.telemedicine.controller;


import com.project.telemedicine.model.Patient;
import com.project.telemedicine.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Patient patient, HttpSession session) {
        Patient authenticatedPatient = patientService.getPatientByEmailAndPassword(patient.getEmail(),patient.getPassword());
        if(authenticatedPatient != null){
            session.setAttribute("patientId", authenticatedPatient.getId());
            session.setAttribute("patientName", authenticatedPatient.getName());

            return ResponseEntity.ok("Login Successful.Session ID:"+session.getId());
        }
        else{
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Patient patient,HttpSession session){
        Patient newPatient = patientService.createPatient(patient);
        if(newPatient != null){
            session.setAttribute("id", newPatient.getId());
            session.setAttribute("name", newPatient.getName());
            session.setAttribute("email", newPatient.getEmail());
            session.setAttribute("age", newPatient.getAge());
            session.setAttribute("medicalHistory", newPatient.getMedicalHistory());
            session.setAttribute("password",newPatient.getPassword());

            return ResponseEntity.ok("Registration Successful.Session ID:"+session.getId());
        }else {
            return ResponseEntity.status(401).body("Registration Failed");
        }
    }

    @GetMapping("/session")
    public ResponseEntity<String> getSessionData(HttpSession session){
        String patientName = (String) session.getAttribute("patientName");
        if (patientName != null) {
            return ResponseEntity.ok("Logged-In Patient Name: " + patientName);
        } else {
            return ResponseEntity.status(401).body("No active session found");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Logout Successful");
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.createPatient(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
