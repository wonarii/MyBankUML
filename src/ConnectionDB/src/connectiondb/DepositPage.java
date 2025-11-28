import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositPage {
    private JTextField amountInput;
    private JButton nextButton;
    private JButton cancelButton;
    private JPanel depositPanel;
    private JLabel currentDateLabel;
    private JLabel balanceField;
    private JLabel headerNameField;

    private DriverScreen driverScreen;

    public DepositPage(DriverScreen driverScreen) {

        this.driverScreen = driverScreen;
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int confirmation = JOptionPane.showConfirmDialog(depositPanel, "Are you sure you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if(confirmation == JOptionPane.YES_OPTION){
//                        amountInput.getText();
                        double amount = Double.parseDouble(amountInput.getText());
                        int status = Transaction.createTransaction(amount, "deposit");

                        if(status == 0){
                            JOptionPane.showMessageDialog(depositPanel, "Deposit was successful!");
                            resetFields();
                            driverScreen.updateUserDashboard();
                            Container parent = depositPanel.getParent();
                            CardLayout layout = (CardLayout) parent.getLayout();
                            layout.show(parent, "userDashboard");
                        } else {

                            JOptionPane.showMessageDialog(depositPanel, "Deposit was unsuccessful, please try again.");
                        }



                    }



                } catch(Exception ex){
                    JOptionPane.showMessageDialog(depositPanel, "An error has occurred. Please try again.");
                }
            }
        });

        //TODO: For the layout, based on the current user account, switch userDashboard to bank teller or something
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                Container parent = depositPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "userDashboard");
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        depositPanel = new JPanel();
        currentDateLabel = new JLabel();
        currentDateLabel.setText((new java.sql.Date(System.currentTimeMillis())).toString());

    }

    public void updateDepositPage(){
        updateUserName();
        updateBalance();
    }

    public void updateBalance(){
        balanceField.setText("$" + Customer.getBalanceFromDatabase());
    }

    public void resetFields() {
        amountInput.setText("0.00");
    }

    public JPanel getPanel() {
        return depositPanel;
    }

    public void updateUserName(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        if (auth.getCurrentUser() != null) {
            headerNameField.setText((String) auth.getCurrentUser().get("user_first_name")  + " " + auth.getCurrentUser().get("user_last_name"));
        } else {
            headerNameField.setText("Friend");
        }

    }

}
