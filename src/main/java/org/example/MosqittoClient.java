package org.example;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import static org.example.ConfigMQTT.*;

public class MosqittoClient {
    private Mqtt3AsyncClient client;

    public MosqittoClient() {
        client = MqttClient.builder()
                .useMqttVersion3()
                .identifier(CLIENT_ID)
                .serverHost(SERVER_URI)
                .serverPort(PORT)
                .buildAsync();
    }

    public void connect() {
        client.connectWith()
                .send()
                .whenComplete(((mqtt3ConnAck, throwable) -> {
                    if (throwable != null) {
                        System.err.println("Erreur lors de la connexion : " + throwable.getMessage());
                    } else {
                        System.out.println("Connecté au broker MQTT !");
                        subscribe();
                    }
                }));
    }

    public void subscribe() {
        client.subscribeWith()
                .topicFilter(TOPIC)
                .qos(MqttQos.AT_MOST_ONCE)
                .callback(mqtt3Publish -> {
                    String message = new String(mqtt3Publish.getPayloadAsBytes());
                    System.out.println("Message reçu sur le topic " + "'" + TOPIC + "'" + ":" + message);
                })
                .send()
                .whenComplete(((mqtt3PubAck, throwable) -> {
                    if (throwable != null) {
                        System.err.println("Erreur lors de l'abonnement : " + throwable.getMessage());
                    } else {
                        System.out.println("Abonné au topic " + "'" + TOPIC + "'" + " !");
                    }
                }));
    }

    public void publish(String message) {
        client.publishWith()
                .topic(TOPIC)
                .payload(message.getBytes())
                .qos(MqttQos.AT_LEAST_ONCE)
                .send()
                .whenComplete(((mqtt3PubAck, throwable) -> {
                    if (throwable != null) {
                        System.err.println("Erreur lors de la publication : " + throwable.getMessage());
                    } else {
                        System.out.println("Publié au broker MQTT !");
                    }
                }));
    }
}