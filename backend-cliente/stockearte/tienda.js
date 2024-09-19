const loadGrpcService = require('./grpcClientFactory');
const tiendaCliente = loadGrpcService('tienda.proto', 'tienda', 'TiendaService');
module.exports = tiendaCliente;
