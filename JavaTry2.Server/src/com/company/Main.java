package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            UDPServer udpSvr = new UDPServer();
            udpSvr.runServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}






