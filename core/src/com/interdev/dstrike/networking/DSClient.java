package com.interdev.dstrike.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Scanner;

public class DSClient {
    public Client client;
    public Scanner scanner;

    public DSClient() {
        scanner = new Scanner(System.in);
        client = new Client();
        register();

        NetworkListener networkListener = new NetworkListener();
        networkListener.init(client);
        client.addListener(networkListener);

        client.start();
        try {
            client.connect(5000, "127.0.0.1", 54555);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }
    }

    private void register() {
        Kryo kryo = client.getKryo();
        kryo.register(Packet.Packet0LoginRequest.class);
        kryo.register(Packet.Packet1LoginAnswer.class);
        kryo.register(Packet.Packet2Message.class);
    }

}
