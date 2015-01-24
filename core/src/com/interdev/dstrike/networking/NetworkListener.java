package com.interdev.dstrike.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.Main;

public class NetworkListener extends Listener {

    private Client client;

    public void init(Client client) {
        this.client = client;
    }


    public void connected(Connection connection) {
        Log.info("[CLIENT] You have connected");
        client.sendTCP(new Packet.PacketLoginRequest());
    }

    public void disconnected(Connection connection) {
        Log.info("[CLIENT] You have disconnected");
    }

    public void received(Connection connection, Object obj) {
        if (obj instanceof Packet.PacketLoginAnswer) {
            boolean answer = ((Packet.PacketLoginAnswer) obj).accepted;
            if (!answer) {
                connection.close();
            }
        }
        if (Main.gameScreenReference != null) {
            if (obj instanceof Packet.PacketWaveSpawned) {
                Main.gameScreenReference.player.onPacketWaveSpawned();
            } else if (obj instanceof Packet.PacketBattlefieldUnitsUpdate) {
                PackedUnit[] player1PackedUnits = ((Packet.PacketBattlefieldUnitsUpdate) obj).Player1PackedUnits;
                PackedUnit[] player2PackedUnits = ((Packet.PacketBattlefieldUnitsUpdate) obj).Player2PackedUnits;
                Main.gameScreenReference.player.onPacketBattlefieldUpdate(player1PackedUnits, player2PackedUnits);
            } else if (obj instanceof Packet.PacketAnswerUnitPurchase) {
                Main.gameScreenReference.player.onPacketAnswerUnitPurchase(((Packet.PacketAnswerUnitPurchase) obj).id);
            } else if (obj instanceof Packet.PacketAnswerUnitSell) {
                Main.gameScreenReference.player.onPacketAnswerUnitSell(((Packet.PacketAnswerUnitSell) obj).answer);
            } else if (obj instanceof Packet.PacketAnswerUpgrade) {
                Main.gameScreenReference.player.onPacketAnswerUpgrade(((Packet.PacketAnswerUpgrade) obj).answer);
            }
        }
    }

}
