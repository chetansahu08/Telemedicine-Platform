package com.project.telemedicine.controller;

import com.project.telemedicine.model.Appointment;
import com.project.telemedicine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService AppointmentService;

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
}

