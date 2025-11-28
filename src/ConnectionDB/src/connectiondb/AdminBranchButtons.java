import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminBranchButtons extends JDialog{
    private JLabel branchIDField;
    private JLabel branchNameField;
    private JLabel locationField;
    private JLabel branchPhoneField;
    private JLabel bankIDField;
    private JButton deleteBankBranchButton;
    private JPanel adminBranchButtonsPanel;


    private DriverScreen driverScreen;
    private JPanel originalScene;

    public AdminBranchButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene) {
        this.driverScreen = driverScreen;
        this.originalScene = originalScene;

        // Set up the popup window
        setTitle("Bank Branch");
        setModal(true);

        // Add Data to the window
        branchIDField.setText(data[0].toString());
        branchNameField.setText(data[1].toString());
        locationField.setText(data[2].toString());
        branchPhoneField.setText(data[3].toString());
        // Bank name is index 4
        bankIDField.setText(data[5].toString());



        deleteBankBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = JOptionPane.showConfirmDialog(adminBranchButtonsPanel, "Are you sure you want to delete this branch?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try{
                        ConnectionDB db = ConnectionDB.getDatabaseInstance();

                        boolean status = db.deleteBranch((int) data[0]);

                        if(status == true){
                            JOptionPane.showMessageDialog(adminBranchButtonsPanel, "The branch was successfully deleted!");
                            driverScreen.updateAdminDashboardPage();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(adminBranchButtonsPanel, "The branch failed to be deleted. Ensure that no user are still with the branch first!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(adminBranchButtonsPanel, "An error occurred while deleting the branch.");
                    }
                }
            }
        });

        add(adminBranchButtonsPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void showBranchButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene){
        AdminBranchButtons newPopup = new AdminBranchButtons(data, driverScreen, originalScene);
    }
}
