public class BankBranch {
    private String bankName;
    private String address;
    private int branchId;
    private int branchPhone;


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

    public int getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(int branchPhone) {
        this.branchPhone = branchPhone;
    }
}
