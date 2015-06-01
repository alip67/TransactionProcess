package util;

import bean.Terminal;
import bean.Transaction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParserSAX {

    public static List<Terminal> xmlParserTerminal(File xmlInput) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        List<Terminal> terminalList = new ArrayList<Terminal>();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlInput, handler);
            terminalList = handler.getTerminalList();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terminalList;
    }

    public static List<Transaction> xmlParserTransaction(File xmlInput) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        List<Transaction> transactionList = new ArrayList<Transaction>();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlInput, handler);

            transactionList = handler.getTransactionList();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactionList;
    }


}
