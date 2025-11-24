import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboard {
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton viewTransactionHistoryButton;
    private JButton viewAccountInformationButton;
    private JButton logOutButton;
    private JPanel userDashboardPanel;

    private DriverScreen driverScreen;

    public UserDashboard(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = userDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();

                layout.show(parent, "withdraw");
            }
        });
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = userDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "deposit");
            }
        });
        viewTransactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateTransactionsTable();
                Container parent = userDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "transactions");
            }
        });
    }

    private void createUIComponents() {
        userDashboardPanel = new JPanel();


    }

    public JPanel getPanel() {
        return userDashboardPanel;
    }
}
