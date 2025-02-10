import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const MyProfile = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [updatedUser, setUpdatedUser] = useState({});

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    if (!storedUser) {
      alert("Unauthorized access!");
      navigate("/login");
      return;
    }
    setUser(storedUser);
    
    axios.get(`http://localhost:8080/api/profile/${storedUser.id}`)
      .then((response) => {
        setUser(response.data);
        setUpdatedUser(response.data);
      })
      .catch((error) => console.error("Error fetching profile:", error));
  }, [navigate]);

  const handleChange = (e) => {
    setUpdatedUser({ ...updatedUser, [e.target.name]: e.target.value });
  };

  const handleUpdate = () => {
    axios.put(`http://localhost:8080/api/profile/${user.id}`, updatedUser)
      .then((response) => {
        setUser(response.data);
        localStorage.setItem("user", JSON.stringify(response.data));
        alert("Profile updated successfully!");
      })
      .catch((error) => console.error("Error updating profile:", error));
  };

  const handleDelete = () => {
    if (window.confirm("Are you sure you want to delete your account?")) {
      axios.delete(`http://localhost:8080/api/profile/${user.id}`)
        .then(() => {
          localStorage.removeItem("user");
          navigate("/signup");
        })
        .catch((error) => console.error("Error deleting account:", error));
    }
  };

  if (!user) return <div>Loading...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">My Profile</h1>

      <div className="mt-4 p-4 border rounded-md shadow">
        <label className="block text-sm font-medium">Name:</label>
        <input type="text" name="name" value={updatedUser.name} onChange={handleChange} className="w-full p-2 border rounded" />

        <label className="block text-sm font-medium mt-3">Email:</label>
        <input type="email" name="email" value={updatedUser.email} onChange={handleChange} className="w-full p-2 border rounded" />

        {user.role === "DOCTOR" && (
          <>
            <label className="block text-sm font-medium mt-3">Specialization:</label>
            <input type="text" name="specialization" value={updatedUser.specialization || ""} onChange={handleChange} className="w-full p-2 border rounded" />

            <label className="block text-sm font-medium mt-3">Consultation Fees:</label>
            <input type="number" name="fees" value={updatedUser.fees || ""} onChange={handleChange} className="w-full p-2 border rounded" />
          </>
        )}

        {user.role === "PATIENT" && (
          <>
            <label className="block text-sm font-medium mt-3">Medical History:</label>
            <textarea name="medicalHistory" value={updatedUser.medicalHistory || ""} onChange={handleChange} className="w-full p-2 border rounded"></textarea>

            <label className="block text-sm font-medium mt-3">Age:</label>
            <input type="number" name="age" value={updatedUser.age || ""} onChange={handleChange} className="w-full p-2 border rounded" />
          </>
        )}

        <button onClick={handleUpdate} className="mt-4 bg-blue-500 text-white px-4 py-2 rounded">
          Update Profile
        </button>

        <button onClick={handleDelete} className="mt-4 bg-red-500 text-white px-4 py-2 rounded ml-2">
          Delete Account
        </button>
      </div>
    </div>
  );
};

export default MyProfile;
