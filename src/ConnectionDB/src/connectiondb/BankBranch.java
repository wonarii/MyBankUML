import java.sql.SQLException;

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
