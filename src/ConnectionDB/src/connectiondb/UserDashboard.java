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
    private JLabel balanceField;
    private JLabel userNameField;
    private JLabel headerUserNameField;

    private DriverScreen driverScreen;

    public UserDashboard(DriverScreen driverScreen) {
        updateBalanceField();
        updateUserName();

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
                int userID = (int) Authenticator.getAuthenticatorInstance().getCurrentUser().get("id");
                driverScreen.updateTransactionsTable(userID);
                Container parent = userDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "transactions");
            }
        });
        viewAccountInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userID = (int) Authenticator.getAuthenticatorInstance().getCurrentUser().get("id");
                driverScreen.updateAccountInformationPage(userID);

                Container parent = userDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "accountInformation");
            }
        });
    }

    private void createUIComponents() {
        userDashboardPanel = new JPanel();


    }
    // TODO update with database
    public void updateBalanceField() {
            balanceField.setText("$" + Customer.getBalanceFromDatabase());
    }

    public void updateUserName(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        if (auth.getCurrentUser() != null) {
            userNameField.setText((String) auth.getCurrentUser().get("user_first_name")  + " " + auth.getCurrentUser().get("user_last_name"));
            headerUserNameField.setText((String) auth.getCurrentUser().get("user_first_name")  + " " + auth.getCurrentUser().get("user_last_name"));
        } else {
            userNameField.setText("Friend!");
            headerUserNameField.setText("Friend");
        }

    }

    public JPanel getPanel() {
        return userDashboardPanel;
    }
}
