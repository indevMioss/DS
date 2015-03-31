package com.interdev.dstrike.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.interdev.dstrike.Main;
import com.interdev.dstrike.screens.game.GameScreen;
import com.interdev.dstrike.screens.game.PlayerValues;

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
        } else if (obj instanceof Packet.PacketRoomReady) {
            onPacketRoomReady((Packet.PacketRoomReady) obj);
        }

        if (Main.gameScreenReference != null) {
            if (obj instanceof Packet.PacketWaveSpawned) {
                Main.gameScreenReference.player.onPacketWaveSpawned();
            } else if (obj instanceof Packet.PacketGameUpdate) {
                onPacketPacketBattlefieldUnitsUpdate((Packet.PacketGameUpdate) obj);
            } else if (obj instanceof Packet.PacketAnswerUnitPurchase) {
                onPacketAnswerUnitPurchase(((Packet.PacketAnswerUnitPurchase) obj));
            } else if (obj instanceof Packet.PacketAnswerUnitSell) {
                Main.gameScreenReference.player.onPacketAnswerUnitSell(((Packet.PacketAnswerUnitSell) obj).answer);
            } else if (obj instanceof Packet.PacketAnswerUpgrade) {
                Main.gameScreenReference.player.onPacketAnswerUpgrade(((Packet.PacketAnswerUpgrade) obj).answer);
            } else if (obj instanceof Packet.PacketCellsDebug) {
            //    Main.gameScreenReference.player.onPacketCellsDebug(((Packet.PacketCellsDebug) obj).cells); // <------ CELLS DEBUG
            } else if (obj instanceof Packet.PacketRoomDestroyed) {
                Main.gameScreenReference.player.onPacketRoomDestroyed();
            }
        }
    }

    private void onPacketRoomReady(final Packet.PacketRoomReady packet) {
        if (Main.gameScreenReference != null && Main.gameScreenReference.screenLoaded) {
            onReady(packet);
        } else {
            final Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if (Main.gameScreenReference != null && Main.gameScreenReference.screenLoaded) {
                        onReady(packet);
                        timer.stop();
                    }
                }
            }, 2f, 2f);
            timer.start();
        }
    }

    private void onReady(Packet.PacketRoomReady packet) {
        GameScreen.tickInterval = packet.tickInterval;
        System.out.println("GameScreen.tickInterval " + GameScreen.tickInterval);

        if (packet.baseAtTheTop) {
            PlayerValues.MY_BASE_ID = PlayerValues.TOP_BASE_ID;
            PlayerValues.ENEMY_BASE_ID = PlayerValues.BOTTOM_BASE_ID;
        } else {
            PlayerValues.MY_BASE_ID = PlayerValues.BOTTOM_BASE_ID;
            PlayerValues.ENEMY_BASE_ID = PlayerValues.TOP_BASE_ID;
        }

        Packet.PacketReadyToPlay packetReadyToPlay = new Packet.PacketReadyToPlay();
        client.sendTCP(packetReadyToPlay);
    }


    private void onPacketPacketBattlefieldUnitsUpdate(final Packet.PacketGameUpdate packet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Main.gameScreenReference.player.onPacketBattlefieldUpdate(packet);
                    }
                });
            }
        }).start();
    }

    private void onPacketAnswerUnitPurchase(final Packet.PacketAnswerUnitPurchase packet) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Main.gameScreenReference.player.onPacketAnswerUnitPurchase(packet);
                    }
                });
            }
        }).start();
    }

}
