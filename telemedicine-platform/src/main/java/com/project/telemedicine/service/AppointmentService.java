package com.project.telemedicine.service;

import com.project.telemedicine.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(String id);

    Appointment createAppointment(Appointment appointment);

    Appointment updateAppointment(String id, Appointment Appointment);

    void deleteAppointment(String id);
}
