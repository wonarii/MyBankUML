import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class CreateTellerAccountPage extends JFrame {
    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField bankBranchField;
    private JTextField emailField;
    private JButton createButton;
    private JButton cancelButton;

    public CreateTellerAccountPage() {
        setContentPane(contentPane);
        setTitle("Create Bank Teller");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Document listener that validates all fields in real time
        DocumentListener documentListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validateForm(); }
            @Override public void removeUpdate(DocumentEvent e) { validateForm(); }
            @Override public void changedUpdate(DocumentEvent e) { validateForm(); }

            private void validateForm() {
                boolean firstNameValid = isFieldValid(firstNameField, "^[a-zA-Z]*$");
                boolean lastNameValid = isFieldValid(lastNameField, "^[a-zA-Z]+$");
                boolean bankBranchValid = isFieldValid(bankBranchField, "^[0-9]{3}$");
                boolean emailValid = isFieldValid(emailField, "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

                boolean valid = firstNameValid && lastNameValid && bankBranchValid && emailValid;
                createButton.setEnabled(valid);
            }
        };

        firstNameField.getDocument().addDocumentListener(documentListener);
        lastNameField.getDocument().addDocumentListener(documentListener);
        bankBranchField.getDocument().addDocumentListener(documentListener);
        emailField.getDocument().addDocumentListener(documentListener);

        // Button to create user
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankTeller bankTeller = new BankTeller(firstNameField.getText(), lastNameField.getText(), emailField.getText(), Integer.parseInt(bankBranchField.getText()));
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                auth.createBankTeller(bankTeller);
            }
        });
    }

    // Validator method
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

}
