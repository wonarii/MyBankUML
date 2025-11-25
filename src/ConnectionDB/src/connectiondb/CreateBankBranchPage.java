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

    public CreateBankBranchPage() {
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

                        Container parent = createBankBranchPanel.getParent();
                        CardLayout layout = (CardLayout) parent.getLayout();
                        layout.show(parent, "userDashboard");
                    } else {
                        JOptionPane.showMessageDialog(createBankBranchPanel, "Bank branch creation was unsuccessful, please try again.");
                    }



                }
            }
        });
    }

    private void createUIComponents() {
        createBankBranchPanel = new JPanel();
        bankList = new JComboBox();
        updateBankOptions();
        // TODO: Use the actual banks

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




}
