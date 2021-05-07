package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            UDPClient client = new UDPClient();
            client.runClient();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

