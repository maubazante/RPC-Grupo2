const express = require('express');
const clienteTienda = require('./tienda'); // Importar el cliente gRPC

const app = express();
app.use(express.json()); // Middleware para parsear JSON

// Endpoint para crear una tienda
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

// Endpoint para eliminar una tienda 
app.delete('/deleteTienda', (req, res) => {
  const { codigo } = req.body;

  const request = { codigo };

  clienteTienda.DeleteTienda(request, (error, response) => {
    if (error){
      res.status(500).send(error);
    } else {
      res.json(response);
    }
  });
});

// Iniciar el servidor en el puerto 3000
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Servidor Rest escuchando en el puerto ${PORT}`);
});
