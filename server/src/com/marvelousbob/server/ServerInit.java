package com.marvelousbob.server;

public class ServerInit {

    public static final int PORT = 80;

    public static void main(String[] args) {
        System.out.println("START MAIN!");
        new MainServer();
        System.out.println("SERVER RUNNING!");
    }
}
