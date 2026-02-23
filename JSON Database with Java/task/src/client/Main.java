package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import payload.ClientRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    @Parameter(names={"--type", "-t"})
    static String type;
    @Parameter(names={"--key", "-k"})
    static String key;
    @Parameter(names={"--value", "-v"})
    static String value;

    public static void main(String[] args) {
        try {
            Main main = new Main();
            JCommander.newBuilder()
                    .addObject(main)
                    .build()
                    .parse(args);
            String address = "127.0.0.1";
            ClientRequest userRequest = new ClientRequest(type, key, value);
            Gson gson = new Gson();
            String requestBody = gson.toJson(userRequest);
            int port = 23456;
            Socket socket = new Socket(InetAddress.getByName(address), port);
            System.out.println("Client started!");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            output.writeUTF(requestBody);
            System.out.println("Sent: " + requestBody);
//            ServerResponse serverResponse = gson.fromJson(input.readUTF(), ServerResponse.class);
            System.out.println("Received: " + input.readUTF());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (ParameterException pe) {
            System.out.println(pe.getMessage());
        }
    }
}
