package server;

import com.google.gson.*;
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
    Gson gson = new GsonBuilder().setLenient().create();

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            String inputLine = input.readUTF();
            JsonObject clientRequest = gson.fromJson(inputLine, JsonObject.class);
            String commandType = clientRequest.has("type") ? clientRequest.get("type").getAsString() : "none";
            ServerResponse serverResponse;
            switch (commandType) {
                case "exit":
                    serverResponse = new ServerResponse("OK");
                    clientSocket.close();
                    System.exit(0);
                    break;
                case "get":
                    if (clientRequest.has("key")) {
                        JsonElement keyElement = clientRequest.get("key");
                        File jsonDbFile = new File(path);
                        JsonReader jsonReader = new JsonReader(new FileReader(jsonDbFile));
                        JsonObject dbJsonObject = gson.fromJson(jsonReader, JsonObject.class);
                        serverResponse = getValueByKey(keyElement, dbJsonObject);
                    } else {
                        serverResponse = new ServerResponse("ERROR");
                        serverResponse.setReason("No key given");
                    }
                    break;
                case "set":
                    if (clientRequest.has("key")) {
                        JsonElement keyToSet = clientRequest.get("key");
                        JsonElement valueToSet = clientRequest.get("value");
                        JsonObject dbJsonObject = getDbFromFile() == null ? new JsonObject() : getDbFromFile().getAsJsonObject();
                        JsonElement newDbJsonObject = setValueByKey(keyToSet, valueToSet, dbJsonObject);
                        writeDbToFile(newDbJsonObject);
                        serverResponse = new ServerResponse("OK");
                    } else {
                        serverResponse = new ServerResponse("No key given");
                    }
                    break;
                case "delete":
                    if (clientRequest.has("key")) {
                        JsonElement keyToDelete = clientRequest.get("key");
                        JsonObject dbJsonObject = getDbFromFile() == null ? new JsonObject() : getDbFromFile().getAsJsonObject();
                        JsonElement newDbJsonObject = deleteValueByKey(keyToDelete, dbJsonObject);
                        writeDbToFile(newDbJsonObject);
                        serverResponse = new ServerResponse("OK");
                    } else {
                        serverResponse = new ServerResponse("No key given");
                    }
                    break;
                default:
                    serverResponse = new ServerResponse("ERROR");
            }
            output.writeUTF(gson.toJson(serverResponse));
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    JsonElement getDbFromFile() throws FileNotFoundException, IOException {
        File jsonDbFile = new File(path);

        if (!jsonDbFile.exists()) {
            jsonDbFile.createNewFile();
        }
        JsonReader jsonReader = new JsonReader(new FileReader(jsonDbFile));
        return gson.fromJson(jsonReader, JsonObject.class);
    }

    void writeDbToFile(JsonElement newDbJsonObject) throws IOException {
        File jsonDbFile = new File(path);

        if (!jsonDbFile.exists()) {
            jsonDbFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(jsonDbFile);
        gson.toJson(newDbJsonObject, fileWriter);
        fileWriter.close();
    }

    JsonObject setValueByKey(JsonElement keyElement, JsonElement valueElement, JsonObject dbObject) throws FileNotFoundException {
        if (keyElement.isJsonPrimitive()) {
            if (dbObject.has(keyElement.getAsString())) {
                dbObject.remove(keyElement.getAsString());
                dbObject.add(keyElement.getAsString(), valueElement);
            } else {
                dbObject.add(keyElement.getAsString(), valueElement);
            }

        } else if (keyElement.isJsonArray() && keyElement.getAsJsonArray().size() > 1) {
            JsonElement recentKey = keyElement.getAsJsonArray().remove(0);
            if (dbObject.has(recentKey.getAsString())) {
                setValueByKey(keyElement, valueElement, dbObject.get(recentKey.getAsString()).getAsJsonObject());
            }
        } else if (keyElement.isJsonArray() && keyElement.getAsJsonArray().size() == 1) {
            JsonElement recentKey = keyElement.getAsJsonArray().remove(0);
            if (dbObject.has(recentKey.getAsString())) {
                dbObject.remove(recentKey.getAsString());
                dbObject.add(recentKey.getAsString(), valueElement);
            } else {
                dbObject.add(recentKey.getAsString(), valueElement);
            }
        }
        return dbObject;
    }

    JsonObject deleteValueByKey(JsonElement keyElement, JsonObject dbObject) throws FileNotFoundException {
        if (keyElement.isJsonPrimitive()) {
            if (dbObject.has(keyElement.getAsString())) {
                dbObject.remove(keyElement.getAsString());
            }
        } else if (keyElement.isJsonArray() && keyElement.getAsJsonArray().size() > 1) {
            JsonElement recentKey = keyElement.getAsJsonArray().remove(0);
            if (dbObject.has(recentKey.getAsString())) {
                deleteValueByKey(keyElement, dbObject.get(recentKey.getAsString()).getAsJsonObject());
            }
        } else if (keyElement.isJsonArray() && keyElement.getAsJsonArray().size() == 1) {
            JsonElement recentKey = keyElement.getAsJsonArray().remove(0);
            if (dbObject.has(recentKey.getAsString())) {
                dbObject.remove(recentKey.getAsString());
            }
        }
        return dbObject;
    }

    ServerResponse getValueByKey(JsonElement keyElement, JsonElement dbElement) throws FileNotFoundException {
        ServerResponse serverResponse;
        if (keyElement.isJsonArray() && keyElement.getAsJsonArray().size() > 1) {
            String firstKey = keyElement.getAsJsonArray().get(0).getAsString();
            keyElement.getAsJsonArray().remove(0);
            if (dbElement.isJsonObject() && dbElement.getAsJsonObject().has(firstKey)) {
                JsonElement firstKeyValue = dbElement.getAsJsonObject().get(firstKey);
                serverResponse = getValueByKey(keyElement, firstKeyValue);
            } else {
                serverResponse = new ServerResponse("ERROR");
                serverResponse.setReason("No such key");
            }
        } else if (keyElement.isJsonArray() && keyElement.getAsJsonArray().size() == 1) {
            String key = keyElement.getAsJsonArray().get(0).getAsString();
            if (dbElement.getAsJsonObject().has(key)) {
                JsonElement result = dbElement.getAsJsonObject().get(key);
                serverResponse = new ServerResponse("OK", result);
            } else {
                serverResponse = new ServerResponse("ERROR");
                serverResponse.setReason("No such key");
            }
        } else {
            serverResponse = new ServerResponse("ERROR");
        }
        return serverResponse;
    }
}