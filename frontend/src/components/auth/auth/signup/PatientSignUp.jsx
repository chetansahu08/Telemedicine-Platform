
    import React, { useState } from "react";
    import "./signup.css"
import { FaEye, FaEyeSlash } from "react-icons/fa"; 


    const PatientSignup = () => {
                    const [passwordShown, setPasswordShown] = useState(false);
  const [confirmPasswordShown, setConfirmPasswordShown] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordShown(!passwordShown);
  };

  const toggleConfirmPasswordVisibility = () => {
    setConfirmPasswordShown(!confirmPasswordShown);
  };

  return (
    <div className="signup-container">
      <div className="logo">
        <h1>Signup for Patient</h1>
      </div>
      <div className="signup-form">
        <form action="">
          <div className="input-group">
            <input type="text" placeholder="Username" />
          </div>
          <div className="input-group">
            <input type="email" placeholder="Email" />
          </div>
          <div className="input-group">
            <input
              type={passwordShown ? "text" : "password"}
              placeholder="Password"
            />
            <button
              type="button"
              className="toggle-btn"
              onClick={togglePasswordVisibility}
            >
              {passwordShown ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>
          <div className="input-group">
            <input
              type={confirmPasswordShown ? "text" : "password"}
              placeholder="Confirm Password"
            />
            <button
              type="button"
              className="toggle-btn"
              onClick={toggleConfirmPasswordVisibility}
            >
              {confirmPasswordShown ? <FaEyeSlash /> : <FaEye />}
            </button>
          </div>
          <div className="action">
            <button type="submit" className="signup-btn">
              Signup
            </button>
          </div>
        </form>
      </div>
    </div>
);
};

export default PatientSignup;

// </>
//   );
// };
