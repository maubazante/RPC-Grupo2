const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const path = require('path');

const PROTO_PATH = path.join(__dirname, 'helloworld.proto');

// Cargar el archivo .proto
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true,
});

// Crear el paquete gRPC a partir del archivo .proto cargado
const helloProto = grpc.loadPackageDefinition(packageDefinition).helloworld;

// Crear un cliente gRPC que se conectará al servidor en localhost:9090
const client = new helloProto.Greeter('localhost:9090', grpc.credentials.createInsecure());

// Definir el mensaje a enviar
const request = {
  name: 'Node.js Client',
};

// Hacer la llamada gRPC al método SayHello
client.SayHello(request, (error, response) => {
  if (error) {
    console.error('Error:', error);
  } else {
    console.log('Greeting:', response.message);
  }
});
