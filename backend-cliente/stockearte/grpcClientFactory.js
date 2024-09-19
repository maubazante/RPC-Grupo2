const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const path = require('path');
const config = require('./config');

/**
 * Carga un servicio gRPC desde un archivo .proto.
 * @param {string} protoFileName - nombre del archivo .proto que contiene el servicio.
 * @param {string} packageName - nombre del paquete en el archivo .proto.
 * @param {string} serviceName - nombre del servicio dentro del paquete.
 * @returns {Object} - una instancia del servicio.
 */

function loadGrpcService(protoFileName, packageName, serviceName) {
    const PROTO_PATH = path.join(__dirname, 'proto', protoFileName);
    const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
        keepCase: true,
        longs: String,
        enums: String,
        defaults: true,
        oneofs: true
    });

    const proto = grpc.loadPackageDefinition(packageDefinition)[packageName];

    // Verificamos si el servicio está correctamente definido
    if (!proto || !proto[serviceName]) {
        throw new Error(`El servicio ${serviceName} no se encontró en el paquete ${packageName}`);
    }

    const { host, port } = config.grpcServer;
    return new proto[serviceName](`${host}:${port}`, grpc.credentials.createInsecure());
}

module.exports = loadGrpcService;
