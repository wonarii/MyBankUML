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
    private JTextField emailField;
    private JButton createButton;
    private JButton cancelButton;
    private JPanel createTellerAccountPanel;
    private JLabel headerNameField;
    private JComboBox branchField;
    private JComboBox bankField;

    private DriverScreen driverScreen;

    public CreateTellerAccountPage(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
//        setContentPane(contentPane);
//        setTitle("Create Bank Teller");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);

        // Document listener that validates all fields in real time
        DocumentListener documentListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validateForm(); }
            @Override public void removeUpdate(DocumentEvent e) { validateForm(); }
            @Override public void changedUpdate(DocumentEvent e) { validateForm(); }

            private void validateForm() {
                boolean firstNameValid = isFieldValid(firstNameField, "^[a-zA-Z]*$");
                boolean lastNameValid = isFieldValid(lastNameField, "^[a-zA-Z]+$");
                boolean emailValid = isFieldValid(emailField, "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

                boolean valid = firstNameValid && lastNameValid && emailValid;
                createButton.setEnabled(valid);
            }
        };

        firstNameField.getDocument().addDocumentListener(documentListener);
        lastNameField.getDocument().addDocumentListener(documentListener);
        emailField.getDocument().addDocumentListener(documentListener);

        bankField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBranchOptions();
            }
        });

        // Button to create user
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Should prob have this somehwere else
                String tempPassword = firstNameField.getText() + "123";

                BankTeller bankTeller = new BankTeller(firstNameField.getText(), lastNameField.getText(), emailField.getText(), (BankBranch) branchField.getSelectedItem(), (Bank) bankField.getSelectedItem(), tempPassword);
                Authenticator auth = Authenticator.getAuthenticatorInstance();
                int status = auth.createBankTeller(bankTeller);

                if(status == 0){
                    JOptionPane.showMessageDialog(createTellerAccountPanel, "Teller account creation was successful!");
                    resetFields();

                    driverScreen.updateAdminDashboardPage();

                    Container parent = createTellerAccountPanel.getParent();
                    CardLayout layout = (CardLayout) parent.getLayout();
                    layout.show(parent, "adminDashboard");
                } else {
                    JOptionPane.showMessageDialog(createTellerAccountPanel, "Teller account creation was unsuccessful, please try again.");
                }



            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                Container parent = createTellerAccountPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "adminDashboard");
            }
        });
    }

    public void updateCreateTellerAccountPage() {
        updateBankOptions();
        updateHeaderName();
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

    public JPanel getPanel() {
        return createTellerAccountPanel;
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

    public void updateHeaderName(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        if(auth.getCurrentUser() != null) {
            String currentUserName = ((String) auth.getCurrentUser().get("user_first_name")) + " " + ((String) auth.getCurrentUser().get("user_last_name"));
            headerNameField.setText(currentUserName);
        } else {
            headerNameField.setText("");
        }
    }

    public void resetFields(){
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");

    }

}
