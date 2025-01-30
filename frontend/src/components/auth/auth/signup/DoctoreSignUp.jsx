import React, { useState } from "react";
import "./signup.css";
import group from "../../../../assets/group-of-doctors.jpg"
import { FaEye, FaEyeSlash } from "react-icons/fa";

const DoctorSignup = () => {
  const [passwordShown, setPasswordShown] = useState(false);
  const [confirmPasswordShown, setConfirmPasswordShown] = useState(false);
  const [availability, setAvailability] = useState("Monday");
  const [selectedTimeSlot, setSelectedTimeSlot] = useState("");

  const togglePasswordVisibility = () => {
    setPasswordShown(!passwordShown);
  };

  const toggleConfirmPasswordVisibility = () => {
    setConfirmPasswordShown(!confirmPasswordShown);
  };

  const timeSlots = [
    "08:00 AM - 01:00 PM",
    "09:00 AM - 02:00 PM",
    "10:00 AM - 03:00 PM",
    "11:00 AM - 04:00 PM",
    "12:00 PM - 05:00 PM",
    "01:00 PM - 06:00 PM",
    "02:00 PM - 07:00 PM",
  ];


  return (
    <div className="page-container">
      <div className="image-section">
        <img
          src={group}
          alt="Doctors Group"
          className="doctors-image"
        />
      </div>
      <div className="signup-container">
      <div className="logo">
        <h1>Doctor Registration</h1>
      </div>
      <div className="signup-form">
        <form action="">
          {/* Hidden Field for Registration Type */}
          <input type="hidden" value="Doctor" name="registrationType" />

          <div className="input-group">
            <input type="text" placeholder="Full Name" required />
          </div>

          <div className="input-group">
            <input type="email" placeholder="Email" required />
          </div>

          <div className="input-group">
            <input
              type={passwordShown ? "text" : "password"}
              placeholder="Password"
              required
            />
            <button
              type="button"
              className="toggle-btn"
              onClick={togglePasswordVisibility}
            >
              {passwordShown ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>

          <div className="input-group">
            <input
              type={confirmPasswordShown ? "text" : "password"}
              placeholder="Confirm Password"
              required
            />
            <button
              type="button"
              className="toggle-btn"
              onClick={toggleConfirmPasswordVisibility}
            >
              {confirmPasswordShown ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>

          <div className="input-group">
            <input type="text" placeholder="Specialization" required />
          </div>

          <div className="input-group">
            <input type="number" placeholder="Consultant Fees" required />
          </div>

          <div className="input-group">
            <label>Availability Day</label>
            <select
              value={availability}
              onChange={(e) => setAvailability(e.target.value)}
            >
              <option value="Monday">Monday</option>
              <option value="Tuesday">Tuesday</option>
              <option value="Wednesday">Wednesday</option>
              <option value="Thursday">Thursday</option>
              <option value="Friday">Friday</option>
              <option value="Saturday">Saturday</option>
              <option value="Sunday">Sunday</option>
            </select>
          </div>

          <div className="input-group">
            <label>Availability Time Slot</label>
            <select
              value={selectedTimeSlot}
              onChange={(e) => setSelectedTimeSlot(e.target.value)}
              required
            >
              <option value="">Select Time Slot</option>
              {timeSlots.map((slot, index) => (
                <option key={index} value={slot}>
                  {slot}
                </option>
              ))}
            </select>
          </div>

          <div className="action">
            <button type="submit" className="signup-btn">
              Signup
            </button>
          </div>
        </form>
      </div>
    </div>
</div>
  );
};

export default DoctorSignup;


