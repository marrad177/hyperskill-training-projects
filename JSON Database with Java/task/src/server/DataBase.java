package server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private final Map<String, String> dataBase;
//    String path = System.getProperty("user.dir") + "/src/server/data/db.json";
    String path = System.getProperty("user.dir") + "/JSON Database/task/src/server/data/db.json";

    public DataBase() throws FileNotFoundException {
        this.dataBase = new HashMap<>(1000);
    }

    public String readDataBase(String key) {
        if(dataBase.containsKey(key)){
            return dataBase.get(key);
        } else {
            return "ERROR";
        }
    }

    public String updateDataBase(String key, String text) {
        if (text.length() > 1000) {
            return "ERROR";
        } else {
            File file = new File(path);
            try(FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write();
            } catch(IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            return "OK";
        }
    }

    public String deleteEntry(String key) {
        if (!dataBase.containsKey(key)) {
            return "ERROR";
        } else {
            dataBase.remove(key);
            return "OK";
        }
    }
}
