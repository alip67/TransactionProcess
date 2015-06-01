package bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

public class ServerHandler extends Thread {
    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {

        try {
            ObjectOutputStream toClientInput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromClientOutput = new ObjectInputStream(socket.getInputStream());
            Logger logger = Server.server.getLogger();
            String clientTerminalId = fromClientOutput.readUTF();

            logger.info("Connected to " + clientTerminalId);
            socket.setSoTimeout(1000);

            while (true) {
                try {
                    Transaction receivedTransaction = (Transaction) fromClientOutput.readObject();
                    logger.info("Received: client Terminal " + clientTerminalId + " , " + receivedTransaction.toString());
                    receivedTransaction.setSuccess(false);
                    Deposit deposit = Server.server.getDepositById(receivedTransaction.getDepositId());
                    if ("withdraw".equals(receivedTransaction.getType())&& deposit != null ) {
                        synchronized (deposit) {

                            if (deposit.validateWithdrawOperation(receivedTransaction.getAmount())) {
                                receivedTransaction.setNewBalance(deposit.withdraw(receivedTransaction.getAmount()));
                                receivedTransaction.setSuccess(true);
                                logger.info("Withdraw Operation Accomplished: client terminal " + clientTerminalId + " , " + receivedTransaction.toString());
                            } else {
                                logger.info("Client Terminal: " + clientTerminalId + " , Transaction " + receivedTransaction.getId() + " : The deposit balance is lower than the requested amount");
                                receivedTransaction.setError("The deposit balance is lower than the requested amount");
                            }
                        }
                    } else if ("deposit".equals(receivedTransaction.getType()) && deposit != null) {
                        synchronized (deposit) {

                            if (deposit.validateDepositOperation(receivedTransaction.getAmount())) {
                                receivedTransaction.setNewBalance(deposit.deposit(receivedTransaction.getAmount()));
                                receivedTransaction.setSuccess(true);
                                logger.info("Deposit Operation Accomplished: client terminal " + clientTerminalId + " , " + receivedTransaction.toString());
                            } else {
                                logger.info("Client Terminal: " + clientTerminalId + " , Transaction " + receivedTransaction.getId() + " : The deposit operation is bigger than the upper bound");
                                receivedTransaction.setError(" The deposit operation is bigger than the upper bound");
                            }

                        }
                    } else {
                        logger.info("client terminal " + clientTerminalId + " transaction " + receivedTransaction.getId() + " Request is not valid");
                        receivedTransaction.setError("Request is not valid");
                    }
                    toClientInput.writeObject(receivedTransaction);
                    logger.info("The results for Transaction " + receivedTransaction.getId() + " sends back to terminal " + clientTerminalId);
                } catch (SocketTimeoutException e) {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

