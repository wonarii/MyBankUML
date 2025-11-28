import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
    private JPanel phonePanel;
    private JLabel phoneField;
    private JButton editFirstName;
    private JButton editLastName;
    private JButton editBirthday;
    private JButton editPhone;

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

        //When these buttons are clicked a JDialog is opened to edit the field
        editFirstName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Enter new first name");
                if (userInput != null) {
                    updateCustomerFields(userInput, "user_first_name");
                    updateAccountInformationPage(shownUserID);
                }
            }
        });
        editLastName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Enter new last name");
                if (userInput != null) {
                    updateCustomerFields(userInput, "user_last_name");
                    updateAccountInformationPage(shownUserID);
                }
            }
        });
        editBirthday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Enter new date of birth");
                if (userInput != null) {
                    updateCustomerFields(userInput, "user_birthday");
                    updateAccountInformationPage(shownUserID);
                }
            }
        });
        editPhone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = JOptionPane.showInputDialog("Enter new phone number");
                if (userInput != null) {
                    updateCustomerFields(userInput, "user_phone_number");
                }
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
    // Update information in the database
    public void updateCustomerFields(String userInput, String fieldName) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();

            db.updateWithUserId(userInput, fieldName, shownUserID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            Map<String, Object> currentUserShown = db.getUserByID(shownUserID);

            institutionField.setText(String.format("%03d",(int) currentUserShown.get("user_bank_id")));
            transitNumberField.setText(String.format("%05d",(int) currentUserShown.get("user_branch_id")));
            accountNumberField.setText(String.format("%07d", (int) currentUserShown.get("id")));


            firstNameField.setText(currentUserShown.get("user_first_name").toString());
            lastNameField.setText(currentUserShown.get("user_last_name").toString());
            birthdayField.setText(currentUserShown.get("user_birthday").toString());

            if(!(currentUserShown.get("user_role").equals("user"))){
                phonePanel.setVisible(false);
            } else{

                if(currentUserShown.get("user_phone") == null){
                    phoneField.setText("");
                } else {
                    phoneField.setText(currentUserShown.get("user_phone").toString());
                }
                phonePanel.setVisible(true);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
