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

    // Scenes
    private ViewTransactionHistoryPage transactionPage;
    private WithdrawPage withdrawPage;
    private DepositPage depositPage;
    private UserDashboard userDashboard;


    private void createUIComponents() {
        // TODO: place custom component creation code here
        activeScene = new JPanel(new CardLayout());

        // Set Up View Transactions Page (Delete Later)
        transactionPage = new ViewTransactionHistoryPage();
        transactionsPanel = transactionPage.getPanel();
        activeScene.add(transactionsPanel, "transactions");

        // Set Up Withdraw Page
        withdrawPage = new WithdrawPage();
        withdrawPanel = withdrawPage.getPanel();
        activeScene.add(withdrawPanel, "withdraw");

        //Set Up Withdraw Page
        depositPage = new DepositPage();
        depositPanel = depositPage.getPanel();
        activeScene.add(depositPanel, "deposit");

        //Set Up User Dashboard Page
        userDashboard = new UserDashboard(this);
        userDashboardPanel = userDashboard.getPanel();
        activeScene.add(userDashboardPanel, "userDashboard");

        // Choose the starting scene
        layout = (CardLayout) activeScene.getLayout();
        layout.show(activeScene, "userDashboard");

    }
    public CardLayout getCardLayout(){
        return layout;
    }

    public JPanel getPanel() {
        return activeScene;
    }

    public void updateTransactionsTable(){
        transactionPage.updateTransactionsView();
    }
}
