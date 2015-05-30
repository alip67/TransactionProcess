package util;


import bean.Deposit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

public class JsonToJava {

    private HashMap<String, Deposit> deposits;
    private int port;
    private String outLog;
    private static JsonToJava instance = null;

    protected JsonToJava() {

    }

    public static JsonToJava getInstance() {
        if (instance == null) {
            instance = new JsonToJava();
        }
        return instance;
    }

    public void getJsonData() {
        String depositCustomer;
        String depositId;
        BigDecimal depositInitialBalance;
        BigDecimal depositUpperBound;
        try {
            deposits = new HashMap<String, Deposit>();
            FileReader fileReader = new FileReader("src\\main\\resources\\core.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            port = Integer.parseInt(jsonObject.get("port").toString());
            outLog = jsonObject.get("outLog").toString();

            JSONArray jsonDepositArray = (JSONArray) jsonObject.get("deposits");
            Iterator<JSONObject> jsonDepositArrayIterator = jsonDepositArray.iterator();

            while (jsonDepositArrayIterator.hasNext()) {
                JSONObject jsonDepositObject = jsonDepositArrayIterator.next();

                depositCustomer = jsonDepositObject.get("customer").toString();
                depositId = jsonDepositObject.get("id").toString();
                depositInitialBalance = new BigDecimal(jsonDepositObject.get("initialBalance").toString());
                depositUpperBound = new BigDecimal(jsonDepositObject.get("upperBound").toString());

                deposits.put(depositId, new Deposit(depositCustomer
                        , depositId
                        , depositInitialBalance
                        , depositUpperBound));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public int getPort() {
        return port;
    }

    public String getOutLog() {
        return outLog;
    }

    public HashMap<String, Deposit> getDepositArrayList() {
        return deposits;
    }


}