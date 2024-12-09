package org.example;


import java.text.DecimalFormat;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        double valeur;
        MosqittoClient client = new MosqittoClient();
        client.connect();

        Thread.sleep(1000);

        while (true) {
            valeur = random.nextDouble(0, 30);
            client.publish("Temperature:" + decimalFormat.format(valeur));
            Thread.sleep(10000);
        }
    }
}