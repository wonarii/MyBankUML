public class Administrator extends BankTeller {

    // When the 'create Bank Teller' account is pressed, this function is called
    public void addBankTeller(String firstName, String lastName, int bankBranch, String email) {
        BankTeller bankTeller = new BankTeller(firstName, lastName, bankBranch, email);
    }

    public void deleteBankTeller() {

    }

    public void createBankBranch() {

    }

    public void deleteBankBranch() {

    }
}
