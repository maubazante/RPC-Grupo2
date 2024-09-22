const express = require('express');
const clienteTienda = require('./tienda');
const clienteUsuario = require('./usuario')
const clienteProducto = require('./producto')

const app = express();
app.use(express.json()); // Middleware para parsear JSON


//                                                                                        ENDPOINTS PARA TIENDA

app.post('/createTienda', (req, res) => {
  const tiendaData = req.body;

  const request = {
    tienda: {
      codigo: tiendaData.codigo,
      direccion: tiendaData.direccion,
      ciudad: tiendaData.ciudad,
      provincia: tiendaData.provincia,
      habilitada: tiendaData.habilitada,
      usuarioId: tiendaData.usuarioId
    }
  };

  clienteTienda.createTienda(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});


app.delete('/deleteTienda', (req, res) => {
  const { codigo } = req.body;

  const request = { codigo };

  clienteTienda.DeleteTienda(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

// Endpoint para modificar una tienda
app.put('/modifyTienda', (req, res) => {
  const tiendaData = req.body;

  const request = {
    tienda: {
      codigo: tiendaData.codigo,
      direccion: tiendaData.direccion,
      ciudad: tiendaData.ciudad,
      provincia: tiendaData.provincia,
      habilitada: tiendaData.habilitada,
      usuarioId: tiendaData.usuarioId
    }
  };
  clienteTienda.modifyTienda(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  })
})


//                                                                                    ENDPOINTS PARA USUARIO
app.post('/createUsuario', (req, res) => {
  const usuarioData = req.body;

  const request = {
    usuario: {
      nombre: usuarioData.nombre,
      apellido: usuarioData.apellido,
      username: usuarioData.username,
      password: usuarioData.password,
      rol: usuarioData.rol,
      tiendaId: usuarioData.tiendaId,
      habilitado: usuarioData.habilitado,
      id: usuarioData.id
    }
  };

  clienteUsuario.createUsuario(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});


app.delete('/deleteUsuario', (req, res) => {
  const { usuarioId } = req.body;

  const request = { usuarioId };

  clienteUsuario.DeleteUsuario(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});


app.put('/modifyUsuario', (req, res) => {
  const usuarioData = req.body;

  const request = {
    usuario: {
      nombre: usuarioData.nombre,
      apellido: usuarioData.apellido,
      username: usuarioData.username,
      password: usuarioData.password,
      rol: usuarioData.rol,
      tiendaId: usuarioData.tiendaId,
      habilitado: usuarioData.habilitado,
      id: usuarioData.id
    }
  };
  clienteUsuario.modifyUsuario(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  })
})

// Endpoint para buscar usuarios
app.post('/buscarUsuarios', (req, res) => {
  const { username, tiendaId } = req.body;

  const request = {
    username: username || '',
    tiendaId: tiendaId || 0
  };

  clienteUsuario.BuscarUsuarios(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});



//                                                                                          ENDPOINTS PARA PRODUCTO

app.post('/createProducto', (req, res) => {
  const productoData = req.body;

  const request = {
    producto: {
      nombre: productoData.nombre,
      codigo: productoData.apellido,
      color: productoData.color,
      talle: productoData.talle,
      habilitado: productoData.habilitado,
      tiendaIds: productoData.tiendaIds,
      id: productoData.id,
      foto: productoData.foto
    }
  };

  clienteProducto.createProducto(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});


app.delete('/deleteProducto', (req, res) => {
  const { productoId } = req.body;

  const request = { productoId };

  clienteProducto.DeleteProducto(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});


app.put('/modifyProducto', (req, res) => {
  const productoData = req.body;

  const request = {
    producto: {
      nombre: productoData.nombre,
      codigo: productoData.apellido,
      color: productoData.color,
      talle: productoData.talla,
      habilitado: productoData.habilitado,
      tiendaIds: productoData.tiendaIds,
      id: productoData.id,
      foto: productoData.foto
    }
  };
  clienteProducto.modifyProducto(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  })
})


// Iniciar el servidor en el puerto 3000
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor Rest escuchando en el puerto ${PORT}`);
});
