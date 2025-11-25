import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Bank {
    private String bankName;
    private int bankID;

    public Bank(String bankName, int bankID) {
        this.bankName = bankName;
        this.bankID = bankID;
    }



    @Override
    public String toString() {
        return bankName;
    }

    //TODO: take from banks
    public static Bank[] getAllBanks() {

        try{
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            List<Map<String, Object>> banks = db.getAllBanks();

            int bankAmount = banks.size();
            Bank[] bankArray = new Bank[bankAmount];
            int index = 0;
            for(Map<String, Object> t: banks){

                String bankName = (String) t.get("bank_name");
                int bankID = (int) t.get("bank_id");
                Bank bank = new Bank(bankName, bankID);
                bankArray[index] = bank;
                index++;
            }
            return bankArray;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return new Bank[0];
        }

    }


    // Getters and Setters
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }
}
