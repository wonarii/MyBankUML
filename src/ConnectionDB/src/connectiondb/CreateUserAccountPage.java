import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CreateUserAccountPage extends JFrame {
    private JPanel contentPane;
    // TODO: Add email field
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField dobField;
    JTextField phoneField;
    JTextField branchField;
    JButton createButton;
    JButton cancelButton;

    public CreateUserAccountPage() {
        setContentPane(contentPane);
        setTitle("Create User Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        createButton.setEnabled(false);

        // Document listener that validates all fields in real time
        DocumentListener documentListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validateForm(); }
            @Override public void removeUpdate(DocumentEvent e) { validateForm(); }
            @Override public void changedUpdate(DocumentEvent e) { validateForm(); }

            private void validateForm() {
                boolean firstNameValid = isFieldValid(firstNameField, "^[a-zA-Z]*$");
                boolean lastNameValid = isFieldValid(lastNameField, "^[a-zA-Z]+$");
                boolean bankBranchValid = isFieldValid(branchField, "^[0-9]{3}$");
                // TODO: Add boolean for future email field
                boolean phoneNumberValid = isFieldValid(phoneField, "^[0-9]{10}$");
                boolean dobValid = isDateValid(dobField);

                boolean valid = firstNameValid && lastNameValid && bankBranchValid && phoneNumberValid && dobValid;
                createButton.setEnabled(valid);
            }
        };

        firstNameField.getDocument().addDocumentListener(documentListener);
        lastNameField.getDocument().addDocumentListener(documentListener);
        dobField.getDocument().addDocumentListener(documentListener);
        phoneField.getDocument().addDocumentListener(documentListener);
        branchField.getDocument().addDocumentListener(documentListener);
        // TODO: add document listener for email

        // When the create button is pressed create a new Customer
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Complete when email field is added
                //Customer newCustomer = new Customer(firstNameField.getText(), lastNameField.getText(), )
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                //auth.signUp(newCustomer);
            }
        });
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

    private static boolean isDateValid(JTextField field) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        if (field.getText().isEmpty()) {
            field.setBackground(Color.WHITE);
            return false;
        }
        try {
            Date date = formatter.parse(field.getText());
            field.setBackground(new Color(200, 255, 200));
        } catch (ParseException e) {
            field.setBackground(new Color(255, 200, 200));
            return false;
        }
        return true;
    }
}
