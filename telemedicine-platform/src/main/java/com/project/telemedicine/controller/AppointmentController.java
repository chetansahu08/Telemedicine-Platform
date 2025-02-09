package com.project.telemedicine.controller;

import com.project.telemedicine.model.Appointment;
import com.project.telemedicine.model.Doctor;
import com.project.telemedicine.model.User;
import com.project.telemedicine.repository.AppointmentRepository;
import com.project.telemedicine.repository.DoctorRepository;
import com.project.telemedicine.repository.UserRepository;
import com.project.telemedicine.service.AppointmentService;
import com.project.telemedicine.service.PaymentService;
import com.project.telemedicine.service.SequenceGeneratorService;
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
import java.util.Map;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")
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
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, Object> appointmentData) {

        String patientId = (String) appointmentData.get("patientId");
        String doctorId = (String) appointmentData.get("doctorId");
        String date = (String) appointmentData.get("date");
        String time = (String) appointmentData.get("time");
        boolean isPaid = (boolean) appointmentData.get("isPaid");

        // Fetch doctor from database
        List<User> doctors = userRepository.findByRole("DOCTOR");

        // ✅ Find the correct doctor
        User user = doctors.stream()
                .filter(doc -> doc.getId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("❌ Doctor not found with ID: " + doctorId));

        // ✅ Check if the user is an instance of Doctor before casting
        if (!(user instanceof Doctor)) {
            throw new RuntimeException("❌ User with ID: " + doctorId + " is not a doctor.");
        }

        // ✅ Cast User to Doctor
        Doctor doctor = (Doctor) user;

        double doctorFees = doctor.getFees(); // Fetch doctor's consultation fee

        System.out.println("✅ Doctor Found: " + doctor.getName() + ", Fees: " + doctorFees);

        // Generate unique room ID for WebRTC
        String roomId = UUID.randomUUID().toString();

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setId(String.valueOf(sequenceGeneratorService.generateSequence(Appointment.SEQUENCE_NAME)));
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(isPaid ? "Confirmed" : "Not Confirmed");
        appointment.setRoomId(roomId);
        appointment.setFees(doctor.getFees());
        appointment.setPaid(isPaid);
        appointment.setCancelled(false);
        appointmentRepository.save(appointment);

        return ResponseEntity.ok("Appointment booked successfully.");
    }

    @GetMapping("/patients")
    public ResponseEntity<?> getAppointmentsByPatient(@RequestParam String patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(404).body("No appointments found for this patient.");
        }

        return ResponseEntity.ok(appointments);
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

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable String doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
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

