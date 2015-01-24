package com.interdev.dstrike.networking;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Scanner;

public class DSClient {
    public static int port = 54555;
    public static String serverIP = "127.0.0.1";
    public static int connectionTimeout = 5000; //ms

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
            client.connect(connectionTimeout, serverIP, port);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }
    }

    private void register() {
        Kryo kryo = client.getKryo();
        kryo.register(Packet.PacketLoginRequest.class);
        kryo.register(Packet.PacketLoginAnswer.class);
        kryo.register(Packet.PacketMessage.class);

        kryo.register(Packet.PacketBattlefieldUnitsUpdate.class);
        kryo.register(Packet.PacketDeadUnitsIDs.class);
        kryo.register(Packet.PacketReadyToPlay.class);
        kryo.register(Packet.PacketWaveSpawned.class);

    }

    public void sendReadyToPlayPacket() {
        client.sendTCP(new Packet.PacketReadyToPlay());
    }
}
