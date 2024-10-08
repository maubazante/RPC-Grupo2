import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, CssBaseline } from '@mui/material';
import theme from './theme'; 
import Navbar from './components/Navbar';
import OrdenesCompra from './pages/OrdenesCompra';
import Inicio from './pages/Inicio';
import Productos from './pages/Productos';


const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline /> 
      <Router>
        <Navbar />
        <div style={{ padding: '20px', backgroundColor: theme.palette.background.default, color: theme.palette.text.primary }}>
          <Routes>
            <Route path="/" element={<Navigate to="/inicio" />} />
            <Route path="/inicio" element={<Inicio />} />
            <Route path='/productos' element={<Productos />}/>
            <Route path="/ordenes-compra" element={<OrdenesCompra />} />
          </Routes>
        </div>
      </Router>
    </ThemeProvider>
  );
};

export default App;
