import React from 'react';
import './AboutUs.css';

const AboutUs = () => {
    return (
        <div className="about-us-container">
            <header className="about-hero">
                <h1 className="about-title">Revolutionizing Insurance Management</h1>
                <p className="about-subtitle">
                    A secure, hyper-connected ecosystem designed to empower modern insurance professionals. We bridge the gap between complex workflows and effortless experiences.
                </p>
            </header>

            <section className="about-content">
                <div className="about-text-block">
                    <h2>Our Mission</h2>
                    <p>
                        At InsureTrack, we believe that managing policies and analyzing risks shouldn't be constrained by legacy systems. Our platform offers unprecedented flexibility, ensuring that every claim assessed, policy quoted, and client managed is handled with precision and speed.
                    </p>
                </div>

                <h2 className="section-heading text-center mt-12 mb-8">Empowering Every Professional</h2>
                
                <div className="about-cards-grid">
                    <div className="about-card">
                        <div className="about-icon">💼</div>
                        <h3>Client Profiles</h3>
                        <p>A seamless interface dedicated to those who hold the policies. View coverage details instantly and track active submissions in real-time.</p>
                    </div>

                    <div className="about-card">
                        <div className="about-icon">🌟</div>
                        <h3>Advisors & Representatives</h3>
                        <p>Those who build client trust can swiftly generate custom quotes, process renewals, and manage portfolios with powerful dashboard insights.</p>
                    </div>

                    <div className="about-card">
                        <div className="about-icon">🔍</div>
                        <h3>Claims Specialists</h3>
                        <p>Professionals tasked with evaluating incidents can triage reports effectively, attach critical evidence, and streamline settlement logistics.</p>
                    </div>

                    <div className="about-card">
                        <div className="about-icon">🛡️</div>
                        <h3>Risk Analysts</h3>
                        <p>Specialists focused on evaluating potential exposure can leverage comprehensive tools to thoroughly assess cases and make critical decisions.</p>
                    </div>

                    <div className="about-card">
                        <div className="about-icon">📊</div>
                        <h3>Strategic Evaluators</h3>
                        <p>Those who look at the big picture utilize system-wide financial metrics to audit billing structures, ensure compliance, and forecast trends.</p>
                    </div>

                    <div className="about-card">
                        <div className="about-icon">🔧</div>
                        <h3>System Architects</h3>
                        <p>Technical experts control backend rating rules, manage personnel access, and maintain the integrity of our comprehensive platform.</p>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default AboutUs;
