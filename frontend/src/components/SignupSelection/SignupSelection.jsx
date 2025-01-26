import React from "react";
import "./SignupSelection.css";
import { useNavigate } from "react-router-dom";

const SignupSelection = () => {
  const navigate = useNavigate()

const togglePatient = () => {
  navigate("/patientSignup")
}

const toggleDoctor = () => {
  navigate("/doctorSignup")
}


  return (
    <div className="container">
      <h1 className="title">Signup As</h1>
      <p className="subtitle">Select your designation to proceed</p>
      <div className="selection">
        <div className="card doctor" onClick={toggleDoctor}>
          <h2 className="card-title">Doctor</h2>
          <p className="card-description">Signup for Doctors</p>
        </div>
        <div className="card patient" onClick={togglePatient}>
          <h2 className="card-title">Patient</h2>
          <p className="card-description">Signup for Patients</p>
        </div>
      </div>
    </div>
  );
};

export default SignupSelection;
