
import './App.css'
import {Routes, Route} from 'react-router-dom'
import LandingPage from './components/Home/LandingPage/LandingPage'
import LogSelection from './components/LogSelection/LogSelection';
import SignupSelection from './components/SignupSelection/SignupSelection';
import PatientSignup from './components/auth/auth/signup/PatientSignUp';
import DoctorSignup from './components/auth/auth/signup/DoctoreSignUp';
import PatientLogin from './components/auth/auth/login/PatientLogin';
import DoctorLogin from './components/auth/auth/login/DoctorLogin';



function App() {


  return (
    <>
    <Routes>
      <Route path='/' element = {<LandingPage/>}/>
      <Route path='/logselection' element = {<LogSelection/>}/>
      <Route path='/signupSelection' element = {<SignupSelection/>}/>
      <Route path='/patientSignup' element={<PatientSignup/>}/>
      <Route path='/doctorSignup' element={<DoctorSignup/>}/>
      <Route path='/patientLogin' element = {<PatientLogin/>}/>
      <Route path='/doctorLogin' element = {<DoctorLogin/>}/>
    </Routes>
    </>
  )
}

export default App
