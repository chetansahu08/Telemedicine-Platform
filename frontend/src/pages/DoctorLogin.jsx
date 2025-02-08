import React from "react";
import { useForm } from "react-hook-form";

function DoctorLogin() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    console.log("Doctor Login Data:", data);
    // Add your login logic here (e.g., API call)
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-50">
      <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold text-center text-gray-700 mb-6">Doctor Login</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
          <div>
            <label className="block text-gray-600 mb-2">Email:</label>
            <input
              type="email"
              {...register("email", { required: true })}
              className={`w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                errors.email ? "border-red-500" : "border-gray-300"
              }`}
              placeholder="Enter your email"
            />
            {errors.email && <span className="text-red-500 text-sm">*Email is mandatory</span>}
          </div>

          <div>
            <label className="block text-gray-600 mb-2">Password:</label>
            <input
              type="password"
              {...register("password", { required: true })}
              className={`w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 ${
                errors.password ? "border-red-500" : "border-gray-300"
              }`}
              placeholder="Enter your password"
            />
            {errors.password && <span className="text-red-500 text-sm">*Password is mandatory</span>}
          </div>

          <div>
            <label className="block text-gray-600 mb-2">Role:</label>
            <input
              type="text"
              value="Doctor"
              disabled
              className="w-full p-3 border rounded-lg bg-gray-100 cursor-not-allowed"
            />
          </div>

          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-3 rounded-lg hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-400"
          >
            Login
          </button>
        </form>
      </div>
    </div>
  );
}

export default DoctorLogin;
