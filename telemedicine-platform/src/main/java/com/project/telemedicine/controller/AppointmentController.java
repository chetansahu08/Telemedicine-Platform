package com.project.telemedicine.controller;

import com.project.telemedicine.model.Appointment;
import com.project.telemedicine.model.Doctor;
import com.project.telemedicine.repository.AppointmentRepository;
import com.project.telemedicine.repository.DoctorRepository;
import com.project.telemedicine.service.AppointmentService;
import com.project.telemedicine.service.PaymentService;
import com.project.telemedicine.utils.PdfGenerator;
import com.razorpay.RazorpayException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService AppointmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            HttpSession session,
            @RequestParam String doctorId,
            @RequestParam String date,
            @RequestParam String time) {

        String patientId = (String) session.getAttribute("patientId");
        if (patientId == null) {
            return ResponseEntity.status(401).body("Unauthorized: Please log in first");
        }

        // Fetch doctor details
        Doctor doctor = doctorRepository.findById(doctorId)
                .filter(user -> "DOCTOR".equals(user.getRole()))
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        double doctorFees = doctor.getFees();

        try {
            // ✅ Generate a payment order using doctor's fee
            String paymentOrder = paymentService.createPaymentOrder(doctorFees);

            // ✅ Generate a unique room ID for WebRTC video call
            String roomId = UUID.randomUUID().toString();

            // ✅ Create appointment
            Appointment appointment = new Appointment(patientId, doctorId, date, time, "BOOKED", roomId, doctorFees);
            appointmentRepository.save(appointment);

            return ResponseEntity.ok(paymentOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
        }
    }


    //  Confirm payment after success
    @PutMapping("/{appointmentId}/confirm-payment")
    public ResponseEntity<String> confirmPayment(@PathVariable String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setPaid(true);
        appointmentRepository.save(appointment);
        return ResponseEntity.ok("Payment successful");
    }

    //  Cancel appointment within one day for full refund
    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        LocalDate bookingDate = appointment.getBookingDate();
        LocalDate today = LocalDate.now();

        if (today.isEqual(bookingDate) || today.isBefore(bookingDate.plusDays(1))) {
            appointment.setCancelled(true);
            appointment.setPaid(false);
            appointmentRepository.save(appointment);
            return ResponseEntity.ok("Appointment cancelled. Full refund will be processed.");
        } else {
            return ResponseEntity.badRequest().body("Cancellation period expired. No refund available.");
        }
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return AppointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
        return ResponseEntity.ok(AppointmentService.getAppointmentById(id));
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment Appointment) {
        String roomId = UUID.randomUUID().toString(); // Generate unique room ID
        Appointment.setRoomId(roomId);
        return ResponseEntity.ok(AppointmentService.createAppointment(Appointment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String id, @RequestBody Appointment Appointment) {
        return ResponseEntity.ok(AppointmentService.updateAppointment(id, Appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
        AppointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{appointmentId}/prescription")
    public ResponseEntity<String> addPrescription(@PathVariable String appointmentId, @RequestBody String prescription) {

        return ResponseEntity.ok(AppointmentService.addPrescription(appointmentId, prescription));
    }

    @GetMapping("/{appointmentId}/download-prescription")
    public ResponseEntity<byte[]> downloadPrescription(@PathVariable String appointmentId) {
        Appointment appointment = AppointmentService.getAppointmentById(appointmentId); // No Optional

        if (appointment.getPrescription() == null || appointment.getPrescription().isEmpty()) {
            throw new RuntimeException("No prescription available for this appointment");
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


}

