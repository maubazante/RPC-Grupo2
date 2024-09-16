const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const express = require('express');
const path = require('path');
const app = express();

const PROTO_PATH = path.join(__dirname,'tienda.proto');
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
});

const tiendaProto = grpc.loadPackageDefinition(packageDefinition).tienda

const cliente = new tiendaProto.TiendaService('localhost:9090', grpc.credentials.createInsecure());

app.post('/createTienda', express.json(), (req, res) => {
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

    cliente.createTienda(request, (error, response) => {
        if (error){
            res.status(500).send(error);
        } else {
            res.json(response);
        }
    });
});

const PORT = 3000
app.listen(PORT, () => {
    console.log(`Servidor Rest escuchando en el puerto ${PORT}`);
})