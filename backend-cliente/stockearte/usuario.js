const loadGrpcService = require('./grpcClientFactory');
const usuarioCliente = loadGrpcService('usuario.proto', 'usuario', 'UsuarioService');
module.exports = usuarioCliente;
