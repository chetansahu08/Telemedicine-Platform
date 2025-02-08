import React from 'react'
import Navbar from './components/Navbar'
import { Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import Doctors from './pages/Doctors'
import Login from './pages/Login'
import About from './pages/About'
import Contact from './pages/Contact'
import Appointment from './pages/Appointment'
import MyAppointments from './pages/MyAppointments'
import MyProfile from './pages/MyProfile'
import Footer from './components/Footer'
import SignupSelection from './pages/SignupSelection'
import LogSelection from './pages/LogSelection'
import DoctorSignup from './pages/DoctorSignup'
import PatientSignUp from './pages/PatientSignUp'
import DoctorLogin from './pages/DoctorLogin'
import PatientLogin from './pages/PatientLogin'

const App = () => {
  
  return (
    <div className='mx-1 sm:mx-[2%]'>
      <Navbar />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/doctors' element={<Doctors />} />
        <Route path='/doctors/:speciality' element={<Doctors />} />
        <Route path='/signup' element={<SignupSelection />} />
        <Route path='/login' element={<LogSelection />} />
        <Route path='/about' element={<About />} />
        <Route path='/contact' element={<Contact />} />
        <Route path='/appointment/:docId' element={<Appointment />} />
        <Route path='/my-appointments' element={<MyAppointments />} />
        <Route path='/my-profile' element={<MyProfile />} />
        <Route path='/doctorSignup' element={<DoctorSignup/>} />
        <Route path='/patientSignup' element={<PatientSignUp/>} />
        <Route path='/doctorLogin' element={<DoctorLogin/>} />
        <Route path='/patientLogin' element={<PatientLogin/>} />
      </Routes>
      <Footer />
    </div>
  )
}

export default App