import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminTellerButtons extends JDialog {
    private JLabel userIDField;
    private JLabel firstNameField;
    private JLabel lastNameField;
    private JLabel bankField;
    private JLabel branchField;
    private JLabel emailField;
    private JButton deleteUserAccountButton;
    private JPanel adminTellerButtonsPanel;

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
        emailField.setText(data[1].toString());
        firstNameField.setText(data[2].toString());
        lastNameField.setText(data[3].toString());
        bankField.setText(data[4].toString());
        branchField.setText(data[5].toString());


        add(adminTellerButtonsPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void showTellerButtons(Object[] data, DriverScreen driverScreen, JPanel originalScene){
        AdminTellerButtons newPopup = new AdminTellerButtons(data, driverScreen, originalScene);
    }


}
