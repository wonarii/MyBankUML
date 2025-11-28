import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ResetPassword extends JDialog{
    private JPasswordField newPasswordField;
    private JPasswordField confirmPassword;
    private JButton cancelButton;
    private JButton confirmButton;
    private JPanel resetPasswordPanel;



    private DriverScreen driverScreen;
    private int shownUserID;

    public ResetPassword(DriverScreen driverScreen, int shownUserID) {

        this.driverScreen = driverScreen;
        this.shownUserID = shownUserID;

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Compare current Password
                int confirmation = JOptionPane.showConfirmDialog(resetPasswordPanel, "Are you sure you want to reset password?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try{

                        boolean status = true;
                        if(!compareNewPassword()){
                            JOptionPane.showMessageDialog(resetPasswordPanel, "The new password does not match.");
                            status = false;
                        }

                        if(status == true){
                            ConnectionDB db = ConnectionDB.getDatabaseInstance();
                            Authenticator auth = Authenticator.getAuthenticatorInstance();
                            String userEmail = db.getUserByID(shownUserID).get("user_email").toString();

                            boolean successful = db.changePassword(userEmail,new String(newPasswordField.getPassword()));

                            if(successful == true){

                                JOptionPane.showMessageDialog(resetPasswordPanel, "Password was successfully changed!");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(resetPasswordPanel, "An error occurred while changing the password. Please try again.");
                            }
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(resetPasswordPanel, "An error occurred while changing password. Please try again.");
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setTitle("Reset Password");
        setModal(true);
        add(resetPasswordPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public boolean compareNewPassword() {
        char[] newPassword = newPasswordField.getPassword();
        char[] confirmationPassword = confirmPassword.getPassword();

        return java.util.Arrays.equals(newPassword, confirmationPassword);
    }


    public static void showResetPasswordScreen(DriverScreen driverScreen, int shownUserID){
        ResetPassword newPopup = new ResetPassword(driverScreen, shownUserID);
    }
}
