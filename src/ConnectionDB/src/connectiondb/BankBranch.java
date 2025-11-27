import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankBranch {
    private int bankId;
    private String branchName;
    private String address;
    private int branchId;
    private String branchPhone;

    public BankBranch(int bankId, String branchName, String address, int branchId, String branchPhone) {
        this.bankId = bankId;
        this.branchName = branchName;
        this.address = address;
        this.branchId = branchId;
        this.branchPhone = branchPhone;
    }

    public static int createBankBranch(String branchName, String address, String branchPhone, int bankID) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            boolean success = db.createBranch(branchName, address,branchPhone ,bankID);

            if (!success) {
                System.err.println("Error creating branch.");
                return -1;
            }
            System.out.println("Successfully created branch.");
            return 0;

        } catch (Exception e) {
            System.err.println("Error creating branch.");
            return -1;
        }
    }

    public static int deleteBankBranch(int branchId) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            boolean success = db.deleteBranch(branchId);
            if (!success) {
                System.err.println("Error deleting branch.");
                return -1;
            }
            System.out.println("Successfully deleted branch.");
            return 0;
        } catch (Exception e) {
            System.err.println("Error deleting branch.");
            return -1;
        }
    }

    public static BankBranch[] getAllBankBranch() {
        try{
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            List<Map<String, Object>> branches = db.getAllBranch();
            int size = branches.size();
            BankBranch[] branchArray = new BankBranch[size];

            int index = 0;
            for (Map<String, Object> map : branches) {
                String branchName = (String) map.get("branch_name");
                String address = (String) map.get("location");
                int branchId = (int) map.get("branch_id");
                String branchPhone = (String) map.get("branch_phone");
                int bankId = (int) map.get("bank_id");

                BankBranch bankBranch = new BankBranch(branchId, branchName, address, branchId, branchPhone);
                branchArray[index] = bankBranch;
                index++;
            }
            return branchArray;
        }
        catch (Exception e){
            System.err.println("Error getting branch list.");
            return new BankBranch[0];
        }
    }

    public static BankBranch[] getAllBankBranchForBankId(int bankId) {
        try{
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            List<Map<String, Object>> branches = db.getAllBranchForBank(bankId);
            int size = branches.size();
            BankBranch[] branchArray = new BankBranch[size];

            int index = 0;
            for (Map<String, Object> map : branches) {
                String branchName = (String) map.get("branch_name");
                String address = (String) map.get("location");
                int branchId = (int) map.get("branch_id");
                String branchPhone = (String) map.get("branch_phone");

                BankBranch bankBranch = new BankBranch(bankId, branchName, address, branchId, branchPhone);
                branchArray[index] = bankBranch;
                index++;
            }
            return branchArray;
        }
        catch (Exception e){

            System.err.println("Error getting branches. ID: " + bankId);
            return new BankBranch[0];
        }
    }

    public Object[] display(){
        Object[] obj = new Object[5];
        obj[0] = this.getBranchId();
        obj[1] = this.getBranchName();
        obj[2] = this.getAddress();
        obj[3] = this.getBranchPhone();
        obj[4] = this.getBankId();

        return obj;

    }



    @Override
    public String toString() {
        return branchName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankName(int bankId) {
        this.bankId = bankId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

}
