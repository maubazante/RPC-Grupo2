const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const path = require('path');

const PROTO_PATH = path.join(__dirname,'tienda.proto');
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
});

const tiendaProto = grpc.loadPackageDefinition(packageDefinition).tienda

const tiendaCliente = new tiendaProto.TiendaService('localhost:9090', grpc.credentials.createInsecure());

module.exports = tiendaCliente;