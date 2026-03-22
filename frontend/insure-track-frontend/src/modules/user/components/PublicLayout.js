import React, { useEffect } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import PublicNavbar from './PublicNavbar';

const PublicLayout = () => {
    const location = useLocation();

    useEffect(() => {
        if (location.state && location.state.scrollToContact) {
            setTimeout(() => {
                const footer = document.getElementById('contact');
                if (footer) footer.scrollIntoView({ behavior: 'smooth' });
            }, 100);
        } else {
            window.scrollTo(0, 0);
        }
    }, [location]);

    return (
        <div className="public-layout">
            <PublicNavbar />
            <main className="public-content">
                <Outlet />
            </main>
        </div>
    );
};

export default PublicLayout;
