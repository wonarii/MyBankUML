import java.sql.SQLException;

public class BankBranch {
    private String bankName;
    private String branchName;
    private String address;
    private int branchId;
    private String branchPhone;


    public BankBranch(String bankName, String branchName, String address, int branchId, String branchPhone) {
        this.bankName = bankName;
        this.branchName = branchName;
        this.address = address;
        this.branchId = branchId;
        this.branchPhone = branchPhone;
    }



    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
