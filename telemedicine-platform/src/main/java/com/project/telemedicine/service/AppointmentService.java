package com.project.telemedicine.service;

import com.project.telemedicine.model.Appointment;
import com.project.telemedicine.model.Prescription;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(String id);

    Appointment createAppointment(Appointment appointment);

    Appointment updateAppointment(String id, Appointment Appointment);

    void deleteAppointment(String id);

    String addPrescription(String id, String prescription);


}
