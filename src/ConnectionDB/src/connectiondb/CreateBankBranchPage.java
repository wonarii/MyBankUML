import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBankBranchPage {
    private JTextField branchNameInput;
    private JTextField branchLocationInput;
    private JTextField phoneNumberInput;

    private JButton createButton;
    private JButton cancelButton;
    private JPanel createBankBranchPanel;
    private JComboBox bankList;
    private JLabel headerNameField;

    private DriverScreen driverScreen;

    public CreateBankBranchPage(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
        updateBankOptions();

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(createBankBranchPanel, "Are you sure you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);

                if(confirmation == JOptionPane.YES_OPTION){

                    Bank chosenBank = (Bank) bankList.getSelectedItem();
                    int newBankID = 1;
                    if(chosenBank != null){
                        newBankID = chosenBank.getBankID();
                    }
                    String newBankBranchName = branchNameInput.getText();
                    String newBankLocation = branchLocationInput.getText();
                    String newBankPhoneNumber = phoneNumberInput.getText();



                    int status = BankBranch.createBankBranch(newBankBranchName, newBankLocation, newBankPhoneNumber, newBankID);

                    if(status == 0){
                        JOptionPane.showMessageDialog(createBankBranchPanel, "Bank branch creation was successful!");
                        resetFields();

                        driverScreen.updateAdminDashboardPage();

                        Container parent = createBankBranchPanel.getParent();
                        CardLayout layout = (CardLayout) parent.getLayout();
                        layout.show(parent, "adminDashboard");
                    } else {
                        JOptionPane.showMessageDialog(createBankBranchPanel, "Bank branch creation was unsuccessful, please try again.");
                    }



                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                Container parent = createBankBranchPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "adminDashboard");
            }
        });
    }

    private void createUIComponents() {
        createBankBranchPanel = new JPanel();
        bankList = new JComboBox();
        updateBankOptions();
        // TODO: Use the actual banks

    }

    public void updateCreateBankBranchPage(){
        updateBankOptions();
        updateHeaderName();
    }

    public void updateBankOptions(){
        if(bankList.getItemCount() != 0){
            bankList.removeAllItems();
        }
        Bank[] banks = Bank.getAllBanks();

        for (Bank bank : banks) {
            bankList.addItem(bank);
        }
    }


    public JPanel getPanel(){
        return createBankBranchPanel;
    }

    public void resetFields(){
        branchNameInput.setText("");
        branchLocationInput.setText("");
        phoneNumberInput.setText("");
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


}
