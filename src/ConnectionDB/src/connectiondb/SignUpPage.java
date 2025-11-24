import javax.swing.*;
<<<<<<< HEAD

public class CreateTellerAccountPage {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton createButton;
    private JButton cancelButton;

    private void createUIComponents() {

    }
}
=======
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpPage extends JFrame {
    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField passwordVerifField;
    private JButton signUpButton;
    private JButton backButton;
    private JTextField phoneField;
    private JFormattedTextField dobField;
    private JTextField branchField;

    public SignUpPage() {
        setContentPane(contentPane);
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        signUpButton.setEnabled(false);

        // Return to login page when the back button is pressed
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage();
                dispose();
            }
        });

        // Live validation for dates
        dobField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateDate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateDate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateDate();
            }

            private void validateDate() {
                signUpButton.setEnabled(false);
                String text = dobField.getText().trim();

                if (text.isEmpty()) {
                    dobField.setBackground(Color.WHITE);
                    return;
                }

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                df.setLenient(false);

                try {
                    Date date = df.parse(text);
                    dobField.setBackground(new Color(200, 255, 200));  // green
                    signUpButton.setEnabled(true);
                } catch (ParseException ex) {
                    dobField.setBackground(new Color(255, 200, 200)); // red
                    signUpButton.setEnabled(false);
                }
            }
        });

        // Document listener to do input validation on these fields in real time
        firstNameField.getDocument().addDocumentListener(new FieldDocumentListener(signUpButton, firstNameField, lastNameField, emailField, phoneField, dobField, branchField));
        lastNameField.getDocument().addDocumentListener(new FieldDocumentListener(signUpButton, firstNameField, lastNameField, emailField, phoneField, dobField, branchField));
        emailField.getDocument().addDocumentListener(new FieldDocumentListener(signUpButton, firstNameField, lastNameField, emailField, phoneField, dobField, branchField));
        phoneField.getDocument().addDocumentListener(new FieldDocumentListener(signUpButton, firstNameField, lastNameField, emailField, phoneField, dobField, branchField));
        dobField.getDocument().addDocumentListener(new FieldDocumentListener(signUpButton, firstNameField, lastNameField, emailField, phoneField, dobField, branchField));
        branchField.getDocument().addDocumentListener(new FieldDocumentListener(signUpButton, firstNameField, lastNameField, emailField, phoneField, dobField, branchField));

        // Button to sign up - creates a new Customer (in the database)
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //If all fields are validated, add it to the database
                //customer.createCustomerAccount();

                // Bring user back to login page so that now they can login with the account they just created.
                new LoginPage();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}

class FieldDocumentListener implements DocumentListener {
    private JButton button;
    private JTextField[] fields;

    public FieldDocumentListener(JButton button, JTextField... fields) {
        this.button = button;
        this.fields = fields;
    }

    private void checkFields() {
        boolean allFilled = true;

        // Verify they are all filled
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                allFilled = false;
                break;
            }
        }

        button.setEnabled(allFilled);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        checkFields();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        checkFields();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        checkFields();
    }
}

>>>>>>> 21e527d9cdc7351ed2bedfe2df3e066695c4e290
