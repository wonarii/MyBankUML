//import connectiondb.ConnectionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Driver;
import java.util.List;
import java.util.Map;


public class BankDriver
{
    public static User currentUser;

    public static void main(String[] args) {

        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            currentUser = new Customer("john", "doe", "joe123@gmail.com", 3, "5141231234", "2002-03-02", 3000, "abc123");

//            Transaction[] transactions = Transaction.convertTransactionsFromDatabase(db, 9);
//
//
//
            JFrame frame = new JFrame("MyBank");
            DriverScreen mainScreen = new DriverScreen();


//            ViewTransactionHistoryPage tempPage = new ViewTransactionHistoryPage();
//
//            tempPage.displayTransactions(transactions);
//
            frame.setContentPane(mainScreen.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e){

        }
//        new LoginPage();
    }
}