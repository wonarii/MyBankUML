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
                    amountInput.getText();
                    double amount = Double.parseDouble(amountInput.getText());
                    Transaction.createTransaction(amount, "deposit");

                } catch(Exception ex){
                    System.err.println(ex.getMessage());
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
