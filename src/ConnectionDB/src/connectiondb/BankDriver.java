//import connectiondb.ConnectionDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Driver;
import java.util.List;
import java.util.Map;


public class BankDriver
{
    public static int currentUserID;

    public static void main(String[] args) {

        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            currentUserID = 9;

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