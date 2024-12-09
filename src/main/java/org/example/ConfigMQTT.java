package org.example;

import java.util.UUID;

public class ConfigMQTT {
    static final String SERVER_URI = "127.0.0.1";
    static final int PORT = 1883;
    static final String TOPIC = "temp/b212/romanenko";
    static final String CLIENT_ID = UUID.randomUUID().toString();
}
