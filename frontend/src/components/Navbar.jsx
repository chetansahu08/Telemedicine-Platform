import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { assets } from "../assets/assets";

const Navbar = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [showDropdown, setShowDropdown] = useState(false);

  // Load user details when component mounts
  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    setUser(storedUser);
  }, []);

  // Listen for login/logout changes in localStorage
  useEffect(() => {
    const handleStorageChange = () => {
      const updatedUser = JSON.parse(localStorage.getItem("user"));
      setUser(updatedUser);
    };

    window.addEventListener("storage", handleStorageChange);
    return () => {
      window.removeEventListener("storage", handleStorageChange);
    };
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("user");
    setUser(null);
    navigate("/");
    window.location.reload(); // Force Navbar to update after logout
  };

  return (
    <div className="flex items-center justify-between text-sm py-4 mb-5 border-b border-gray-400">
      <h1 onClick={() => navigate("/")} className="text-2xl font-bold text-primary cursor-pointer hover:text-blue-600">
        Telemedicine
      </h1>

      {/* Desktop Navigation Links */}
      <ul className="md:flex items-start gap-5 font-medium hidden">
        <NavLink to="/"><li className="py-1">HOME</li></NavLink>
        <NavLink to="/doctors"><li className="py-1">ALL DOCTORS</li></NavLink>
        <NavLink to="/about"><li className="py-1">ABOUT</li></NavLink>
        <NavLink to="/contact"><li className="py-1">CONTACT</li></NavLink>
      </ul>

      <div className="flex items-center gap-4">
        {user ? (
          <div className="relative">
            <img
              className="w-8 h-8 rounded-full cursor-pointer"
              src={assets.profile_pic}
              alt="Profile"
              onClick={() => setShowDropdown(!showDropdown)}
            />

            {showDropdown && (
              <div className="absolute right-0 mt-2 w-48 bg-white border rounded-lg shadow-lg p-3 z-50">
                <p onClick={() => navigate("/myprofile")} className="cursor-pointer hover:text-blue-600">
                  My Profile
                </p>

                {user.role === "PATIENT" && (
                  <>
                  <p onClick={() => navigate("/patientdashboard")} className="cursor-pointer hover:text-blue-600">
                    My Dashboard
                  </p>
                  <p onClick={() => navigate("/patientappointments")} className="cursor-pointer hover:text-blue-600">
                      My Appointments
                    </p>
                  </>
                  
                  
                )}

                {user.role === "DOCTOR" && (
                  <>
                    <p onClick={() => navigate("/doctordashboard")} className="cursor-pointer hover:text-blue-600">
                      My Dashboard
                    </p>
                    <p onClick={() => navigate("/doctorappointments")} className="cursor-pointer hover:text-blue-600">
                      Appointments
                    </p>
                  </>
                )}

                <hr className="my-2" />
                <p onClick={handleLogout} className="cursor-pointer text-red-500 hover:text-red-700">
                  Logout
                </p>
              </div>
            )}
          </div>
        ) : (
          <button
            onClick={() => navigate("/signup")}
            className="bg-primary text-white px-3 py-1 rounded-full font-light hidden md:block"
          >
            Login/Signup
          </button>
        )}
      </div>
    </div>
  );
};

export default Navbar;
