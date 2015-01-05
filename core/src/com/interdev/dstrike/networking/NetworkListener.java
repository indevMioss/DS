package com.interdev.dstrike.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class NetworkListener extends Listener {

    private Client client;

    public void init(Client client) {
        this.client = client;
    }


    public void connected(Connection connection) {
        Log.info("[CLIENT] You have connected");
        client.sendTCP(new Packet.Packet0LoginRequest());
    }

    public void disconnected(Connection connection) {
        Log.info("[CLIENT] You have disconnected");
    }

    public void received(Connection connection, Object obj) {

        if(obj instanceof Packet.Packet1LoginAnswer) {
            boolean answer = ((Packet.Packet1LoginAnswer) obj).accepted;
            if(answer) {
                Packet.Packet2Message mPacket = new Packet.Packet2Message();
                mPacket.message = "This is the mesage for the server!";
                client.sendTCP(mPacket);

            } else {
                connection.close();
            }
        }

    }


}
