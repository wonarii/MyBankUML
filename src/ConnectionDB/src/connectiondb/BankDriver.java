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
            currentUser = new Customer("John", "Doe", "joe123@gmail.com", 2, "5141231235", "2002-01-02", 5000.0);

//            Transaction[] transactions = Transaction.convertTransactionsFromDatabase(db, 9);
//
//
//
            JFrame frame = new JFrame("Main Screen");
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


    }
}