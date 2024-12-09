package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MosqittoClient client = new MosqittoClient();
        client.connect();
    }
}