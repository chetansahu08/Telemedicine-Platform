import React, { useState } from 'react';
import './signup.css'; // Optional: for styling
import group from '../../../../assets/group-of-doctors.jpg';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const DoctorSignup = () => {
  const navigate = useNavigate(); // Initialize useHistory
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role:'DOCTOR',
    fees: '',
    specialization: '',
    availability: [{ day: '', startTime: '', endTime: '' }],
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [error, setError] = useState('');

  const handleAvailabilityChange = (index, e) => {
    const { name, value } = e.target;
    const newAvailability = [...formData.availability];
    newAvailability[index][name] = value;
    setFormData((prevData) => ({
      ...prevData,
      availability: newAvailability,
    }));
  };

  const addAvailability = () => {
    setFormData((prevData) => ({
      ...prevData,
      availability: [...prevData.availability, { day: '', startTime: '', endTime: '' }],
    }));
  };

  

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    try {
        const { confirmPassword, ...dataToSend } = formData;
        const response = await axios.post('http://localhost:8080/api/auth/register', dataToSend);
        console.log(response.data);
      // Handle successful registration (e.g., redirect or show a success message)
      alert('Registration successful! Please log in.');
      navigate('/doctorLogin')
    } catch (error) {
      console.error('There was an error registering the doctor!', error);
      setError('Registration failed. Please try again.');
    }
  };

  return (
    <div className="registration-container">
      <div className="image-container">
        <img src={group} alt="Group of Doctors" />
      </div>
      <div className="form-container">
        <h2>Doctor Registration</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label>Full Name:</label>
            <input type="text" name="name" value={formData.fullName} onChange={handleChange} required />
          </div>
          <div>
            <label>Email:</label>
            <input type="email" name="email" value={formData.email} onChange={handleChange} required />
          </div>
          <div>
          <label>Password:</label>
            <input
              type={showPassword ? 'text' : 'password'}
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
            <button type="button" onClick={() => setShowPassword(!showPassword)}>
              {showPassword ? 'Hide' : 'Show'}
            </button>
          </div>
          <div>
            <label>Confirm Password:</label>
            <input
              type={showConfirmPassword ? 'text' : 'password'}
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
            />
            <button type="button" onClick={() => setShowConfirmPassword(!showConfirmPassword)}>
              {showConfirmPassword ? 'Hide' : 'Show'}
            </button>
          </div>
          {error && <div className="error-message">{error}</div>}
          <div>
            <label>Role:</label>
            <input type="text" name="role" value="DOCTOR" disabled />
          </div>
          <div>
            <label>Fees:</label>
            <input type="number" name="fees" value={formData.fees} onChange={handleChange} required />
          </div>
          <div>
            <label>Specialization:</label>
            <input type="text" name="specialization" value={formData.specialization} onChange={handleChange} required />
          </div>
          <h3>Availability</h3>
          {formData.availability.map((availability, index) => (
            <div key={index}>
              <label>Day:</label>
              <select name="day" value={availability.day} onChange={(e) => handleAvailabilityChange(index, e)} required>
                <option value="">Select a day</option>
                <option value="Monday">Monday</option>
                <option value="Tuesday">Tuesday</option>
                <option value="Wednesday">Wednesday</option>
                <option value="Thursday">Thursday</option>
                <option value="Friday">Friday</option>
                <option value="Saturday">Saturday</option>
                <option value="Sunday">Sunday</option>
              </select>
              <label>Start Time:</label>
              <input type="time" name="startTime" value={availability.startTime} onChange={(e) => handleAvailabilityChange(index, e)} required />
              <label>End Time:</label>
              <input type="time" name="endTime" value={availability.endTime} onChange={(e) => handleAvailabilityChange(index, e)} required />
            </div>
          ))}
          <button type="button" onClick={addAvailability}>Add Availability</button>
          <button type="submit">Register</button>
        </form>
      </div>
    </div>
  );
};

export default DoctorSignup;