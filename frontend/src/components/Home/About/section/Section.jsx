import React from 'react'
import "./section.css"

const Section = ({Data}) => {
  return (
    <div className='section-container'>
        <div className="image">
            <img src={Data.src} alt="" />
        </div>
        <div className="content">
            <h1>{Data.heading}</h1>
            <p>{Data.Para}</p>
            <button>{Data.button}</button>
        </div>
    </div>
  )
}

export default Section
