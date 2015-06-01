package bean;

import util.JsonToJava;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * @author Maral Khojasteh
 */
public class Server {
    public static  Server server = new Server();
    private Map<String, Deposit> depositMap = new HashMap<String, Deposit>();
    private Logger logger;
    private ServerSocket serverSocket;

    private Server() {
        JsonToJava jsonToJava = new JsonToJava();
        jsonToJava.getJsonData();
        depositMap = jsonToJava.getDepositArrayList();
        try {
            FileHandler fileHandler = new FileHandler("src\\main\\resources\\" + jsonToJava.getOutLog());
            logger = Logger.getLogger("server");
            logger.addHandler(fileHandler);
            serverSocket = new ServerSocket(jsonToJava.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Deposit getDepositById(String id) {

        if (depositMap.containsKey(id)) {
            return depositMap.get(id);
        } else
            return null;
    }

    public Logger getLogger() {
        return logger;
    }

    public void run() {
        try {
            serverSocket.setSoTimeout(10000000);
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
