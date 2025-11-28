import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminUserButtons extends JDialog {
    private JButton viewTransactionHistoryButton;
    private JButton viewAccountInformationButton;
    private JButton deleteUserAccountButton;
    private JLabel firstNameField;
    private JLabel emailField;
    private JLabel userIDField;
    private JPanel adminUserButtonsPanel;
    private JLabel lastNameField;
    private JLabel bankField;
    private JLabel branchField;
    private JLabel balanceField;

    private JPanel originalScene;
    private DriverScreen driverScreen;

    public AdminUserButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene) {
        this.driverScreen = driverScreen;
        this.originalScene = originalScene;

        // Set up the popup window
        setTitle("User");
        setModal(true);

        // Add Data to the window
        userIDField.setText(data[0].toString());
        // WE DONT USE data[1] here
        emailField.setText(data[2].toString());
        firstNameField.setText(data[3].toString());
        lastNameField.setText(data[4].toString());
        bankField.setText(data[5].toString());
        branchField.setText(data[6].toString());
        balanceField.setText(data[7].toString());


        viewTransactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateTransactionsTable((int) data[0]);
                dispose();
                Container parent = originalScene.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "transactions");

            }
        });


        viewAccountInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateAccountInformationPage((int) data[0]);
                dispose();
                Container parent = originalScene.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "accountInformation");
            }
        });


        deleteUserAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(adminUserButtonsPanel, "Are you sure you want to delete this account?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try{
                        ConnectionDB db = ConnectionDB.getDatabaseInstance();
                        boolean status = db.deleteUser(data[2].toString());

                        if(status == true){
                            JOptionPane.showMessageDialog(adminUserButtonsPanel, "Account was successfully deleted!");

                            Authenticator auth = Authenticator.getAuthenticatorInstance();
                            if(auth.getCurrentUser().get("user_role").toString().equals("admin")){
                                driverScreen.updateAdminDashboardPage();
                            } else if (auth.getCurrentUser().get("user_role").toString().equals("teller")){
                                driverScreen.updateTellerDashboardPage();
                            }
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(adminUserButtonsPanel, "Account failed to be deleted!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(adminUserButtonsPanel, "An error occurred while deleting the account.");
                    }
                }

            }
        });

        add(adminUserButtonsPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void showUserButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene){
        AdminUserButtons newPopup = new AdminUserButtons(data, driverScreen, originalScene);
    }


}


