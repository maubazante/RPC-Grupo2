import React, { useState, useEffect } from 'react';
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button } from '@mui/material';
import OrderModal from './OrderModal';

const OrdenesCompra = () => {
  // Placeholder para las órdenes de compra
  const [orders, setOrders] = useState([
    { id: 1, code: 'OC-001', estado: 'SOLICITADA', fecha: '2024-09-01' },
    { id: 2, code: 'OC-002', estado: 'RECIBIDA', fecha: '2024-09-12' },
  ]);
  const [selectedOrder, setSelectedOrder] = useState(null); 
  const [isModalOpen, setModalOpen] = useState(false);

  useEffect(() => {

  }, []);

  const handleSaveOrder = (newOrder) => {
    if (selectedOrder) {
      setOrders((prevOrders) =>
        prevOrders.map((order) => (order.id === selectedOrder.id ? newOrder : order))
      );
    } else {
      setOrders([...orders, { ...newOrder, id: orders.length + 1 }]);
    }
  };

  const handleOpenModal = (order = null) => {
    setSelectedOrder(order);
    setModalOpen(true);
  };

  const handleCloseModal = () => {
    setModalOpen(false);
    setSelectedOrder(null);
  };

  return (
    <div>
      <h1>Órdenes de Compra</h1>
      {/* Botón para abrir el modal de creación */}
      <Button variant="contained" color="primary" onClick={() => handleOpenModal()}>
        Crear Nueva Orden
      </Button>

      {/* Tabla de órdenes de compra */}
      <TableContainer component={Paper} sx={{ marginTop: '20px' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Código</TableCell>
              <TableCell>Estado</TableCell>
              <TableCell>Fecha</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.map((order) => (
              <TableRow key={order.id}>
                <TableCell>{order.id}</TableCell>
                <TableCell>{order.code}</TableCell>
                <TableCell>{order.estado}</TableCell>
                <TableCell>{order.fecha}</TableCell>
                <TableCell>
                  <Button
                    variant="outlined"
                    color="secondary"
                    style={{ marginRight: '10px' }}
                    onClick={() => handleOpenModal(order)}
                  >
                    Modificar
                  </Button>
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

      {/* Modal de alta/modificación */}
      <OrderModal
        open={isModalOpen}
        handleClose={handleCloseModal}
        order={selectedOrder}
        handleSave={handleSaveOrder}
      />
    </div>
  );
};

export default OrdenesCompra;
