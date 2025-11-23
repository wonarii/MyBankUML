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
                    amountInput.getText();
                    double amount = Double.parseDouble(amountInput.getText());
                    Transaction.createTransaction(amount, "withdraw");

                } catch(Exception ex){

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
