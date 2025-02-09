package com.project.telemedicine.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "appointments")
public class Appointment {

    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "appointment_sequence";

    @Id
    private String id;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;
    private String roomId;
    private String prescription;
    private double fees;
    private boolean isPaid;
    private boolean isCancelled;
    private LocalDate bookingDate;

    public Appointment(String doctorId, String date, String time, String status, String roomId, String patientId, double fees) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = status;
        this.roomId = roomId;
        this.patientId = patientId;
        this.prescription = "";
        this.fees = fees;
        this.isPaid = false; // Payment must be completed at booking
        this.isCancelled = false; // Default value
        this.bookingDate = LocalDate.now(); // Set booking date
    }


    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPrescription() {
        return prescription;
    }
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}

