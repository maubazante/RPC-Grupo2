const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const path = require('path');

const PROTO_PATH = path.join(__dirname,'usuario.proto');
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true
});

const usuarioProto = grpc.loadPackageDefinition(packageDefinition).usuario

const usuarioCliente = new usuarioProto.UsuarioService('localhost:9090', grpc.credentials.createInsecure());

module.exports = usuarioCliente;