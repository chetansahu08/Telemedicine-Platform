import React,{useState} from 'react'
import { FaEye, FaEyeSlash } from "react-icons/fa";
import "./login.css"

const DoctorLogin = () => {
 const [passwordShown, setPasswordShown] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordShown(!passwordShown);
  };

  return (
    <div className="login-container">
      <div className="logo">
        <h1>Login of Doctors</h1>
      </div>
      <div className="login-form">
        <form action="">
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
          <div className="action">
            <button type="submit" className="login-btn">
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default DoctorLogin
