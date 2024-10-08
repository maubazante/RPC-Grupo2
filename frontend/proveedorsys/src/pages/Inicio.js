import React from 'react';
import { Typography, Button, Box, Grid, Link } from '@mui/material';
import GitHubIcon from '@mui/icons-material/GitHub';

const Inicio = () => {
  return (
    <Box sx={{ padding: '20px' }}>
      {/* Título */}
      <Typography variant="h4" gutterBottom>
        ProveedorSys - Trabajo Práctico
      </Typography>

      {/* Explicación del TP */}
      <Typography variant="body1" paragraph>
        Este es un sistema de gestión de proveedores desarrollado como parte del trabajo práctico de sistemas distribuidos. El objetivo del sistema es permitir la administración de órdenes de compra y la integración de stock con diferentes tiendas mediante la utilización de mensajería con Apache Kafka.
      </Typography>

      {/* Link al Repositorio de GitHub */}
      <Box sx={{ marginY: 2 }}>
        <Button
          variant="contained"
          color="primary"
          startIcon={<GitHubIcon />}
          href="https://github.com/maubazante/RPC-Grupo2"
          target="_blank"
          sx={{ marginBottom: '10px' }}
        >
          Ver Repositorio en GitHub
        </Button>
      </Box>

      {/* Links a Perfiles de GitHub */}
      <Typography variant="h6">Contribuidores:</Typography>
      <Grid container spacing={2} sx={{ marginTop: 1 }}>
        <Grid item>
          <Button
            variant="outlined"
            color="secondary"
            startIcon={<GitHubIcon />}
            href="https://github.com/maubazante"
            target="_blank"
          >
            Mauro Bazante
          </Button>
        </Grid>
        <Grid item>
          <Button
            variant="outlined"
            color="secondary"
            startIcon={<GitHubIcon />}
            href="https://github.com/Suhiang98"
            target="_blank"
          >
            Andrés Cupo
          </Button>
        </Grid>
        <Grid item>
          <Button
            variant="outlined"
            color="secondary"
            startIcon={<GitHubIcon />}
            href="https://github.com/valentinolivero"
            target="_blank"
          >
            Valentín Olivero
          </Button>
        </Grid>
        <Grid item>
          <Button
            variant="outlined"
            color="secondary"
            startIcon={<GitHubIcon />}
            href="https://github.com/ImNotThrasher"
            target="_blank"
          >
            Sergio Robles
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Inicio;
