import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
// import Login from './modules/user/components/Login';
// import Register from './modules/user/components/Register';
import AppRoutes from './core/services/routes'
 
function App() {
  return (
    <div>
      <AppRoutes></AppRoutes>
    </div>
  );
}
 
export default App;