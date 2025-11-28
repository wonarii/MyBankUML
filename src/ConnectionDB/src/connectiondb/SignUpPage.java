import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

public class SignUpPage extends JFrame {
    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField passwordField2;
    private JButton signUpButton;
    private JButton backButton;
    private JTextField phoneField;
    private JFormattedTextField dobField;
    private JComboBox bankDropDown;
    private JComboBox branchField;

    public SignUpPage() {
        updateBankOptions();
        updateBranchOptions();

        // Return to login page when the back button is pressed
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                Container parent = contentPane.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "login");
            }
        });

        // Remove placeholder for DOB field when user writes in it
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dobField.getText().equals("YYYY-MM-DD")) {
                    dobField.setText("");
                    dobField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dobField.getText().isEmpty()) {
                    dobField.setText("YYYY-MM-DD");
                    dobField.setForeground(Color.GRAY);
                    dobField.setBackground(Color.WHITE);
                }
            }
        };

        dobField.addFocusListener(focusListener);

        // Live validation for all fields
        DocumentListener documentListener = new DocumentListener() {
            private void validateForm() {
                boolean firstNameValid = isFieldValid(firstNameField, "^[a-zA-Z]*$");
                boolean lastNameValid = isFieldValid(lastNameField, "^[a-zA-Z]*$");
                boolean emailValid = isFieldValid(emailField, "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
                boolean phoneValid = isFieldValid(phoneField, "^[0-9]{10}$");
                boolean dobValid = isDateValid(dobField);
                boolean doPasswordsMatch = doPasswordsMatch(passwordField, passwordField2);

                boolean valid = firstNameValid && lastNameValid && emailValid && dobValid && phoneValid && doPasswordsMatch;
                signUpButton.setEnabled(valid);
            }
            @Override public void insertUpdate(DocumentEvent e) { validateForm(); }
            @Override public void removeUpdate(DocumentEvent e) { validateForm(); }
            @Override public void changedUpdate(DocumentEvent e) { validateForm(); }
        };

        firstNameField.getDocument().addDocumentListener(documentListener);
        lastNameField.getDocument().addDocumentListener(documentListener);
        emailField.getDocument().addDocumentListener(documentListener);
        dobField.getDocument().addDocumentListener(documentListener);
        phoneField.getDocument().addDocumentListener(documentListener);
        passwordField.getDocument().addDocumentListener(documentListener);
        passwordField2.getDocument().addDocumentListener(documentListener);

        // Button to sign up - creates a new Customer (in the database)
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call Authenticator class to write to db
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                int status = auth.signUp(firstNameField.getText(), lastNameField.getText(), emailField.getText(), (Bank) bankDropDown.getSelectedItem(), phoneField.getText(), dobField.getText(), passwordField.getText(), (BankBranch)  branchField.getSelectedItem());
                if(status == 0 && verifyAge()) {
                    JOptionPane.showMessageDialog(contentPane, "Sign up was successful!");
                    resetFields();

                    Container parent = contentPane.getParent();
                    CardLayout layout = (CardLayout) parent.getLayout();
                    layout.show(parent, "login");
                } else if (!verifyAge()) {
                    JOptionPane.showMessageDialog(contentPane, "You must be over 18 years of age to sign up. Please ask for help from a parent.");
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Sign up was unsuccessful, please try again.");
                }

            }
        });
        bankDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBranchOptions();
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

    private static boolean doPasswordsMatch(JPasswordField passwordField, JPasswordField passwordField2) {
        char[] password = passwordField.getPassword();
        char[] password2 = passwordField2.getPassword();

        if (Arrays.equals(password, password2) && password.length > 0 && password2.length > 0) {
            return true;
        } return false;
    }

    public JPanel getPanel() {
        return contentPane;
    }

    public void resetFields(){
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dobField.setText("");
        passwordField.setText("");
        passwordField2.setText("");
    }


    public void updateBankOptions(){
        if(bankDropDown.getItemCount() != 0){
            bankDropDown.removeAllItems();
        }
        Bank[] banks = Bank.getAllBanks();

        for (Bank bank : banks) {
            bankDropDown.addItem(bank);
        }
        updateBranchOptions();
    }

    public void updateBranchOptions(){
        if(branchField.getItemCount() != 0){
            branchField.removeAllItems();
        }
        Bank selectedBank = (Bank) bankDropDown.getSelectedItem();
        BankBranch[] branches = BankBranch.getAllBankBranchForBankId(selectedBank.getBankID());

        for (BankBranch branch : branches) {
            branchField.addItem(branch);
        }
    }

    public boolean verifyAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(dobField.getText(), formatter);
        if (Period.between(dob, LocalDate.now()).getYears() > 18) {
            return true;
        }
        return false;
    }

//    public static void main(String[] args) {
//        new SignUpPage();
//    }
}

