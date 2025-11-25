import javax.swing.*;
import java.awt.*;

public class DriverScreen {
    private JPanel activeScene;

    // Panels for the main scenes
    private JPanel transactionsPanel;
    private JPanel withdrawPanel;
    private JPanel depositPanel;
    private JPanel userDashboardPanel;
    private JPanel loginPanel;
    private JPanel signupPanel;
    private JPanel createBankBranchPanel;
    private JPanel adminDashboardPanel;

    private CardLayout layout;

    // Scenes
    private ViewTransactionHistoryPage transactionPage;
    private WithdrawPage withdrawPage;
    private DepositPage depositPage;
    private UserDashboard userDashboard;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
    private CreateBankBranchPage createBankBranchPage;
    private AdminDashboard adminDashboard;


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

        //Set Up Login IN Page
        loginPage = new LoginPage();
        loginPanel = loginPage.getPanel();
        activeScene.add(loginPanel, "login");

        //Set Up Sign Up Page
        signUpPage = new SignUpPage();
        signupPanel = signUpPage.getPanel();
        activeScene.add(signupPanel, "signup");

        //SetUp createBankBranchPage
        createBankBranchPage = new CreateBankBranchPage();
        createBankBranchPanel = createBankBranchPage.getPanel();
        activeScene.add(createBankBranchPanel, "createBankBranch");

        //Set up adminDashboard Page
        adminDashboard = new AdminDashboard();
        adminDashboardPanel = adminDashboard.getPanel();
        activeScene.add(adminDashboardPanel, "adminDashboard");



        // Choose the starting scene
        layout = (CardLayout) activeScene.getLayout();
        layout.show(activeScene, "adminDashboard");

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
