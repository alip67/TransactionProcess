package util;


import bean.Terminal;
import bean.Transaction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class SaxHandler extends DefaultHandler {

    private List<Terminal> terminalList = new ArrayList<Terminal>();
    private Terminal terminal = null;
    private List<Transaction> transactionList = new ArrayList<Transaction>();
    private Transaction transaction = null;

    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }


    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("terminal".equals(qName)) {
            terminal = new Terminal();
            terminal.setTerminalId(attributes.getValue("id"));
            U.wl("" + terminal.getTerminalId());
            terminal.setTerminalType(attributes.getValue("type"));
            U.wl("" + terminal.getTerminalType());
        } else if ("server".equals(qName)) {
            terminal.setServerIp(attributes.getValue("ip"));
            U.wl("" + terminal.getServerIp());
            terminal.setServerPort(Integer.parseInt(attributes.getValue("port")));
            U.wl("" + terminal.getServerPort());
        } else if ("outLog".equals(qName)) {
            terminal.setOutLogPath(attributes.getValue("path"));
            U.wl("" + terminal.getOutLogPath());
            terminalList.add(terminal);
        } else if ("transaction".equals(qName)) {
            transaction = new Transaction();
            transaction.setId(attributes.getValue("id"));
            U.wl("" + transaction.getId());
            transaction.setType(attributes.getValue("type"));
            U.wl("" + transaction.getType());
            transaction.setAmount(new BigDecimal(attributes.getValue("amount")));
            U.wl("" + transaction.getAmount());
            transaction.setDepositId(attributes.getValue("deposit"));
            U.wl("" + transaction.getDepositId());
            transactionList.add(transaction);
        }

    }
}


