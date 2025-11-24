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


    public DepositPage() {

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int confirmation = JOptionPane.showConfirmDialog(depositPanel, "Are you sure you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if(confirmation == JOptionPane.YES_OPTION){
                        amountInput.getText();
                        double amount = Double.parseDouble(amountInput.getText());
                        int status = Transaction.createTransaction(amount, "deposit");

                        if(status == 0){
                            JOptionPane.showMessageDialog(depositPanel, "Deposit was successful!");

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

    public JPanel getPanel() {
        return depositPanel;
    }

}
