package com.project.telemedicine.repository;

import com.project.telemedicine.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment,String> {}
