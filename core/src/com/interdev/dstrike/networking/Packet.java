package com.interdev.dstrike.networking;

public class Packet {
    public static class Packet0LoginRequest { }
    public static class Packet1LoginAnswer { boolean accepted = false; }
    public static class Packet2Message {String message; }
}