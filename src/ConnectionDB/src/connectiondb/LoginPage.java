import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField enterYourEmailTextField;
    private JButton loginButton;
    private JPasswordField enterPasswordPasswordField;
    private JPanel loginPane;
    private JButton createAccButton;

    public LoginPage() {
        setContentPane(loginPane);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(200, 200, 550, 400);
        setVisible(true);

        // To open create account page
        createAccButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpPage();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
