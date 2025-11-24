import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBankBranchPage {
    private JTextField branchNameInput;
    private JTextField branchLocationInput;
    private JTextField branchNumberInput;
    private JTextField phoneNumberInput;

    private JButton createButton;
    private JButton cancelButton;
    private JPanel createBankBranchPanel;
    private JComboBox bankList;

    public CreateBankBranchPage() {


        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(createBankBranchPanel, "Are you sure you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);

                if(confirmation == JOptionPane.YES_OPTION){

                    String newBankName = bankList.getSelectedItem().toString();
                    String newBankBranchName = branchNameInput.getText();
                    String newBankLocation = branchLocationInput.getText();
                    String newBranchNumber = branchNumberInput.getText();
                    String newBankPhoneNumber = phoneNumberInput.getText();


//
//                    double amount = Double.parseDouble(amountInput.getText());
//                    int status = Transaction.createTransaction(amount, "deposit");
//
//                    if(status == 0){
//                        JOptionPane.showMessageDialog(depositPanel, "Deposit was successful!");
//
//                        Container parent = depositPanel.getParent();
//                        CardLayout layout = (CardLayout) parent.getLayout();
//                        layout.show(parent, "userDashboard");
//                    } else {
//                        JOptionPane.showMessageDialog(depositPanel, "Deposit was unsuccessful, please try again.");
//                    }



                }
            }
        });
    }

    private void createUIComponents() {
        createBankBranchPanel = new JPanel();
        // TODO: Use the actual banks
        String[] banks = {"Desjardins", "RBC", "BMO", "CIBC"};
        bankList = new JComboBox(banks);
    }


    public JPanel getPanel(){
        return createBankBranchPanel;
    }




}
