import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './PublicNavbar.css';

const PublicNavbar = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [scrolled, setScrolled] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            setScrolled(window.scrollY > 50);
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    // Contact is now handled via direct navigation

    return (
        <nav className={`public-navbar ${scrolled ? 'scrolled' : ''}`}>
            <div className="navbar-logo" onClick={() => navigate('/')}>
                <img src="/logo.png" alt="InsureTrack Logo" />
            </div>
            
            <div className="navbar-links">
                <button className={`nav-link ${location.pathname === '/' ? 'active' : ''}`} onClick={() => navigate('/')}>Home</button>
                <button className={`nav-link ${location.pathname === '/about' ? 'active' : ''}`} onClick={() => navigate('/about')}>About Us</button>
                <button className={`nav-link ${location.pathname === '/contact' ? 'active' : ''}`} onClick={() => navigate('/contact')}>Contact Us</button>
            </div>

            <div className="navbar-actions">
                <button className="btn-nav btn-login" onClick={() => navigate('/login')}>Sign In</button>
                <button className="btn-nav btn-register" onClick={() => navigate('/register')}>Register</button>
            </div>
        </nav>
    );
};

export default PublicNavbar;
