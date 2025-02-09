package com.project.telemedicine.service.impl;

import com.project.telemedicine.exception.ResourceNotFoundException;
import com.project.telemedicine.model.Appointment;
import com.project.telemedicine.repository.AppointmentRepository;
import com.project.telemedicine.service.AppointmentService;

import com.project.telemedicine.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        appointment.setId(String.valueOf(sequenceGeneratorService.generateSequence(Appointment.SEQUENCE_NAME)));
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(String id, Appointment Appointment) {
        Appointment existingAppointment = getAppointmentById(id);
        existingAppointment.setDate(Appointment.getDate());
        existingAppointment.setTime(Appointment.getTime());
        existingAppointment.setStatus(Appointment.getStatus());
        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(String id) {
        Appointment existingAppointment = getAppointmentById(id);
        appointmentRepository.delete(existingAppointment);
    }

    @Override
    public String addPrescription(String id, String prescription) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found")); // No Optional

        appointment.setPrescription(prescription);
        appointmentRepository.save(appointment);
        return "Prescription added successfully";
    }
}

