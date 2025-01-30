package com.project.telemedicine.model;


import java.io.Serializable;

public class Availability implements Serializable {
    private static final long serialVersionUID = 1L;

    private String day; // Example: "Monday", "Tuesday", etc.
    private String startTime; // Example: "10:00 AM"
    private String endTime; // Example: "2:00 PM"

    public Availability() {
    }

    public Availability(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

