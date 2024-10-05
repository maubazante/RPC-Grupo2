const { Kafka } = require('kafkajs');

// Configurar el Kafka Broker (puede ser localhost o tu IP)
const kafka = new Kafka({
    clientId: 'node-kafka-producer',
    brokers: ['localhost:9092'] // Cambia el puerto si es diferente
});

// Crear un productor
const producer = kafka.producer();

// Función para enviar el mensaje
const sendMessage = async (message) => {
    await producer.connect(); // Conectar al broker
    await producer.send({
        topic: 'topic-test',  // El mismo topic que usas en Spring Boot
        messages: [
            { value: message },
        ],
    });
    console.log(`Message "${message}" sent successfully!`);
    await producer.disconnect(); // Desconectar
};

module.exports = sendMessage;
