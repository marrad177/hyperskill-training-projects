package server;

import com.google.gson.Gson;
import payload.ClientRequest;
import payload.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        Handler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        logger.log(Level.ALL, "Log");
        boolean appRunning = true;
        String address = "127.0.0.1";
        int port = 23456;

        try(ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            DataBase dataBase = new DataBase();
            System.out.println("Server started!");
            while (appRunning) {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
                Gson gson = new Gson();
                ClientRequest clientRequest = gson.fromJson(input.readUTF(), ClientRequest.class);
                ServerResponse serverResponse;
                switch (clientRequest.getType()) {
                    case "exit":
                        serverResponse = new ServerResponse("OK");
                        appRunning = false;
                        break;
                    case "get":
                        if(!dataBase.readDataBase(clientRequest.getKey()).equals("ERROR")) {
                            serverResponse = new ServerResponse("OK", dataBase.readDataBase(clientRequest.getKey()));
                        } else {
                            serverResponse = new ServerResponse("ERROR");
                            serverResponse.setReason("No such key");
                        }
                        break;
                    case "set":
                        dataBase.updateDataBase(clientRequest.getKey(), clientRequest.getValue());
                        serverResponse = new ServerResponse("OK");
                        break;
                    case "delete":
                        if(!dataBase.readDataBase(clientRequest.getKey()).equals("ERROR")) {
                            dataBase.deleteEntry(clientRequest.getKey());
                            serverResponse = new ServerResponse("OK");
                        } else {
                            serverResponse = new ServerResponse("ERROR");
                            serverResponse.setReason("No such key");
                        }
                        break;
                    default:
                        serverResponse = new ServerResponse("ERROR");
                }
                output.writeUTF(gson.toJson(serverResponse));
            }
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("ERROR");
        } catch (IOException ioe) {
            logger.log( Level.SEVERE, ioe.toString(), ioe );
        }
    }
}
