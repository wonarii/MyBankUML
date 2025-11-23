public class BankBranch {
    private String bankName;
    private String address;
    private int branchId;
    private String branchPhone;


    public BankBranch(String bankName, String address, int branchId, String branchPhone) {
        this.bankName = bankName;
        this.address = address;
        this.branchId = branchId;
        this.branchPhone = branchPhone;
    }

    public String getBank() {
        return bankName;
    }

    public void setBank(String bankName) {
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
}
