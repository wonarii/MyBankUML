//import connectiondb.ConnectionDB;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;
import java.util.Map;


public class BankDriver
{

    public static void main(String[] args) {

        try {
            JFrame frame = new JFrame("Sign Up Page");
            SignUpPage signUpPage = new SignUpPage();
            frame.setContentPane(signUpPage.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e){

        }


    }
}
