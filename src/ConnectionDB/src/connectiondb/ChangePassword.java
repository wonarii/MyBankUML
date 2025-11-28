import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChangePassword extends JDialog{
    private JPanel changePasswordPanel;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton cancelButton;
    private JButton confirmButton;
    private JPasswordField confirmPassword;

    private DriverScreen driverScreen;
    private JPanel originalScene;

    public ChangePassword(DriverScreen driverScreen, JPanel originalScene) {

        this.driverScreen = driverScreen;
        this.originalScene = originalScene;

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Compare current Password
                int confirmation = JOptionPane.showConfirmDialog(changePasswordPanel, "Are you sure you want to change passwords?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try{

                        boolean status = true;
                        if(!compareCurrentPassword()){
                            JOptionPane.showMessageDialog(changePasswordPanel, "The current password does not match.");
                            status = false;
                        } else if(!compareNewPassword()){
                            JOptionPane.showMessageDialog(changePasswordPanel, "The new password does not match.");
                            status = false;
                        }

                        if(status == true){
                            ConnectionDB db = ConnectionDB.getDatabaseInstance();
                            Authenticator auth = Authenticator.getAuthenticatorInstance();
                            String userEmail = auth.getCurrentUser().get("user_email").toString();

                            boolean successful = db.changePassword(userEmail,new String(newPasswordField.getPassword()));

                            if(successful == true){
                                JOptionPane.showMessageDialog(changePasswordPanel, "Password was successfully changed!");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(changePasswordPanel, "An error occurred while changing the password. Please try again.");
                            }
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(changePasswordPanel, "An error occurred while deleting the branch.");
                    }
                }
            }

                // compare new password with confirmation


        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public boolean compareCurrentPassword(){


        try{
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            Authenticator auth = Authenticator.getAuthenticatorInstance();
            String userEmail = auth.getCurrentUser().get("user_email").toString();

            boolean passwordMatched = db.verifyPassword(userEmail, new String(currentPasswordField.getPassword()));
            return  passwordMatched;

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }



    public boolean compareNewPassword() {
        char[] newPassword = newPasswordField.getPassword();
        char[] confirmationPassword = confirmPassword.getPassword();

        return java.util.Arrays.equals(newPassword, confirmationPassword);
    }


    public static void showChangePasswordScreen(DriverScreen driverScreen, JPanel originalScene){
        ChangePassword newPopup = new ChangePassword(driverScreen, originalScene);
    }
}
