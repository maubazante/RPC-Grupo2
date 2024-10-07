import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, CssBaseline } from '@mui/material';
import theme from './theme'; // Importar el tema actualizado
import Navbar from './components/Navbar';
import OrdenesCompra from './pages/OrdenesCompra';
import Tiendas from './pages/Tiendas';
import Novedades from './pages/Novedades';
import Inicio from './pages/Inicio';


const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline /> 
      <Router>
        <Navbar />
        <div style={{ padding: '20px', backgroundColor: theme.palette.background.default, color: theme.palette.text.primary }}>
          <Routes>
            <Route path="/" element={<Inicio />} />
            <Route path="/tiendas" element={<Tiendas />} />
            <Route path="/novedades" element={<Novedades />} />
            <Route path="/ordenes-compra" element={<OrdenesCompra />} />
          </Routes>
        </div>
      </Router>
    </ThemeProvider>
  );
};

export default App;
