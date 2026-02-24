package server;

import com.google.gson.Gson;
import payload.ClientRequest;
import payload.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        DataBase dataBase = new DataBase();
        Gson gson = new Gson();
        ClientRequest clientRequest = null;
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            clientRequest = gson.fromJson(input.readUTF(), ClientRequest.class);
            ServerResponse serverResponse;
            switch (clientRequest.getType()) {
                case "exit":
                    serverResponse = new ServerResponse("OK");
                    clientSocket.close();
                    System.exit(0);
                    break;
                case "get":
                    String getResult = dataBase.readDataBase(clientRequest.getKey());
                    if (!getResult.equals("ERROR")) {
                        serverResponse = new ServerResponse("OK", getResult);
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
                    String deleteResult = dataBase.readDataBase(clientRequest.getKey());
                    if (!deleteResult.equals("ERROR")) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}