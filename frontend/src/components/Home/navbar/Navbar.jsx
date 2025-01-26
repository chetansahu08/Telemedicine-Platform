import React, { useEffect, useState } from 'react'
import "./navbar.css"
import { useNavigate } from 'react-router-dom'


const Navbar = () => {
  const [scrolled, setScrolled] = useState(false);
  const navigate = useNavigate()

  const toggleLogin  = () => {
    navigate("/logselection")
  }

   const toggleSignup  = () => {
    navigate("/signupSelection")
  }

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 50) {
        setScrolled(true);
      } else {
        setScrolled(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);



  return (
     <div className={`navbar ${scrolled ? 'scrolled' : ''}`}>
        <div className="nav-logo">MediConnect</div>
      <div className="nav-links">
        <ul>
            <li><button onClick={toggleLogin}>Login</button></li>
            <li><button onClick={toggleSignup}>SignUp</button></li>
        </ul>
      </div>
    </div>
  )
}

export default Navbar
