//import connectiondb.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Driver;
import java.util.List;
import java.util.Map;


public class BankDriver
{

    public static void main(String[] args) {

        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            JFrame frame = new JFrame("MyBank");
            DriverScreen mainScreen = new DriverScreen();

            frame.setContentPane(mainScreen.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(1000,800));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


        } catch (Exception e){
            System.err.println(e);
        }
//        new LoginPage();
    }
}