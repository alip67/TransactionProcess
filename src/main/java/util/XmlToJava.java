package util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XmlToJava {
    private static XmlToJava instance = null;

    protected XmlToJava() {

    }

    public static XmlToJava getInstance() {
        if (instance == null) {
            instance = new XmlToJava();
        }
        return instance;
    }

    public List<DepositStructure> getXmlData() throws CheckDurationInDaysException, CheckDepositBalanceException, CheckDepositTypeException {

        List<DepositStructure> lisDeposit = new ArrayList<DepositStructure>();

        DepositType depositType = null;
        try {
            File file = new File("src/file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Deposits.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Deposits depositData = (Deposits) jaxbUnmarshaller.unmarshal(file);
            List<Deposit> list = depositData.getDeposit();
            for (Deposit deposit : list) {
                if (deposit.getDurationInDays() == 0 || deposit.getDurationInDays() < 0) {
                    try {
                        throw new CheckDurationInDaysException("The Duriton In Days Is Not Valid");
                    } catch (CheckDurationInDaysException checkDurationInDaysException) {
                        Writeline.wl("" + checkDurationInDaysException);
                    }
                } else if (deposit.getDepositBalance().compareTo(new BigDecimal(0)) == -1) {

                    try {
                        throw new CheckDepositBalanceException("The Deposit Balance  Is Not Valid");
                    } catch (CheckDepositBalanceException checkDepositBalanceException) {
                        Writeline.wl("" + checkDepositBalanceException);
                    }
                } else if (deposit.getDepositType().equals("ShortTerm") || deposit.getDepositType().equals("LongTerm") || deposit.getDepositType().equals("Qarz")) {
                    DepositStructure depositStructure = new DepositStructure();
                    depositStructure.setCustomerNumber(deposit.getCustomerNumber());
                    depositStructure.setDepositBalance(deposit.getDepositBalance());
                    depositStructure.setDurationInDays(deposit.getDurationInDays());
                    try {
                        Class c = Class.forName("bean." + deposit.depositType);

                        depositType = (DepositType) c.newInstance();

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    depositStructure.setDepositType(depositType);
                    depositStructure.setPayedInterest(new BigDecimal(0));
                    lisDeposit.add(depositStructure);
                } else {
                    try {
                        throw new CheckDepositTypeException("The Deposit Type  Is Not Valid");
                    } catch (CheckDepositTypeException checkDepositTypeException) {
                        Writeline.wl("" + checkDepositTypeException);
                    }
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return lisDeposit;
    }
}
