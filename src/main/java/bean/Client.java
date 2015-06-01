package bean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.XmlParserSAX;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Client extends Thread {
    private static int threadNumber = 0;
    InputStream xmlInput;
    private String terminalId;
    private String terminalType;
    private String serverIp;
    private int serverPort;
    private String outLogPath;
    private Socket socket;
    List<Transaction> transactionList = new ArrayList<Transaction>();
    List<Terminal> terminalList = new ArrayList<Terminal>();

    public Client() {
        super("" + threadNumber);
        try {
            xmlInput = new FileInputStream("src\\main\\resources\\terminal" + this.getName() + ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        terminalList = XmlParserSAX.xmlParserSAX(xmlInput).getTerminalList();
        for(Terminal terminal : terminalList){
            terminalId = terminal.getTerminalId();
            terminalType = terminal.getTerminalType();
            serverIp = terminal.getServerIp();
            serverPort = terminal.getServerPort();
            outLogPath = terminal.getOutLogPath();
        }
        transactionList = XmlParserSAX.xmlParserSAX(xmlInput).getTransactionList();
        start();
    }

    public void run() {
        try {
            FileHandler fileHandler = new FileHandler("src\\main\\resources\\" + outLogPath);
            Logger logger = Logger.getLogger(this.getName());
            logger.addHandler(fileHandler);
            socket = new Socket(serverIp, serverPort);
            logger.info("Connected to Server");
            ObjectOutputStream toServerInput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServerOutput = new ObjectInputStream(socket.getInputStream());

            toServerInput.writeBytes("" + terminalId);

            Iterator<Transaction> iterator = transactionList.iterator();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element transactionsElement = doc.createElement("transactions");
            doc.appendChild(transactionsElement);
            while (iterator.hasNext()) {
                Transaction requestTransaction = iterator.next();

                toServerInput.writeObject(requestTransaction);
                logger.info("Sends request to server for transaction " + requestTransaction.getId());

                Transaction responseTransaction = (Transaction) fromServerOutput.readObject();
                logger.info("Answer from Server for Current transaction " + responseTransaction.getId());

                if (responseTransaction.isSuccess()) {
                    logger.info("Transaction " + responseTransaction.getId() + " has done successfully and The new balance is " + responseTransaction.getNewBalance());
                } else {
                    logger.info("Transaction " + responseTransaction.getId() + " fails");
                    logger.info(responseTransaction.getError());
                }

                Element transactionElement = doc.createElement("transaction");
               // set attribute to transaction element
                transactionElement.setAttribute("id", responseTransaction.getId());
                // Type element
                transactionElement.setAttribute("type", responseTransaction.getType());
                // amount element
                transactionElement.setAttribute("amount", responseTransaction.getAmount().toString());
                // deposit element
                transactionElement.setAttribute("deposit", responseTransaction.getDepositId());
                // success element
                transactionElement.setAttribute("success", responseTransaction.isSuccess() ? "succeed" : "fail");
                if (responseTransaction.isSuccess()) {
                    //newBalance element
                    transactionElement.setAttribute("newBalance", responseTransaction.getNewBalance().toString());
                }

                transactionsElement.appendChild(transactionElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src\\main\\resources\\response" + terminalId + ".xml"));
            transformer.transform(source, result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }



    public static void main(String args[]) {

        for (int i = 0; i < 2; i++) {
            new Client();
        }
    }

}

