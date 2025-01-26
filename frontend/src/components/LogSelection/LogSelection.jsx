import React from "react";
import "./LogSelection.css";
import { useNavigate } from "react-router-dom";

const LogSelection = () => {
const navigate = useNavigate()

const togglePatient = () => {
  navigate("/patientLogin")
}

const toggleDoctor = () => {
  navigate("/doctorLogin")
}

  return (
    <div className="container">
      <h1 className="title">Login As</h1>
      <p className="subtitle">Select your designation to proceed</p>
      <div className="selection">
        <div className="card doctor" onClick={toggleDoctor}>
          <h2 className="card-title">Doctor</h2>
          <p className="card-description">Login for Doctors</p>
        </div>
        <div className="card patient" onClick={togglePatient}>
          <h2 className="card-title">Patient</h2>
          <p className="card-description">Login for Patients</p>
        </div>
      </div>
    </div>
  );
};

export default LogSelection;
