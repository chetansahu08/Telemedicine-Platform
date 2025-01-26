import React from 'react';
import VideoBg from "../../../assets/VideoBg.mp4";
import "./hero.css";

const Hero = () => {
  return (
    <div className="hero-container">
      <div className="video-background">
        <video src={VideoBg} muted loop autoPlay>
          Your browser does not support the video tag.
        </video>
      </div>
      <div className="heading">
        <h1>You are in good Hands</h1>
        <p>
          When you lead a busy, demanding life, it’s easy to forget to take care of yourself. We’re here to help you regain your balance, one day at a time. Take a look around our website and find out if we’re right for you.
        </p>
        <button>Read More</button>
      </div>
    </div>
  );
};

export default Hero;
