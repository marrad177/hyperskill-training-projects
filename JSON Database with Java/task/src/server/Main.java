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
import java.net.SocketException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static String address = "127.0.0.1";
    private static int port = 23456;
    private static boolean appRunning = true;
    private static ClientHandler clientHandler;
    private static ServerSocket server;

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        Handler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        logger.log(Level.ALL, "Log");

        try{
            server = new ServerSocket(port, 50, InetAddress.getByName(address));
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println("Server started!");
            while (appRunning) {
                clientHandler = new ClientHandler(server.accept());
                executorService.execute(clientHandler);
            }
            server.close();
        } catch (SocketException se) {
            appRunning = false;
        } catch (IndexOutOfBoundsException ioobe) {
            System.out.println("ERROR");
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, ioe.toString(), ioe);
        }
    }
}
