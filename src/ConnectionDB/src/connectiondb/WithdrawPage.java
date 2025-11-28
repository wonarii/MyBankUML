import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DecimalFormat;

public class WithdrawPage {
    private JFormattedTextField amountInput;
    private JButton nextButton;
    private JButton cancelButton;
    private JPanel withdrawPanel;
    private JLabel currentDateLabel;
    private JLabel balanceField;
    private JLabel headerNameField;


    private DriverScreen driverScreen;

    public WithdrawPage(DriverScreen driverScreen) {

        this.driverScreen = driverScreen;
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int confirmation = JOptionPane.showConfirmDialog(withdrawPanel, "Are you sure you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if(confirmation == JOptionPane.YES_OPTION){

                        amountInput.getText();
                        double amount = Double.parseDouble(amountInput.getText());


                        int status = Transaction.createTransaction(amount, "withdraw");

                        if(status == 0){
                            JOptionPane.showMessageDialog(withdrawPanel, "Withdrawal was successful!");
                            resetFields();
                            driverScreen.updateUserDashboard();
                            Container parent = withdrawPanel.getParent();
                            CardLayout layout = (CardLayout) parent.getLayout();
                            layout.show(parent, "userDashboard");
                        } else{
                            JOptionPane.showMessageDialog(withdrawPanel, "Withdrawal was unsuccessful, please try again.");
                        }


                    }



                } catch(Exception ex){
                    JOptionPane.showMessageDialog(withdrawPanel, "An error has occurred. Please try again.");
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
                Container parent = withdrawPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "userDashboard");
            }
        });
    }


    public void updateWithdrawPage(){
        updateUserName();
        updateBalance();

    }

    public void updateBalance(){
        balanceField.setText("$" + Customer.getBalanceFromDatabase());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        withdrawPanel = new JPanel();
        currentDateLabel = new JLabel();
        currentDateLabel.setText((new java.sql.Date(System.currentTimeMillis())).toString());

        DecimalFormat df = new DecimalFormat("0.00");
        NumberFormatter formatter = new NumberFormatter(df);
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.00);

        amountInput = new JFormattedTextField(formatter);

    }

    public JPanel getPanel() {
        return withdrawPanel;
    }

    public void resetFields() {
        amountInput.setText("0.00");
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
