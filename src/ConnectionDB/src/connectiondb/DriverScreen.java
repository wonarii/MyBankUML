import javax.swing.*;
import java.awt.*;

public class DriverScreen {
    private JPanel activeScene;

    // Panels for the main scenes
    private JPanel transactionsPanel;
    private JPanel withdrawPanel;
    private JPanel depositPanel;
    private JPanel userDashboardPanel;
    private CardLayout layout;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        activeScene = new JPanel(new CardLayout());

        // Set Up View Transactions Page (Delete Later)
        ViewTransactionHistoryPage transactionPage = new ViewTransactionHistoryPage();
        transactionsPanel = transactionPage.getPanel();
        activeScene.add(transactionsPanel, "transactions");

        // Set Up Withdraw Page
        WithdrawPage withdrawPage = new WithdrawPage();
        withdrawPanel = withdrawPage.getPanel();
        activeScene.add(withdrawPanel, "withdraw");

        //Set Up Withdraw Page
        DepositPage depositPage = new DepositPage();
        depositPanel = depositPage.getPanel();
        activeScene.add(depositPanel, "deposit");

        //Set Up User Dashboard Page
        UserDashboard userDashboard = new UserDashboard();
        userDashboardPanel = userDashboard.getPanel();
        activeScene.add(userDashboardPanel, "userDashboard");

        // Choose the starting scene
        CardLayout layout = (CardLayout) activeScene.getLayout();
        layout.show(activeScene, "userDashboard");

    }
    public CardLayout getCardLayout(){
        return layout;
    }

    public JPanel getPanel() {
        return activeScene;
    }
}
