import React from 'react'
import { Data } from './data'
import Section from './section/Section'
import "./about.css"

const About = () => {
  return (
    <div className='about'>
      {Data.map((data)=> {
        return <Section Data = {data}/>
      })}
    </div>
  )
}

export default About
