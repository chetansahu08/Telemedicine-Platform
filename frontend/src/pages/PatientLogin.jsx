import React from "react";
import { useForm } from "react-hook-form";

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
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md p-8 bg-white shadow-md rounded-lg">
        <h2 className="text-2xl font-bold text-center text-gray-700 mb-6">Patient Login</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label className="block text-gray-600 mb-1">Email:</label>
            <input
              type="email"
              {...register("email", { required: true })}
              className={`w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.email ? 'border-red-500' : 'border-gray-300'}`}
            />
            {errors.email && <span className="text-red-500 text-sm">*Email is mandatory</span>}
          </div>
          <div>
            <label className="block text-gray-600 mb-1">Password:</label>
            <input
              type="password"
              {...register("password", { required: true })}
              className={`w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${errors.password ? 'border-red-500' : 'border-gray-300'}`}
            />
            {errors.password && <span className="text-red-500 text-sm">*Password is mandatory</span>}
          </div>
          <div>
            <label className="block text-gray-600 mb-1">Role:</label>
            <input
              type="text"
              value="Patient"
              disabled
              className="w-full p-3 border bg-gray-100 rounded-lg cursor-not-allowed"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
}

export default PatientLogin;