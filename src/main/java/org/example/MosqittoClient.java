package org.example;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import org.example.utils.Decodage_TTN_Laird;

import static org.example.ConfigMQTT.*;

public class MosqittoClient {
    private Mqtt3AsyncClient client;
    private Decodage_TTN_Laird decodage_ttn_laird;

    public MosqittoClient() {
        client = MqttClient.builder()
                .useMqttVersion3()
                .serverHost(SERVER_URI)
                .serverPort(PORT)
                .sslWithDefaultConfig()
                .buildAsync();
    }

    public void connect() {
        client.connectWith()
                .simpleAuth()
                .username(USER_NAME)
                .password(PASSWORD)
                .applySimpleAuth()
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
                    String json = new String(mqtt3Publish.getPayloadAsBytes());
                    decodage_ttn_laird = new Decodage_TTN_Laird(json);
                    System.out.println("Message reçu sur le topic " + "'" + TOPIC + "'" + ":" + decodage_ttn_laird.getUplinkMessage());
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
}