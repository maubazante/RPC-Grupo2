import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Typography, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField, IconButton, Checkbox, FormControlLabel } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

const Productos = () => {
  // Estados
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = useState(false);  // Control del modal
  const [currentProduct, setCurrentProduct] = useState({ nombre: '', codigo: '', talle: '', foto: '', color: '', habilitado: true }); // Producto en edición o nuevo

  // Cargar productos al montar el componente
  useEffect(() => {
    axios.get('http://localhost:8081/api/productos')
      .then(response => {
        setProductos(response.data);
        setLoading(false);
      })
      .catch(err => {
        setError('Error al cargar los productos');
        setLoading(false);
      });
  }, []);

  // Abrir el modal de formulario para editar o crear un producto
  const handleOpen = (product) => {
    setCurrentProduct(product ? product : { nombre: '', codigo: '', talle: '', foto: '', color: '', habilitado: true });
    setOpen(true);
  };

  // Cerrar el modal
  const handleClose = () => {
    setOpen(false);
  };

  // Manejar cambios en el formulario
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setCurrentProduct({
      ...currentProduct,
      [name]: type === 'checkbox' ? checked : value // Detectar si es checkbox o text
    });
  };

  const handleSave = () => {
    console.log("Datos del formulario:", currentProduct);

    if (currentProduct.id) {
      axios.put(`http://localhost:8081/api/productos/${currentProduct.id}`, currentProduct)
        .then(response => {
          setProductos(productos.map(p => (p.id === response.data.id ? response.data : p)));
          handleClose();
        })
        .catch(() => setError('Error al actualizar el producto'));
    } else {
      axios.post('http://localhost:8081/api/productos', currentProduct)
        .then(response => {
          setProductos([...productos, response.data]);
          handleClose();
        })
        .catch(() => setError('Error al crear el producto'));
    }
  };

  // Eliminar un producto
  const handleDelete = (id) => {
    axios.delete(`http://localhost:8081/api/productos/${id}`)
      .then(() => {
        setProductos(productos.filter(p => p.id !== id));
      })
      .catch(() => setError('Error al eliminar el producto'));
  };

  // Mostrar mensaje de carga o error
  if (loading) return <Typography variant="h6">Cargando productos...</Typography>;
  if (error) return <Typography variant="h6" color="error">{error}</Typography>;

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Productos Disponibles
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpen(null)} sx={{ mb: 2 }}>
        Agregar Producto
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Nombre</TableCell>
              <TableCell>Código</TableCell>
              <TableCell>Talle</TableCell>
              <TableCell>Color</TableCell>
              <TableCell>Habilitado</TableCell>
              <TableCell>Foto</TableCell>
              <TableCell>Acciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {productos.map((producto) => (
              <TableRow key={producto.id}>
                <TableCell>{producto.id}</TableCell>
                <TableCell>{producto.nombre}</TableCell>
                <TableCell>{producto.codigo}</TableCell>
                <TableCell>{producto.talle}</TableCell>
                <TableCell>{producto.color}</TableCell>
                <TableCell>{producto.habilitado ? 'Sí' : 'No'}</TableCell>
                <TableCell>
                  <img src={producto.foto} alt={producto.nombre} style={{ width: '50px', height: '50px' }} />
                </TableCell>
                <TableCell>
                  {/* Botones de editar y eliminar */}
                  <IconButton color="primary" onClick={() => handleOpen(producto)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(producto.id)}>
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Modal de formulario */}
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{currentProduct.id ? 'Editar Producto' : 'Agregar Producto'}</DialogTitle>
        <DialogContent>
          <TextField margin="dense" name="nombre" label="Nombre" fullWidth value={currentProduct.nombre} onChange={handleChange} />
          <TextField margin="dense" name="codigo" label="Código" fullWidth value={currentProduct.codigo} onChange={handleChange} />
          <TextField margin="dense" type="number" name="cantidad" label="Cantidad" fullWidth value={currentProduct.cantidad} onChange={handleChange} />
          <TextField margin="dense" name="talle" label="Talle" fullWidth value={currentProduct.talle} onChange={handleChange} />
          <TextField margin="dense" name="foto" label="Foto URL" fullWidth value={currentProduct.foto} onChange={handleChange} />
          <TextField margin="dense" name="color" label="Color" fullWidth value={currentProduct.color} onChange={handleChange} />
          <FormControlLabel
            control={<Checkbox name="habilitado" checked={currentProduct.habilitado} onChange={handleChange} />}
            label="Habilitado"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleSave} color="primary">
            Guardar
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default Productos;
