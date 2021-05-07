package com.company;

import Common.Model.ExpressionRequestModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    private final static int SERVER_PORT = 5555;
    private final static String SERVER_ADDRESS = "127.0.0.1";

    private final static String SOLVE_EXPRESSION_CHOICE = "1";
    private final static String EXIT_CHOICE = "2";

    public void runClient() throws IOException {

        var scanner = new Scanner(System.in);
        String choice;

        try (DatagramSocket socket = new DatagramSocket()) {

            System.out.println("You was successfully connected to the server.");

            var exitFlag = false;
            while(!exitFlag)
            {
                System.out.println("\nMenu:");
                System.out.println("1. Solve expression");
                System.out.println("2. Exit");

                do {
                    choice = scanner.nextLine();
                } while(!choice.equals(SOLVE_EXPRESSION_CHOICE) && !choice.equals(EXIT_CHOICE));

                switch (choice) {
                    case SOLVE_EXPRESSION_CHOICE: {
                        var requestModel = GetInput(scanner);

                        var buffer = ConvertModelToByteArray(requestModel);
                        var bufferLength = buffer.length;;

                        // send
                        var sendPacket = new DatagramPacket(buffer, bufferLength, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                        socket.send(sendPacket);
                        System.out.println("Request was sent to the server");

                        // get
                        var responseBuffer = new byte[2048];
                        var recvPacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                        socket.receive(recvPacket);

                        // process
                        var response = new String(recvPacket.getData()).trim();
                        System.out.println("Server response: " + response);

                        break;
                    }
                    case EXIT_CHOICE: {

                        var model = new ExpressionRequestModel();
                        var byteArray = ConvertModelToByteArray(model);

                        var sendPacket = new DatagramPacket(byteArray, byteArray.length, InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                        socket.send(sendPacket);
                        System.out.println("Request was sent to the server");

                        exitFlag = true;
                        break;
                    }
                }
            }
        }
    }

    private byte[] ConvertModelToByteArray(ExpressionRequestModel model) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(model);
        oos.flush();

        return baos.toByteArray();
    }

    private ExpressionRequestModel GetInput(Scanner scanner) {
        String x;
        String y;
        String z;

        do {
            System.out.println("Please, enter x:");
            x = scanner.nextLine();
        } while(!isNumeric(x));

        do {
            System.out.println("Please, enter y:");
            y = scanner.nextLine();
        } while(!isNumeric(y));

        do {
            System.out.println("Please, enter z:");
            z = scanner.nextLine();
        } while(!isNumeric(z));

        return new ExpressionRequestModel(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
    }


    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
}
