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

    private DriverScreen driverScreen;
    public LoginPage(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;

        loginButton.setEnabled(false);

        // When login button is clicked
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                boolean validCredentials = auth.validateCredentials(enterYourEmailTextField.getText(), enterPasswordPasswordField.getText());
                if (validCredentials) {
                    String userRole = (String) auth.getCurrentUser().get("user_role");
                    System.out.println(userRole);

                    // Check what type of role in BankDriver?
                    Container parent = loginPane.getParent();
                    CardLayout layout = (CardLayout) parent.getLayout();

                    if(userRole.equals("admin")){
                        driverScreen.updateAdminDashboardPage();
                        layout.show(parent, "adminDashboard");
                    } else if(userRole.equals("user")){
                        driverScreen.updateUserDashboard();
                        layout.show(parent, "userDashboard");
                    } else if(userRole.equals("teller")){
                        driverScreen.updateTellerDashboardPage();
                        layout.show(parent, "tellerDashboard");
                    }

                }
            }
        });

        // To open create account page
        createAccButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                // Get information from the parent panel (ScreenDriver) and switch the layout signup page
                Container parent = loginPane.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "signup");
//                new SignUpPage();
//                dispose();
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

    public JPanel getPanel() {
        return loginPane;
    }

//    public JPanel getPanel() {
//        return contentPane;
//    }
    public void resetFields() {
        enterYourEmailTextField.setText("");
        enterPasswordPasswordField.setText("");
    }


//    public static void main(String[] args) {
//        new LoginPage();
//    }
}
