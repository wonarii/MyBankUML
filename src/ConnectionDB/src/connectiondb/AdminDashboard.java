import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard {
    private JComboBox comboBox1;
    private JTextField eGUS343110453TextField;
    private JButton enterButton;
    private JButton viewTransactionHistoryButton;
    private JButton viewAccountInformationButton;
    private JButton deleteUserAccountButton;
    private JButton createTellerAccountButton;
    private JButton createBankBranchButton;
    private JButton createUserAccountButton1;
    private JButton logOutButton;
    private JPanel adminDashboardPanel;

    public AdminDashboard() {
        createBankBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createBankBranch");
            }
        });
    }

    private void createUIComponents() {
        adminDashboardPanel = new JPanel();
    }

    public JPanel getPanel() {
        return adminDashboardPanel;

    }
}
