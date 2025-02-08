import { useEffect, useState } from "react";

const Dashboard = () => {
    const [patient, setPatient] = useState(null);

    useEffect(() => {
        fetch("/api/auth/session") // ✅ Check session status
            .then(res => res.json())
            .then(data => {
                if (data.loggedIn) {
                    setPatient(data.patientId);
                } else {
                    window.location.href = "/login"; // Redirect if not logged in
                }
            })
            .catch(() => window.location.href = "/login");
    }, []);

    return patient ? <PatientDashboard patient={patient} /> : <div>Loading...</div>;
};

const bookAppointment = async (doctorId, date, time) => {
    const response = await fetch("/api/appointments/book", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ doctorId, date, time }),
        credentials: "include" // ✅ Include session cookie
    });

    const data = await response.json();
    if (response.ok) {
        alert("Payment Required. Redirecting...");
        window.open(data.paymentUrl); // Redirect to payment
    } else {
        alert("Error: " + data.message);
    }
};


export default Dashboard;
