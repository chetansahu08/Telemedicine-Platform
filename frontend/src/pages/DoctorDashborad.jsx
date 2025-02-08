import { useEffect, useState } from "react";

const DoctorDashboard = () => {
    const [doctor, setDoctor] = useState(null);

    useEffect(() => {
        fetch("/api/auth/session") // ✅ Check session status
            .then(res => res.json())
            .then(data => {
                if (data.loggedIn && data.role === "DOCTOR") {
                    setDoctor(data.userId);
                } else {
                    window.location.href = "/login"; // Redirect if not logged in
                }
            })
            .catch(() => window.location.href = "/login");
    }, []);

    return doctor ? <DoctorAppointments doctorId={doctor} /> : <div>Loading...</div>;
};

export default DoctorDashboard;
