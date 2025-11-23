import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.sql.Date;

public class WithdrawPage {
    private JTextField amountInput;
    private JButton nextButton;
    private JButton cancelButton;
    private JPanel withdrawPanel;


    // TODO: We need a description box
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
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        withdrawPanel = new JPanel();

    }

    public JPanel getPanel() {
        return withdrawPanel;
    }




}
