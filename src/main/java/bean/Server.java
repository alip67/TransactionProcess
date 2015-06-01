package bean;

import util.JsonToJava;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Server {

    private Map<String, Deposit> depositMap = new HashMap<String, Deposit>();
    private Logger logger;
    private ServerSocket serverSocket;
    public static Server server = new Server();

    private Server() {
        depositMap.putAll(JsonToJava.getInstance().getJsonData());
        for (Map.Entry<String, Deposit> entry : depositMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());
        }

        try {
            FileHandler fileHandler = new FileHandler("src\\main\\resources\\" + JsonToJava.getInstance().getOutLog());
            logger = Logger.getLogger("server");
            logger.addHandler(fileHandler);
            serverSocket = new ServerSocket(JsonToJava.getInstance().getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Deposit getDepositById(String id) {

        if (depositMap.containsKey(id)) {
            return (Deposit) depositMap.get(id);
        } else
            return null;
    }

    public Logger getLogger() {
        return logger;
    }

    public void run() {
        try {
            serverSocket.setSoTimeout(100000000);
            while (true) {
                new ServerHandler(serverSocket.accept());
            }
        } catch (SocketTimeoutException e) {
            logger.info("Server  terminates");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        Server.server.run();
    }
}
