import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        emailField.setText(data[1].toString());
        firstNameField.setText(data[2].toString());
        lastNameField.setText(data[3].toString());
        bankField.setText(data[4].toString());
        branchField.setText(data[5].toString());
        balanceField.setText(data[6].toString());


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

        add(adminUserButtonsPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void showUserButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene){
        AdminUserButtons newPopup = new AdminUserButtons(data, driverScreen, originalScene);
    }


}


