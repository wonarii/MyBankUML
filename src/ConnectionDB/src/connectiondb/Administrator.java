public class Administrator extends BankTeller {

    //Constructor
    public Administrator() {}
    // When the 'create Bank Teller' account is pressed, this function is called
    public void addBankTeller(String firstName, String lastName, String email, int accountId, int branch) {
        BankTeller bankTeller = new BankTeller(firstName, lastName, email, accountId, branch);
    }

    public void deleteBankTeller() {

    }

    public void createBankBranch() {

    }

    public void deleteBankBranch() {

    }
}
