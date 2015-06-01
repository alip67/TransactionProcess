package util;

import bean.Terminal;
import bean.Transaction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlParserSAX {

        public static SaxHandler xmlParserSAX(InputStream xmlInput) {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SaxHandler handler;
            handler = null;
            try {
                SAXParser saxParser = saxParserFactory.newSAXParser();
                handler = new SaxHandler();
                saxParser.parse(xmlInput, handler);
                //Get Employees list
                List<Terminal> terminalList = handler.getTerminalList();
                List<Transaction> transactionList = handler.getTransactionList();
                //print employee information
                for (Terminal emp : terminalList)
                    System.out.println(emp);
                for (Transaction transaction : transactionList)
                    System.out.println(transaction);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return handler;
        }




}
