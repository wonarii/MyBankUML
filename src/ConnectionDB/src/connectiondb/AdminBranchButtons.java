import javax.swing.*;

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
        bankIDField.setText(data[4].toString());


        add(adminBranchButtonsPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void showBranchButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene){
        AdminBranchButtons newPopup = new AdminBranchButtons(data, driverScreen, originalScene);
    }
}
