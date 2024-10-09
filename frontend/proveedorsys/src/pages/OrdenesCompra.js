import React, { useState, useEffect } from 'react';
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button } from '@mui/material';
import axios from 'axios'; 

const OrdenesCompra = () => {
  const [orders, setOrders] = useState([]); 

  useEffect(() => {
    axios.get('http://localhost:8081/api/ordenDeCompra/list')
      .then((response) => {
        setOrders(response.data); 
      })
      .catch((error) => {
        console.error("Error al obtener las órdenes de compra:", error);
      });
  }, []);

  return (
    <div>
      <h1>Órdenes de Compra</h1>
      {/* Tabla de órdenes de compra */}
      <TableContainer component={Paper} sx={{ marginTop: '20px' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Código</TableCell>
              <TableCell>ID de tienda</TableCell>
              <TableCell>Estado</TableCell>
              <TableCell>Fecha</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((order) => (
              <TableRow key={order.id}>
                <TableCell>{order.id}</TableCell>
                <TableCell>{order.codigoArticulo}</TableCell>
                <TableCell>{order.tienda.codigo}</TableCell>
                <TableCell>{order.estado}</TableCell>
                <TableCell>{order.fechaSolicitud}</TableCell>
                <TableCell>
                  {/* Solo dejar opción de eliminar */}
                  <Button
                    variant="outlined"
                    color="error"
                    onClick={() => setOrders(orders.filter((o) => o.id !== order.id))}
                  >
                    Eliminar
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default OrdenesCompra;
