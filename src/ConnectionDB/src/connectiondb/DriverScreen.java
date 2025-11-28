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
    private JPanel createUserAccountPanel;
    private JPanel tellerDashboardPanel;
    private JPanel accountInformationPanel;
    private JPanel createTellerAccountPanel;

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
    private CreateUserAccountPage createUserAccountPage;
    private TellerDashboard tellerDashboard;
    private ViewAccountInformationPage accountInformationPage;
    private CreateTellerAccountPage createTellerAccountPage;


    private void createUIComponents() {
        // TODO: place custom component creation code here
        activeScene = new JPanel(new CardLayout());

        // Set Up View Transactions Page (Delete Later)
        transactionPage = new ViewTransactionHistoryPage();
        transactionsPanel = transactionPage.getPanel();
        activeScene.add(transactionsPanel, "transactions");

        // Set Up Withdraw Page
        withdrawPage = new WithdrawPage(this);
        withdrawPanel = withdrawPage.getPanel();
        activeScene.add(withdrawPanel, "withdraw");

        //Set Up Withdraw Page
        depositPage = new DepositPage(this);
        depositPanel = depositPage.getPanel();
        activeScene.add(depositPanel, "deposit");

        //Set Up User Dashboard Page
        userDashboard = new UserDashboard(this);
        userDashboardPanel = userDashboard.getPanel();
        activeScene.add(userDashboardPanel, "userDashboard");

        //Set Up Login IN Page
        loginPage = new LoginPage(this);
        loginPanel = loginPage.getPanel();
        activeScene.add(loginPanel, "login");

        //Set Up Sign Up Page
        signUpPage = new SignUpPage();
        signupPanel = signUpPage.getPanel();
        activeScene.add(signupPanel, "signup");

        //SetUp createBankBranchPage
        createBankBranchPage = new CreateBankBranchPage(this);
        createBankBranchPanel = createBankBranchPage.getPanel();
        activeScene.add(createBankBranchPanel, "createBankBranch");

        //Set up adminDashboard Page
        adminDashboard = new AdminDashboard(this);
        adminDashboardPanel = adminDashboard.getPanel();
        activeScene.add(adminDashboardPanel, "adminDashboard");

        //Setup CreateUserAccount Page
        createUserAccountPage = new CreateUserAccountPage(this);
        createUserAccountPanel = createUserAccountPage.getPanel();
        activeScene.add(createUserAccountPanel, "createUserAccount");

        //Set up Teller Dashboard Page
        tellerDashboard = new TellerDashboard(this);
        tellerDashboardPanel = tellerDashboard.getPanel();
        activeScene.add(tellerDashboardPanel, "tellerDashboard");

        //Set up accountInformation page
        accountInformationPage = new ViewAccountInformationPage(this);
        accountInformationPanel = accountInformationPage.getPanel();
        activeScene.add(accountInformationPanel, "accountInformation");

        //Set up Create Teller Account Page
        createTellerAccountPage = new CreateTellerAccountPage(this);
        createTellerAccountPanel = createTellerAccountPage.getPanel();
        activeScene.add(createTellerAccountPanel, "createTellerAccount");

        // Choose the starting scene
        layout = (CardLayout) activeScene.getLayout();
        layout.show(activeScene, "login");

    }
    public CardLayout getCardLayout(){
        return layout;
    }

    public JPanel getPanel() {
        return activeScene;
    }




    // ----------------- Update Functions to update the data displayed when you want to ----------------
    // Example opening TransactionTable page should have the latest data, so call this function
    // when the button to the transaction page is pressed
    // The reason its here is so that other scenes can update if they have access to the Driver Screen

    public void updateTransactionsTable(int userID) {
        transactionPage.updateTransactionsView(userID);
    }

    public void updateUserDashboard(){
        userDashboard.updateBalanceField();
        userDashboard.updateUserName();
    }

    public void updateAccountInformationPage(int userID) {
        accountInformationPage.updateAccountInformationPage(userID);
    }

    public void updateAdminDashboardPage(){

        adminDashboard.updateAdminDashboardPage();
    }

    public void updateCreateUserAccountPage(){
        createUserAccountPage.updateCreateUserAccountPage();
    }

    public void updateCreateBankBranchPage(){
        createBankBranchPage.updateCreateBankBranchPage();
    }

    public void updateCreateTellerAccountPage(){
        createTellerAccountPage.updateCreateTellerAccountPage();
    }

    public void updateTellerDashboardPage(){
        tellerDashboard.updateTellerDashboardPage();
    }
}
