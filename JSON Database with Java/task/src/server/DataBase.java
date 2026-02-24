package server;

import client.service.JsonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import payload.DbEntry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private final Map<String, String> dataBase;
    // Test path
    // String path = System.getProperty("user.dir") + "/src/server/data/db.json";
    // Local path
    String path = System.getProperty("user.dir") + "/JSON Database with Java/task/src/server/data/db.json";

    public DataBase() {
        this.dataBase = readDbFromFile();
    }

    public String readDataBase(String key) {
        if (dataBase.containsKey(key)) {
            return dataBase.get(key);
        } else {
            return "ERROR";
        }
    }

    public String updateDataBase(String key, String text) {
        if (text.length() > 1000) {
            return "ERROR";
        } else {
            dataBase.put(key, text);
            writeDbToFile();
            return "OK";
        }
    }

    public String deleteEntry(String key) {
        if (!dataBase.containsKey(key)) {
            return "ERROR";
        } else {
            dataBase.remove(key);
            writeDbToFile();
            return "OK";
        }
    }

    public void writeDbToFile() {
        Gson gson = new GsonBuilder().create();
        File file = new File(path);
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter isr = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, String> entry : dataBase.entrySet()) {
                DbEntry dbEntry = new DbEntry(entry.getKey(), entry.getValue());
                String line = gson.toJson(dbEntry) + System.getProperty("line.separator");
                isr.write(line);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public Map<String, String> readDbFromFile() {
        File file = new File(path);
        Gson gson = new GsonBuilder().create();

        StringBuilder fileContent = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            JsonObject clientRequest = gson.fromJson(fileContent.toString(), JsonObject.class);
            //ClientRequest clientRequest = gson.fromJson(line, ClientRequest.class);
            if (clientRequest.has("key")) {
//                key = JsonService.getValuesInObject(clientRequest, "key").get(0);
            }
            if (clientRequest.has("value")) {
//                value = JsonService.getValuesInObject(clientRequest, "value").get(0);
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return new HashMap<>();
    }
}