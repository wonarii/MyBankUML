import javax.swing.*;

public class ViewAccountInformationPage {
    private JButton editButton;
    private JButton backButton;
    private JPanel accountInformationPanel;

    private DriverScreen driverScreen;
    public ViewAccountInformationPage(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
    }

    private void  createUIComponents() {
    }
    public JButton getEditButton() {
        return editButton;
    }

    public JPanel getPanel() {
        return accountInformationPanel;
    }

    /**
     * This function will be called outside, and will update the page based on the given ID
     * @param userID
     * @return
     */
    public void updateAccountInformationPage(int userID) {
        //Do all the set texts here or something


    }
}
