import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class LoginPage extends JFrame {
    private JPanel contentPane;
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
        loginButton.setEnabled(false);

        // When login button is clicked
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                boolean validCredentials = auth.validateCredentials(enterYourEmailTextField.getText(), enterPasswordPasswordField.getText());
                if (validCredentials) {
                    System.out.println(auth.getCurrentUser().get("user_role"));
                    // Check what type of role in BankDriver?
                }
            }
        });

        // To open create account page
        createAccButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpPage();
                dispose();
            }
        });

        // Live validation for email field
        DocumentListener documentListener = new DocumentListener() {
            private void validateForm() {
                boolean emailValid = isFieldValid(enterYourEmailTextField, "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

                boolean valid = emailValid;
                loginButton.setEnabled(valid);
            }

            @Override public void insertUpdate(DocumentEvent e) { validateForm(); }
            @Override public void removeUpdate(DocumentEvent e) { validateForm(); }
            @Override public void changedUpdate(DocumentEvent e) { validateForm(); }
        };

        enterYourEmailTextField.getDocument().addDocumentListener(documentListener);
    }


    // Validator methods
    private static boolean isFieldValid(JTextField field, String regexPattern) {
        Pattern pattern = Pattern.compile(regexPattern);
        boolean valid = pattern.matcher(field.getText()).matches();
        if (field.getText().isEmpty()) {
            field.setBackground(Color.WHITE);
        } else if (valid) {
            field.setBackground(new Color(200, 255, 200));
        } else {
            field.setBackground(new Color(255, 200, 200));
        }
        return valid;
    }

    public static void main(String[] args) {
        new LoginPage();
    }

    public JPanel getPanel() {
        return contentPane;
    }
}
