import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, MenuItem, IconButton, Box, Typography } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';

const OrderModal = ({ open, handleClose, order, handleSave }) => {
    const [orderData, setOrderData] = useState(
        order || {
            estado: 'SOLICITADA',
            observaciones: '',
            ordenDeDespacho: '',
            fechaSolicitud: '',
            fechaRecepcion: '',
            items: [],  // Lista de items en la orden de compra
        }
    );

    // Placeholder para tema back
    const [productos, setProductos] = useState([
        { id: 1, nombre: 'Producto A' },
        { id: 2, nombre: 'Producto B' },
        { id: 3, nombre: 'Producto C' },
    ]);


    const [newItem, setNewItem] = useState({ codigoArticulo: '', color: '', talle: '', cantidadSolicitada: 0 });

    const estados = ['SOLICITADA', 'RECHAZADA', 'ACEPTADA', 'RECIBIDA'];

    // Maneja el cambio en cada campo del formulario de la orden de compra
    const handleChange = (e) => {
        setOrderData({ ...orderData, [e.target.name]: e.target.value });
    };

    // Maneja el cambio en el formulario del nuevo item
    const handleItemChange = (e) => {
        setNewItem({ ...newItem, [e.target.name]: e.target.value });
    };

    // Agregar un nuevo item a la lista
    const handleAddItem = () => {
        setOrderData({ ...orderData, items: [...orderData.items, newItem] });
        setNewItem({ codigoArticulo: '', color: '', talle: '', cantidadSolicitada: 0 });
    };

    // Eliminar un item de la lista
    const handleDeleteItem = (index) => {
        const updatedItems = [...orderData.items];
        updatedItems.splice(index, 1);
        setOrderData({ ...orderData, items: updatedItems });
    };

    // Función para enviar los datos al backend
    const sendToBackend = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/orders', orderData, {
                headers: { 'Content-Type': 'application/json' },
            });
            console.log('Orden de compra guardada:', response.data);
            handleSave(response.data); // Guardar los datos en la UI al recibir la respuesta del backend
        } catch (error) {
            console.error('Error al guardar la orden de compra:', error);
        }
    };

    // Guardar los datos de la orden y enviar al backend
    const handleSaveClick = () => {
        sendToBackend();
        handleClose();
    };

    return (
        <Dialog open={open} onClose={handleClose} fullWidth maxWidth="md">
            <DialogTitle>{order ? 'Modificar Orden de Compra' : 'Crear Nueva Orden de Compra'}</DialogTitle>
            <DialogContent>
                {/* Campos a Nivel de Orden de Compra */}
                <TextField
                    margin="dense"
                    label="Estado"
                    name="estado"
                    select
                    value={orderData.estado}
                    onChange={handleChange}
                    fullWidth
                    required
                >
                    {estados.map((estado) => (
                        <MenuItem key={estado} value={estado}>
                            {estado}
                        </MenuItem>
                    ))}
                </TextField>

                <TextField
                    margin="dense"
                    label="Observaciones"
                    name="observaciones"
                    value={orderData.observaciones}
                    onChange={handleChange}
                    multiline
                    fullWidth
                />
                <TextField
                    margin="dense"
                    label="Orden de Despacho"
                    name="ordenDeDespacho"
                    value={orderData.ordenDeDespacho}
                    onChange={handleChange}
                    fullWidth
                />

                {/* Campos Deshabilitados: Fecha de Solicitud y Fecha de Recepción */}
                <TextField
                    margin="dense"
                    label="Fecha de Solicitud"
                    name="fechaSolicitud"
                    type="date"
                    value={orderData.fechaSolicitud}
                    onChange={handleChange}
                    fullWidth
                    InputLabelProps={{
                        shrink: true,
                    }}
                    disabled
                />
                <TextField
                    margin="dense"
                    label="Fecha de Recepción"
                    name="fechaRecepcion"
                    type="date"
                    value={orderData.fechaRecepcion}
                    onChange={handleChange}
                    fullWidth
                    InputLabelProps={{
                        shrink: true,
                    }}
                    disabled
                />

                {/* Lista de Items */}
                <Typography variant="h6" sx={{ marginTop: '20px' }}>
                    Items de la Orden de Compra
                </Typography>
                {orderData.items.map((item, index) => (
                    <Box key={index} display="flex" alignItems="center" sx={{ marginBottom: '10px' }}>
                        <Typography>{`Artículo: ${item.codigoArticulo} | Color: ${item.color} | Talle: ${item.talle} | Cantidad: ${item.cantidadSolicitada}`}</Typography>
                        <IconButton color="error" onClick={() => handleDeleteItem(index)}>
                            <DeleteIcon />
                        </IconButton>
                    </Box>
                ))}

                {/* Formulario para agregar un nuevo Item */}
                <Box display="flex" gap={2} sx={{ marginTop: '10px' }}>
                    {/* Select de Código de Artículo */}
                    <TextField
                        margin="dense"
                        label="Producto"
                        name="codigoArticulo"
                        select
                        value={newItem.codigoArticulo}
                        onChange={(e) => setNewItem({ ...newItem, codigoArticulo: e.target.value })}
                        fullWidth
                    >
                        {/* Muestra los nombres de los productos, pero almacena el ID */}
                        {productos.map((producto) => (
                            <MenuItem key={producto.id} value={producto.id}>
                                {producto.nombre}
                            </MenuItem>
                        ))}
                    </TextField>
                    <TextField
                        margin="dense"
                        label="Color"
                        name="color"
                        value={newItem.color}
                        onChange={handleItemChange}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        label="Talle"
                        name="talle"
                        value={newItem.talle}
                        onChange={handleItemChange}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        label="Cantidad Solicitada"
                        name="cantidadSolicitada"
                        type="number"
                        value={newItem.cantidadSolicitada}
                        onChange={handleItemChange}
                        fullWidth
                    />
                    <Button variant="contained" color="primary" onClick={handleAddItem}>
                        Agregar
                    </Button>
                </Box>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancelar
                </Button>
                <Button onClick={handleSaveClick} color="secondary">
                    Guardar
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default OrderModal;
