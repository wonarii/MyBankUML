import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

public class CreateUserAccountPage extends JFrame {
    private JPanel contentPane;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField dobField;
    JTextField phoneField;
    JComboBox branchField;
    JButton createButton;
    JButton cancelButton;
    private JTextField emailField;
    private JComboBox bankField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JPanel createUserAccountPanel;
    private JLabel headerNameField;

    private DriverScreen driverScreen;

    public CreateUserAccountPage(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
//        setContentPane(contentPane);
//        setTitle("Create User Account");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);
//        createButton.setEnabled(false);


        updateCreateUserAccountPage();

        // Document listener that validates all fields in real time
        DocumentListener documentListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validateForm(); }
            @Override public void removeUpdate(DocumentEvent e) { validateForm(); }
            @Override public void changedUpdate(DocumentEvent e) { validateForm(); }

            private void validateForm() {
                boolean firstNameValid = isFieldValid(firstNameField, "^[a-zA-Z]*$");
                boolean lastNameValid = isFieldValid(lastNameField, "^[a-zA-Z]+$");
//                boolean bankBranchValid = isFieldValid(branchField, "^[0-9]{3}$");
                boolean emailFieldValid = isFieldValid(emailField, "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
                boolean phoneNumberValid = isFieldValid(phoneField, "^[0-9]{10}$");
                boolean dobValid = isDateValid(dobField);
                boolean doPasswordsMatch = doPasswordsMatch(passwordField, confirmPasswordField);

                boolean valid = firstNameValid && lastNameValid && phoneNumberValid && dobValid && emailFieldValid && doPasswordsMatch;
                createButton.setEnabled(valid);
            }
        };

        firstNameField.getDocument().addDocumentListener(documentListener);
        lastNameField.getDocument().addDocumentListener(documentListener);
        dobField.getDocument().addDocumentListener(documentListener);
        phoneField.getDocument().addDocumentListener(documentListener);
//        branchField.getDocument().addDocumentListener(documentListener);
        emailField.getDocument().addDocumentListener(documentListener);
        passwordField.getDocument().addDocumentListener(documentListener);
        confirmPasswordField.getDocument().addDocumentListener(documentListener);

        // When the create button is pressed create a new Customer
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                int status = auth.signUp(firstNameField.getText(), lastNameField.getText(), emailField.getText(), (Bank) bankField.getSelectedItem(), phoneField.getText(), dobField.getText(), passwordField.getText(),(BankBranch) branchField.getSelectedItem());

                if(status == 0){
                    JOptionPane.showMessageDialog(createUserAccountPanel, "Account creation was successful!");
                    resetFields();

                    String dashboard;
                    if(auth.getCurrentUser().get("user_role").toString().equals("admin")){
                        driverScreen.updateAdminDashboardPage();
                        dashboard = "adminDashboard";
                    } else if (auth.getCurrentUser().get("user_role").toString().equals("teller")){
                        driverScreen.updateTellerDashboardPage();
                        dashboard = "tellerDashboard";
                    } else {
                        // This shouldnt ever happen since creating User Account is an admin / teller thing
                        dashboard = "userDashboard";
                    }

                    Container parent = createUserAccountPanel.getParent();
                    CardLayout layout = (CardLayout) parent.getLayout();
                    layout.show(parent, dashboard);
                } else {
                    JOptionPane.showMessageDialog(createUserAccountPanel, "Account creation was unsuccessful, please try again.");
                }
                 // Return to bank teller dashboard
            }
        });
        bankField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBranchOptions();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                resetFields();

                String dashboard;
                if(auth.getCurrentUser().get("user_role").toString().equals("admin")){
                    dashboard = "adminDashboard";
                } else if (auth.getCurrentUser().get("user_role").toString().equals("teller")){
                    dashboard = "tellerDashboard";
                } else {
                    // This shouldnt ever happen since creating User Account is an admin / teller thing
                    dashboard = "userDashboard";
                }

                Container parent = createUserAccountPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, dashboard);
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

    /**
     * This function will call all the updating helper functions to refresh everything
     */
    public void updateCreateUserAccountPage(){
        updateBankOptions();
        updateHeaderName();
    }

    public void updateHeaderName(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        if(auth.getCurrentUser() != null) {
            String currentUserName = ((String) auth.getCurrentUser().get("user_first_name")) + " " + ((String) auth.getCurrentUser().get("user_last_name"));
            headerNameField.setText(currentUserName);
        } else {
            headerNameField.setText("");
        }
    }


    public void updateBankOptions(){
        if(bankField.getItemCount() != 0){
            bankField.removeAllItems();
        }
        Bank[] banks = Bank.getAllBanks();

        for (Bank bank : banks) {
            bankField.addItem(bank);
        }
        updateBranchOptions();
    }

    public void updateBranchOptions(){
        if(branchField.getItemCount() != 0){
            branchField.removeAllItems();
        }
        Bank selectedBank = (Bank) bankField.getSelectedItem();
        if(selectedBank != null){
            BankBranch[] branches = BankBranch.getAllBankBranchForBankId(selectedBank.getBankID());

            for (BankBranch branch : branches) {
                branchField.addItem(branch);
            }
        }

    }

    public void resetFields(){
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        phoneField.setText("");
        dobField.setText("");
    }

    public JPanel getPanel()
    {
        return createUserAccountPanel;
    }
}


