import java.util.Date;

public class Transaction {

    private int transactionID;
    private int userID;
    private Date date;
    private String type;
    private Double amount;
    private String description;
    private BankBranch branch;


    /***
     * Transaction Constructor
     * @param transactionID Idk if this is gonna be generated here or in the database
     * @param date Date of the transactions
     * @param type The transaction was either a Deposit or Withdraw
     * @param amount The amount withdrawn/deposited
     * @param description A short possibly optional description of the deposit / withdrawal (Ex: "Birthday Money" or "Water Bill")
     * @param branch The Branch location that the deposit / withdrawal is associated with
     *TODO: Firgure out the TransactionID stuff
     */
    public Transaction(int transactionID, int userID, Date date, String type, Double amount, BankBranch branch, String description) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.branch = branch;

    }

    /***
     * This function should display the information of a Transaction on the GUI?
     * Ideally, we would loop through a list of Transactions to display a series on transactions
     */
    public Object[] display(){
        Object[] obj = new Object[5];
        obj[0] = this.transactionID;
        obj[1] = this.userID;
        obj[2] = this.date;
        obj[3] = this.type;
        obj[4] = this.amount;
        obj[5] = this.description;
        return obj;
    }







// --------------------------  Getters and Setters -------------------------------------
//  Remove any getters and setters as needed
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankBranch getBranch() {
        return branch;
    }

    public void setBranch(BankBranch branch) {
        this.branch = branch;
    }
}