package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import payload.ServerResponse;

import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    // Test path
    // String path = System.getProperty("user.dir") + "/src/server/data/db.json";
    // Local path
    String path = System.getProperty("user.dir") + "/JSON Database with Java/task/src/server/data/db.json";

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        Gson gson = new Gson();
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            JsonObject clientRequest = gson.fromJson(new InputStreamReader(input), JsonObject.class);
            String commandType = clientRequest.has("type") ? clientRequest.get("type").getAsString() : "none";
            ServerResponse serverResponse;
            switch (commandType) {
                case "exit":
                    serverResponse = new ServerResponse("OK");
                    clientSocket.close();
                    System.exit(0);
                    break;
                case "get":
                    if(clientRequest.has("key")) {
                        serverResponse = new ServerResponse("OK", clientRequest.get("key").getAsString());
                    } else {
                        serverResponse = new ServerResponse("ERROR");
                        serverResponse.setReason("No such key");
                    }
                    break;
                case "set":
                    if(clientRequest.has("key")) {
                        String keyToAdd = clientRequest.get("key").getAsString();
                        File jsonInputFile = new File(path);
                        if (jsonInputFile != null) {
                            JsonReader jsonReader = new JsonReader(new FileReader(jsonInputFile));
                            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
                            if(jsonObject.has(keyToAdd)) {
                                jsonObject.add(keyToAdd, clientRequest.get(keyToAdd));
                            }
                        gson.toJson(jsonObject, new FileWriter(jsonInputFile));
                        }
                    }
                    serverResponse = new ServerResponse("OK");
                    break;
                case "delete":
                    if(clientRequest.has("key")) {
                        String keyToDelete = clientRequest.get("key").getAsString();
                        File jsonInputFile = new File(path);
                        if (jsonInputFile != null) {
                            JsonReader jsonReader = new JsonReader(new FileReader(jsonInputFile));
                            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
                            if(jsonObject.has(keyToDelete)) {
                                jsonObject.remove(keyToDelete);
                                gson.toJson(jsonObject, new FileWriter(jsonInputFile));
                                serverResponse = new ServerResponse("OK");
                            } else {
                                serverResponse = new ServerResponse("ERROR");
                            }
                        } else  {
                            serverResponse = new ServerResponse("ERROR");
                        }
                    } else {
                        serverResponse = new ServerResponse("ERROR");
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