import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class ViewTransactionHistoryPage {
    private JTable table1;
    private JButton backButton;
    private JPanel transactionsPanel;
    private DefaultTableModel model;

    // Step 1: Get A list of Transactions from the Users (viewTransactions() should return a list of transactions)

    // Step 2: Loop through the list of transactions and gather the information needed

    // Step 3: in the same loop, format the information and add to the JTable using table1.addRow(new Object[] { ....})


    private void createUIComponents() {
        String[] columns = {"TransactionID","User ID","Date","Type","Amount","Description"};

        model = new DefaultTableModel(columns, 0);
        transactionsPanel = new JPanel();
        table1 = new JTable(model);
        backButton = new JButton("Back");

        // Change the column width of the first and second column
        table1.getColumnModel().getColumn(0).setPreferredWidth(25);
        table1.getColumnModel().getColumn(1).setPreferredWidth(25);

        // Adding Dummy Data
        model.addRow(new Object[]{1,2, "12-12-2021", "Deposit", 500.00, "Stuff"});

    }


    public JPanel getPanel() {
        return transactionsPanel;
    }



    public int displayTransactions(Transaction[] transactions) {
        for(int i = 0; i < transactions.length; i++) {
            Transaction item = transactions[i];
            model.addRow(item.display());
        }
        return 0;
    }
}
