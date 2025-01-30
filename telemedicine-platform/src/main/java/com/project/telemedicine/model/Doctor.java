package com.project.telemedicine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import java.util.List;

@Document(collection = "doctors")
public class Doctor extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Transient
    public static final String SEQUENCE_NAME = "doctor_sequence";

    private String specialization;
    private float fees;
    private List<Availability> availability;

    @JsonProperty("role") // Explicitly include the role field in the JSON response
    public String getRole() {
        return super.getRole();
    }

    public float getFees() {
        return fees;
    }
    public void setFees(float fees) {
        this.fees = fees;
    }

    public List<Availability> getAvailability() {
        return availability;
    }
    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
