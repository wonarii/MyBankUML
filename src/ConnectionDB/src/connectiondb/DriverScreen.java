import javax.swing.*;
import java.awt.*;

public class DriverScreen {
    private JPanel activeScene;

    // Panels for the main scenes
    private JPanel transactionsPanel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        activeScene = new JPanel(new CardLayout());

        ViewTransactionHistoryPage transactionPage = new ViewTransactionHistoryPage();
        transactionsPanel = transactionPage.getPanel();

        activeScene.add(transactionsPanel, "transactions");

        CardLayout layout1 = (CardLayout) activeScene.getLayout();
        layout1.show(activeScene, "transactions");

    }

    public JPanel getPanel() {
        return activeScene;
    }
}
