import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

public class ViewAccountInformationPage {
    private JButton editButton;
    private JButton backButton;
    private JPanel accountInformationPanel;
    private JLabel headerNameField;
    private JLabel institutionField;
    private JLabel transitNumberField;
    private JLabel accountNumberField;
    private JLabel firstNameField;
    private JLabel lastNameField;
    private JLabel birthdayField;

    private int shownUserID;

    private DriverScreen driverScreen;
    public ViewAccountInformationPage(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dashboard;
                String currentRole =(String) Authenticator.getAuthenticatorInstance().getCurrentUser().get("user_role");

                System.out.println(currentRole);
                if(Objects.equals(currentRole, "admin")){
                    dashboard = "adminDashboard";
                    System.out.println("Should Be Admin");
                } else if(Objects.equals(currentRole, "teller")){
                    dashboard = "tellerDashboard";
                } else{
                    dashboard = "userDashboard";
                }

                Container parent = accountInformationPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, dashboard);
            }
        });
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
        this.shownUserID = userID;

        Authenticator auth = Authenticator.getAuthenticatorInstance();

        // Set up the Header Name which should be the current Logged in user
        String currentUserName = ((String) auth.getCurrentUser().get("user_first_name")) + " " + ((String) auth.getCurrentUser().get("user_last_name"));
        headerNameField.setText(currentUserName);

        try{
            // Ideally this is a function implemented in the Authenticator or something
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            Map<String, Object> currentUser = db.getUserByID(shownUserID);

            institutionField.setText(String.format("%03d",(int) currentUser.get("user_bank_id")));
            transitNumberField.setText(String.format("%05d",(int) currentUser.get("user_branch_id")));
            accountNumberField.setText(String.format("%07d", (int) currentUser.get("id")));


            firstNameField.setText(currentUser.get("user_first_name").toString());
            lastNameField.setText(currentUser.get("user_last_name").toString());
            birthdayField.setText(currentUser.get("user_birthday").toString());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //Do all the set texts here or something


    }
}
