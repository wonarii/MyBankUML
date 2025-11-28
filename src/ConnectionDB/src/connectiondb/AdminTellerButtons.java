import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminTellerButtons extends JDialog {
    private JLabel userIDField;
    private JLabel firstNameField;
    private JLabel lastNameField;
    private JLabel bankField;
    private JLabel branchField;
    private JLabel emailField;
    private JButton deleteTellerAccountButton;
    private JPanel adminTellerButtonsPanel;
    private JButton viewAccountInformationButton;

    private DriverScreen driverScreen;
    private JPanel originalScene;

    public AdminTellerButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene) {
        this.driverScreen = driverScreen;
        this.originalScene = originalScene;

        // Set up the popup window
        setTitle("Teller");
        setModal(true);

        // Add Data to the window
        userIDField.setText(data[0].toString());
        // BankBranchID is currently using data[1]
        emailField.setText(data[2].toString());
        firstNameField.setText(data[3].toString());
        lastNameField.setText(data[4].toString());
        bankField.setText(data[5].toString());
        branchField.setText(data[6].toString());

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

        deleteTellerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(adminTellerButtonsPanel, "Are you sure you want to delete this account?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try{
                        ConnectionDB db = ConnectionDB.getDatabaseInstance();
                        boolean status = db.deleteUser(data[2].toString());

                        if(status == true){
                            JOptionPane.showMessageDialog(adminTellerButtonsPanel, "Account was successfully deleted!");
                            driverScreen.updateAdminDashboardPage();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(adminTellerButtonsPanel, "Account failed to be deleted!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(adminTellerButtonsPanel, "An error occurred while deleting the account.");
                    }
                }

            }
        });


        add(adminTellerButtonsPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void showTellerButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene){
        AdminTellerButtons newPopup = new AdminTellerButtons(data, driverScreen, originalScene);
    }


}
