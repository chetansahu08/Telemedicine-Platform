import React from "react";
import { useForm } from "react-hook-form";
import "./login.css"; // Ensure this path is correct

function PatientLogin() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log("Patient Login Data:", data);
    // Add your login logic here (e.g., API call)
  };

  return (
    <>
      <h2>Patient Login</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            {...register("email", { required: true })}
          />
          {errors.email && <span>*Email is mandatory</span>}
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            {...register("password", { required: true })}
          />
          {errors.password && <span>*Password is mandatory</span>}
        </div>
        <div>
          <label>Role:</label>
          <input type="text" value="Patient" disabled />
        </div>
        <button type="submit">Login</button>
      </form>
    </>
  );
}

export default PatientLogin;