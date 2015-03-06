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
        client = new Client(32769, 8192);
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

        kryo.register(PackedCell.class);
        kryo.register(PackedCell[].class);
        kryo.register(PackedCell[][].class);
        kryo.register(Packet.PacketCellsDebug.class);

        kryo.register(Packet.PacketLoginAnswer.class);
        kryo.register(Packet.PacketRoomReady.class);
        kryo.register(Packet.PacketRoomDestroyed.class);

        kryo.register(PackedUnit.class);
        kryo.register(PackedUnit[].class);
        kryo.register(Packet.PacketBattlefieldUnitsUpdate.class);
        kryo.register(Packet.PacketWaveSpawned.class);
        kryo.register(Packet.PacketAnswerUnitPurchase.class);
        kryo.register(Packet.PacketAnswerUnitSell.class);
        kryo.register(Packet.PacketAnswerUpgrade.class);

        kryo.register(Packet.PacketLoginRequest.class);
        kryo.register(Packet.PacketReadyToPlay.class);
        kryo.register(Packet.PacketRequestUnitPurchase.class);
        kryo.register(Packet.PacketRequestUnitSell.class);
        kryo.register(Packet.PacketRequestUpgrade.class);

    }

    public void sendReadyToPlayPacket() {
        client.sendTCP(new Packet.PacketReadyToPlay());
    }
}
