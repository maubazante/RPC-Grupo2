const loadGrpcService = require('./grpcClientFactory');
const productoCliente = loadGrpcService('producto.proto', 'producto', 'ProductoService');
module.exports = productoCliente;
