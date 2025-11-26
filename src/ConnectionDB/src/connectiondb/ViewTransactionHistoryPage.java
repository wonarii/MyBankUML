import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewTransactionHistoryPage{
    private JTable table1;
    private JButton backButton;
    private JPanel transactionsPanel;
    private DefaultTableModel model;

    // Step 1: Get A list of Transactions from the Users (viewTransactions() should return a list of transactions)

    // Step 2: Loop through the list of transactions and gather the information needed

    // Step 3: in the same loop, format the information and add to the JTable using table1.addRow(new Object[] { ....})


    // TODO: Make sure userDashboard goes to the right type of user later
    public ViewTransactionHistoryPage() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = transactionsPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "userDashboard");
            }
        });
    }

    private void createUIComponents() {
        String[] columns = {"TransactionID","User ID","Date","Type","Amount"};

        model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells non-editable
            }
        };
        transactionsPanel = new JPanel();
        table1 = new JTable(model);
        backButton = new JButton("Back");

        // Change the column width of the first and second column
        table1.getColumnModel().getColumn(0).setPreferredWidth(25);
        table1.getColumnModel().getColumn(1).setPreferredWidth(25);

        //TODO: Change this
        updateTransactionsView();

    }


    public JPanel getPanel() {
        return transactionsPanel;
    }

    public void updateTransactionsView() {
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        if(auth.getCurrentUser() != null){
            int tempUserID = (int) Authenticator.getAuthenticatorInstance().getCurrentUser().get("id");
            displayTransactions(Transaction.convertTransactionsFromDatabase(tempUserID));
        }

    }



    public int displayTransactions(Transaction[] transactions) {
        model.setRowCount(0);
        for(int i = 0; i < transactions.length; i++) {
            Transaction item = transactions[i];
            model.addRow(item.display());
        }
        return 0;
    }
}
