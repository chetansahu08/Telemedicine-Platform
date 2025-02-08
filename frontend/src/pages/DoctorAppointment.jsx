import { useEffect, useState } from "react";

const DoctorAppointments = ({ doctorId }) => {
    const [appointments, setAppointments] = useState([]);

    useEffect(() => {
        fetch("/api/doctors/appointments", { credentials: "include" }) // ✅ Fetch appointments
            .then(res => res.json())
            .then(data => setAppointments(data));
    }, []);

    return (
        <div>
            <h2>Your Appointments</h2>
            <ul>
                {appointments.map((appointment) => (
                    <li key={appointment.id}>
                        Patient ID: {appointment.patientId} | Date: {appointment.date} | Time: {appointment.time}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default DoctorAppointments;
