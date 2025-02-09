package com.project.telemedicine.repository;

import com.project.telemedicine.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment,String> {

    List<Appointment> findByDoctorId(String doctorId);
}
