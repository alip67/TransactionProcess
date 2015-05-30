package util;

public class JaxbConverter {
    private static JaxbConverter instance = null;

    protected JaxbConverter() {

    }

    public static JaxbConverter getInstance() {
        if (instance == null) {
            instance = new JaxbConverter();
        }
        return instance;
    }

    public void getXmlData()  {

//        List<DepositStructure> lisDeposit = new ArrayList<DepositStructure>();
//
//        DepositType depositType = null;
//        try {
//            File file = new File("src/file.xml");
//            JAXBContext jaxbContext = JAXBContext.newInstance(Deposits.class);
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            Deposits depositData = (Deposits) jaxbUnmarshaller.unmarshal(file);
//            List<Deposit> list = depositData.getDeposit();
//            for (Deposit deposit : list) {
//                DataStructureDeposit dsd = new DataStructureDeposit();
//                dsd.setCustomerNumber(deposit.getCustomerNumber());
//
//                dsd.setDepositBalance(deposit.getDepositBalance());
//
//                dsd.setDurationInDays(deposit.getDurationInDays());
//
//                dsd.setDepositType(deposit.getDepositType());
//
//
//                lisDeposit.add(dsd);
//            }
//
//
//
//
//
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//
//
//        return lisDeposit;
    }

 public void setXMLData(){

 }
}

