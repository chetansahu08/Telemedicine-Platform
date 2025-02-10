import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

const BookAppointment = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const doctor = location.state?.doctor;

  const [selectedDate, setSelectedDate] = useState("");
  const [selectedTime, setSelectedTime] = useState("");
  const [error, setError] = useState("");

  if (!doctor) {
    return <p className="text-center p-4">Error: No doctor selected.</p>;
  }

  const handlePayment = async () => {
    if (!selectedDate || !selectedTime) {
      setError("Please select a date and time.");
      return;
    }

    try {
      const patient = JSON.parse(localStorage.getItem("user")); // Get logged-in patient details

      // ✅ 1. Create Razorpay order on backend
      const response = await axios.post("http://localhost:8080/api/payments/create", {
        amount: doctor.fees * 100, // Convert to paise
        currency: "INR",
        patientId: patient.id,
        doctorId: doctor.id,
        date: selectedDate,
        time: selectedTime,
      });

      const { orderId } = response.data;

      // ✅ 2. Open Razorpay payment window
      const options = {
        key: "rzp_test_UCnm9ZETwzn9YW", // Replace with Razorpay Key
        amount: doctor.fees * 100,
        currency: "INR",
        name: "Telemedicine Platform",
        description: `Appointment with ${doctor.name}`,
        order_id: orderId,
        handler: async (paymentResponse) => {
          try {
            // ✅ 3. Verify Payment on Backend
            await axios.post("http://localhost:8080/api/payments/verify", {
              orderId,
              paymentId: paymentResponse.razorpay_payment_id,
              razorpaySignature: paymentResponse.razorpay_signature,
            });

            // ✅ 4. Now, create an appointment after payment success
            await axios.post("http://localhost:8080/api/appointments/book", {
              patientId: patient.id,
              doctorId: doctor.id,
              date: selectedDate,
              time: selectedTime,
              isPaid: true, // Payment flag
            });

            // ✅ 5. Show success message and redirect
            alert("Appointment booked successfully!");
            navigate("/patientdashboard");
          } catch (error) {
            console.error("Payment verification or booking failed:", error);
            setError("Payment verification failed. Try again.");
          }
        },
        prefill: {
          name: patient.name,
          email: patient.email,
          contact: "9876543210",
        },
        theme: { color: "#528FF0" },
      };

      const paymentObject = new window.Razorpay(options);
      paymentObject.open();
    } catch (error) {
      console.error("Payment failed:", error);
      setError("Failed to process payment. Try again.");
    }
  };

  return (
    <div className="bg-gray-100 p-6 min-h-screen flex justify-center">
      <div className="bg-white shadow-lg rounded-xl p-6 w-full max-w-md">
        <h1 className="text-2xl font-bold text-center">Book Appointment</h1>
        <p className="text-center text-gray-600 mt-2">{doctor.name} ({doctor.specialization})</p>
        <p className="text-center text-gray-700 mt-1">Fee: ₹{doctor.fees}</p>

        {/* Date Selection */}
        <div className="mt-4">
          <label className="block font-medium">Select Date:</label>
          <input
            type="date"
            className="w-full p-2 border rounded-md"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
          />
        </div>

        {/* Time Slot Selection */}
        <div className="mt-4">
          <label className="block font-medium">Select Time Slot:</label>
          <select
            className="w-full p-2 border rounded-md"
            value={selectedTime}
            onChange={(e) => setSelectedTime(e.target.value)}
          >
            <option value="">-- Select Time --</option>
            {doctor.availability.map((slot, index) => (
              <option key={index} value={`${slot.day} (${slot.startTime} - ${slot.endTime})`}>
                {slot.day} ({slot.startTime} - {slot.endTime})
              </option>
            ))}
          </select>
        </div>

        {error && <p className="text-red-500 text-sm mt-2">{error}</p>}

        {/* Proceed to Payment */}
        <button
          onClick={handlePayment}
          className="w-full mt-4 bg-blue-500 text-white py-2 rounded-md"
        >
          Proceed to Payment
        </button>
      </div>
    </div>
  );
};

export default BookAppointment;
