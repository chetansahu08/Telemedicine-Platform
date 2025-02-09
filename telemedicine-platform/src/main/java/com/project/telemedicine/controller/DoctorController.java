package com.project.telemedicine.controller;

import com.project.telemedicine.model.Appointment;
import com.project.telemedicine.model.Doctor;
import com.project.telemedicine.model.User;
import com.project.telemedicine.repository.AppointmentRepository;
import com.project.telemedicine.repository.UserRepository;
import com.project.telemedicine.service.DoctorService;
import com.project.telemedicine.utils.PdfGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService DoctorService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get all appointments for logged-in doctor
    @GetMapping("/appointments")
    public ResponseEntity<?> getDoctorAppointments(HttpSession session) {
        String doctorId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (doctorId == null || !"DOCTOR".equals(role)) {
            return ResponseEntity.status(401).body("Unauthorized: Only doctors can access this");
        }

        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    // ✅ Get all prescriptions given by the doctor
    @GetMapping("/prescriptions")
    public ResponseEntity<?> getDoctorPrescriptions(HttpSession session) {
        String doctorId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (doctorId == null || !"DOCTOR".equals(role)) {
            return ResponseEntity.status(401).body("Unauthorized: Only doctors can access this");
        }

        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        return ResponseEntity.ok(appointments.stream()
                .filter(a -> a.getPrescription() != null && !a.getPrescription().isEmpty())
                .map(a -> Map.of(
                        "patientId", a.getPatientId(),
                        "appointmentId", a.getId(),
                        "prescription", a.getPrescription()
                )));
    }

    @GetMapping("/prescriptions/download/{appointmentId}")
    public ResponseEntity<byte[]> downloadPastPrescription(@PathVariable String appointmentId, HttpSession session) {
        String doctorId = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (doctorId == null || !"DOCTOR".equals(role)) {
            return ResponseEntity.status(401).body("Unauthorized".getBytes());
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctorId().equals(doctorId)) {
            return ResponseEntity.status(403).body("Unauthorized: This prescription does not belong to you".getBytes());
        }

        if (appointment.getPrescription() == null || appointment.getPrescription().isEmpty()) {
            return ResponseEntity.status(400).body("No prescription available for this appointment".getBytes());
        }

        byte[] pdfBytes = PdfGenerator.generatePrescriptionPdf(
                "Patient " + appointment.getPatientId(),
                "Doctor " + appointment.getDoctorId(),
                appointment.getDate(),
                appointment.getPrescription()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "prescription.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllDoctors() {
        List<User> doctors = userRepository.findByRole("DOCTOR");

        if (doctors.isEmpty()) {
            return ResponseEntity.status(404).body("No doctors found.");
        }

        return ResponseEntity.ok(doctors);
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

