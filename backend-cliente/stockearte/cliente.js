const express = require('express');
const cors = require('cors');
const clienteTienda = require('./tienda');
const clienteUsuario = require('./usuario')
const clienteProducto = require('./producto')


const app = express();
app.use(express.json()); // Middleware para parsear JSON

// CORS
app.use(cors({
  origin: 'http://localhost:4200',
  methods: ['GET', 'POST', 'PUT', 'DELETE'],
  allowedHeaders: ['Content-Type', 'Authorization'],
  credentials: true
}));

// Iniciar el servidor en el puerto 3000
const PORT = 3001;
app.listen(PORT, () => {
  console.log(`Servidor Rest escuchando en el puerto ${PORT}`);
});


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
      usuarioId: tiendaData.usuarioId,
      idUserAdmin: tiendaData.idUserAdmin
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

  clienteTienda.deleteTienda(request, (error, response) => {
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
    }
  };

  if (tiendaData.usuarioId) request.tienda.usuarioId = tiendaData.usuarioId;
  if (tiendaData.idUserAdmin) request.tienda.idUserAdmin = tiendaData.idUserAdmin

  clienteTienda.modifyTienda(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  })
})

// Endpoint para buscar tiendas
app.post('/findTiendas', (req, res) => {
  const { codigo, habilitada, username } = req.body;

  const request = {
    codigo: codigo || '',
    habilitada: habilitada !== undefined ? habilitada : null,
    username: username || ''
  };

  clienteTienda.findTiendas(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

// Endpoint para obtener todas las tiendas
app.get('/getTiendas', (req, res) => {
  const request = {}; // No necesitas enviar datos en la solicitud

  clienteTienda.getTiendas(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});



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
      id: usuarioData.id,
      idUserAdmin: usuarioData.idUserAdmin,
      tiendaCodigo: usuarioData.tiendaCodigo
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

  clienteUsuario.deleteUsuario(request, (error, response) => {
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
      id: usuarioData.id,
      idUserAdmin: usuarioData.idUserAdmin
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
app.post('/findUsuarios', (req, res) => {
  const { username, tiendaId } = req.body;

  const request = {
    username: username || '',
    tiendaId: tiendaId || 0
  };

  clienteUsuario.findUsuarios(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

// Endpoint para obtener todos los usuarios
app.get('/getUsuarios', (req, res) => {
  // Si habilitados_unicamente no se proporciona, asignamos null
  const habilitados = req.query.habilitados === 'true'
    ? true
    : (req.query.habilitados === 'false' ? false : null);

  console.log("habilitados: ", habilitados);

  // Crear un objeto request
  const request = {
    habilitados: habilitados
  };

  clienteUsuario.getUsuarios(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

app.post('/login', (req, res) => {
  const data = req.body;
  const request = {
    userLogin: {
      username: data.username,
      password: data.password,
    }
  };
  clienteUsuario.loginUsuario(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  })
})


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
      foto: productoData.foto,
      cantidad: productoData.cantidad,
      idUserAdmin: productoData.idUserAdmin
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

  clienteProducto.deleteProducto(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});


app.put('/modifyProducto', (req, res) => {
  const productoData = req.body;
  console.log(productoData.foto);
  const request = {
    producto: {
      nombre: productoData.nombre,
      codigo: productoData.apellido,
      color: productoData.color,
      talle: productoData.talle,
      habilitado: productoData.habilitado,
      tiendaIds: productoData.tiendaIds,
      id: productoData.id,
      cantidad: productoData.cantidad,
      foto: productoData.foto,
      idUserAdmin: productoData.idUserAdmin
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

// Endpoint para buscar productos
app.post('/findProductos', (req, res) => {
  const { nombre, codigo, talle, color } = req.body;

  const request = {
    nombre: nombre || '',
    codigo: codigo || '',
    talle: talle || '',
    color: color || ''
  };

  clienteProducto.findProductos(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

// Endpoint para traer productos
app.post('/getProductos', (req, res) => {
  const { username } = req.body;

  const request = {
    username: username || ''
  };

  clienteProducto.getProductos(request, (error, response) => {
    if (error) {
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

