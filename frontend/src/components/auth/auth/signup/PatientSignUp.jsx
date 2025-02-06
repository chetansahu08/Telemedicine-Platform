import React, { useState } from 'react';
import './signup.css'; // Optional: for styling
import group from '../../../../assets/patients-signup-image.jpg'
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const DoctorSignup = () => {
  const navigate = useNavigate(); // Initialize useNavigate
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    role:'PATIENT',
    age:'',
    medicalHistory:'',
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
      navigate('/patientLogin');

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
        <h2>Patient Registration</h2>
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
            <input type="text" name="role" value="PATIENT" disabled />
          </div>
          <div>
            <label>Age:</label>
            <input type="number" name="age" value={formData.age} onChange={handleChange} required />
          </div>
          <div>
            <label>medicalHistory:</label>
            <input type="text" name="medicalHistory" value={formData.medicalHistory} onChange={handleChange} required />
          </div>
         
          <button type="submit">Register</button>
        </form>
      </div>
    </div>
  );
};

export default DoctorSignup;