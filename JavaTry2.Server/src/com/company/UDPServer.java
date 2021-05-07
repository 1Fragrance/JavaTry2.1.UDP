package com.company;

import Common.Model.ExpressionRequestModel;
import Common.Model.OperationType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public final static int SERVER_PORT = 5555;

    public void runServer() throws IOException, ClassNotFoundException {


        try (DatagramSocket socket = new DatagramSocket(SERVER_PORT)) {

            System.out.println("Server is running");

            var logger = new Logger();
            var expressionSolver = new ExpressionSolver();
            byte[] buffer = new byte[2048];

            while (true) {
                // listening
                DatagramPacket recvPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(recvPacket);

                // processing
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(recvPacket.getData()));
                var requestModel  = (ExpressionRequestModel) ois.readObject();
                ois.close();
                System.out.println("Received request from: " + recvPacket.getAddress() + ":" + recvPacket.getPort());

                if(requestModel.getOperation() == OperationType.CALCULATE) {
                    System.out.println("Data: x = " + requestModel.getX() + " y = " + requestModel.getY() + " z = " + requestModel.getZ());

                    // calculating
                    var answer = expressionSolver.solveExpression(requestModel.getX(), requestModel.getY(), requestModel.getZ());
                    var answerBytes = String.valueOf(answer).getBytes();

                    // response
                    var sendPacket = new DatagramPacket(answerBytes, answerBytes.length, recvPacket.getAddress(), recvPacket.getPort());
                    socket.send(sendPacket);

                    // logging
                    logger.logResult(recvPacket.getAddress() + ":" + recvPacket.getPort(), answer);
                }
                else if(requestModel.getOperation() == OperationType.EXIT) {
                    System.out.println("Server stopped");
                    break;
                }
            }
        }
    }
}
