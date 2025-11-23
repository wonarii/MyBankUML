import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Transaction {

    private int transactionID;
    private int userID;
    private java.sql.Date date;
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
    public Transaction(int transactionID, int userID, java.sql.Date date, String type, Double amount, BankBranch branch, String description) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.branch = branch;

    }

    /***
     * This function should display the information of a Transaction on the GUI
     * Ideally, we would loop through a list of Transactions to display a series on transactions
     */
    public Object[] display(){
        Object[] obj = new Object[6];
        obj[0] = this.transactionID;
        obj[1] = this.userID;
        obj[2] = this.date;
        obj[3] = this.type;
        obj[4] = this.amount;
        obj[5] = this.description;
        return obj;
    }


    /***
     * This is a static function that will parse through the transactions list of a user in the database
     * and return a Transaction[]
     * @param db
     * @param userID
     * @return Transaction[] a list of Transaction objects
     */
    public static Transaction[] convertTransactionsFromDatabase(ConnectionDB db, int userID) {
        List<Map<String, Object>> transactions = db.loadTransactions(userID);

        Transaction[] transaction = new Transaction[transactions.size()];

        // Use this index to place the Transaction into the array
        int index = 0;
        for (Map<String, Object> t : transactions) {
            // Parse all the fields from the database to a Transaction Object
            int newTransactionID = (int) t.get("transaction_id");
            int newUserID = userID;
//            java.sql.Date date = java.sql.Date.valueOf((String)t.get("date"));
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            String type = (String) t.get("type");
            Double amount = (Double) t.get("amount");

//            BankBranch branch = (BankBranch) t.get("branch");
            BankBranch branch = new BankBranch("TD", "123 Sesame Street", 100,"5141234567");
//            String description = (String) t.get("description");
            String description = "Testing Description";

            // Create a new entry and add it to the array
            Transaction newEntry = new Transaction(newTransactionID, newUserID, date, type, amount, branch, description);
            transaction[index] = newEntry;
            index++;
        }
        return transaction;
    }


    public static int createTransaction(double amount, String type, String description) {

        try{
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            //TODO: Get Email from user
            String tempEmail = "ABC@gmail.com";

            db.updateBalance(tempEmail, type, amount);



        } catch (Exception e){

        }


        return 0;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", userID=" + userID +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", branch=" + branch +
                '}';
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

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
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