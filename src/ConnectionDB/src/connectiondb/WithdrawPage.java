import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.sql.Date;

public class WithdrawPage {
    private JTextField amountInput;
    private JButton nextButton;
    private JButton cancelButton;
    private JPanel withdrawPanel;
    private JLabel currentDateLabel;


    public WithdrawPage() {

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
                Container parent = withdrawPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "userDashboard");
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        withdrawPanel = new JPanel();
        currentDateLabel = new JLabel();
        currentDateLabel.setText((new java.sql.Date(System.currentTimeMillis())).toString());

    }

    public JPanel getPanel() {
        return withdrawPanel;
    }




}
