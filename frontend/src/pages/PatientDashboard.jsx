import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const PatientDashboard = () => {
  const navigate = useNavigate();
  const [patient, setPatient] = useState(null);
  const [doctors, setDoctors] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [prescriptions, setPrescriptions] = useState([]);
  

  useEffect(() => {
    // ✅ Get logged-in patient from localStorage
    const storedUser = JSON.parse(localStorage.getItem("user"));

    if (!storedUser || storedUser.role !== "PATIENT") {
      alert("Unauthorized access. Redirecting...");
      navigate("/login");
      return;
    }

    // ✅ Set patient data from localStorage
    setPatient(storedUser);

    // ✅ Fetch all available doctors
    axios
      .get("http://localhost:8080/api/doctors/")
      .then((response) => {
        setDoctors(response.data);
      })
      .catch((error) => {
        console.error("Error fetching doctors:", error);
      });

    
}, [navigate]);

  const handleBookAppointment = (doctor) => {
    navigate("/bookappointment", { state: { doctor } });
  };

  if (!patient) return <div className="text-center p-4">Loading patient details...</div>;

  return (
    <div className="bg-gray-100 p-8 min-h-screen">
      <h1 className="text-3xl font-bold mb-6 text-center text-gray-800">Patient Dashboard</h1>

      {/* Patient Info */}
      <div className="bg-white shadow-lg rounded-2xl p-6 mb-8">
        <h2 className="text-xl font-semibold mb-3 text-gray-800">Patient Details</h2>
        <p className="text-gray-600"><strong>Name:</strong> {patient.name}</p>
        <p className="text-gray-600"><strong>Email:</strong> {patient.email}</p>
        <p className="text-gray-600"><strong>Age:</strong> {patient.age}</p>
        <p className="text-gray-600"><strong>Medical History:</strong> {patient.medicalHistory}</p>
        
      </div>

      {/* Available Doctors */}
      <div className="bg-white shadow-lg rounded-2xl p-6 mb-8">
        <h2 className="text-xl font-semibold mb-3 text-gray-800">Available Doctors</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {doctors.map((doctor) => (
            <div key={doctor.id} className="p-4 shadow-xl border rounded-md">
              <h3 className="text-lg font-bold">{doctor.name}</h3>
              <p className="text-gray-600">Specialization: {doctor.specialization}</p>
              <p className="text-gray-600">Consultation Fee: ₹{doctor.fees}</p>

              <div className="mt-2">
                <button 
                  className="w-full p-2 bg-blue-500 text-white rounded-md"
                  onClick={() => handleBookAppointment(doctor)}
                >
                  Book Appointment
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>

      
      
      

    </div>
  );
};

export default PatientDashboard;
