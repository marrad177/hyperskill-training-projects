package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import payload.ClientRequest;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    @Parameter(names = {"--type", "-t"})
    static String type;
    @Parameter(names = {"--key", "-k"})
    static String key;
    @Parameter(names = {"--value", "-v"})
    static String value;
    @Parameter(names = {"--filename", "-in"})
    static String fileName;

    static String address = "127.0.0.1";
    static int port = 23456;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        // Test path
        // String path = System.getProperty("user.dir") + "/src/client/data/" + fileName;
        // Local path
        String path = System.getProperty("user.dir") + "/JSON Database with Java/task/src/client/data/" + fileName;
        File jsonInputFile = new File(path);
        Gson gson = new GsonBuilder().create();

        try {
            String requestBody;
            if (fileName != null) {
                JsonReader jsonReader = new JsonReader(new FileReader(jsonInputFile));
                JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
                requestBody = jsonObject.toString();
            } else {
                ClientRequest userRequest = new ClientRequest(type, key, value);
                requestBody = gson.toJson(userRequest);
            }
            Socket socket = new Socket(InetAddress.getByName(address), port);
            System.out.println("Client started!");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            output.writeUTF(requestBody);
            System.out.println("Sent: " + requestBody);
            System.out.println("Received: " + input.readUTF());
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (ParameterException pe) {
            System.out.println(pe.getMessage());
        }
    }
}
