import javax.swing.*;

public class SignUpPage extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton signUpButton;
    private JButton backButton;
    private JPanel signUpPanel;

    private void createUIComponents() {
        signUpPanel = new JPanel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back");
    }



    public JPanel getPanel() {
        return signUpPanel;
    }
}
