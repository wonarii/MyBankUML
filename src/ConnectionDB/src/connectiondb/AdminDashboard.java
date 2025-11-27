import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard {
    private JComboBox comboBox1;
    private JTextField eGUS343110453TextField;
    private JButton enterButton;
    private JButton createTellerAccountButton;
    private JButton createBankBranchButton;
    private JButton createUserAccountButton1;
    private JButton logOutButton;
    private JPanel adminDashboardPanel;
    private JTable userAccountTable;
    private JScrollPane userAccountsScrollPane;

    private DefaultTableModel userAccountModel;

    private final int AMOUNTOFTABLEROWS = 4;
    private DriverScreen driverScreen;

    final int[] lastSelectedRow = {-1};

    public AdminDashboard(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
        userAccountsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        createBankBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createBankBranch");
            }
        });

        // Store the selected row to allow it to get clicked afterwards
        userAccountTable.getSelectionModel().addListSelectionListener( e -> {
            if(!e.getValueIsAdjusting()) {
                lastSelectedRow[0] = userAccountTable.getSelectedRow();
            }
        });

        // Check if the selected row has been clicked
        userAccountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int clickedRow = userAccountTable.rowAtPoint(e.getPoint());

                // Check to make sure nothing will happen if we click an empty row
                Object testData = userAccountTable.getValueAt(clickedRow, 0);

                if(clickedRow == lastSelectedRow[0] && clickedRow >= 0 && testData != null) {
                    //Get The Selected Row's Information and Send it to the ShowButtonsFunction

                    // Convert the information from the table into an Object[]
                    int columnCount = userAccountModel.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = userAccountModel.getValueAt(clickedRow, i);
                    }

                    // Send this to the Buttons
                    AdminUserButtons.showUserButtons(rowData, driverScreen, adminDashboardPanel);

                }
            }

        });
    }



    private void createUIComponents() {
        adminDashboardPanel = new JPanel();

        // ---------------------- User/Customer Account ---------------------------------
        String[] userColumns = {"Account ID","Email", "First Name","Last Name", "Bank" , "Branch", "Balance"};      // Table Column Names

        // Make a model for userAccounts and lock the fields so they can't be edited in the UI
        userAccountModel = new DefaultTableModel(userColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells non-editable
            }
        };
        // Set up Table stuff
        userAccountTable = new JTable(userAccountModel);                                                // Initialize the User Account Table
        userAccountTable.getColumnModel().getColumn(0).setPreferredWidth(25);                // lower the column width of the Account ID
        userAccountTable.setRowHeight(25);                                                              // Increase the height of a row

        // Create a temporary customer
        // TODO: Remove Later
        Customer tempCust = new Customer("John", "Doe", "joe@gmail.com", new BankBranch(1, "Desjardin Branch", "abc", 11, "1231231234"), new Bank("Desjardins", 1), "password", "1234567890", "2002-01-01", 5000.0);
        Customer tempCust2 = new Customer("Jane", "Doe", "jane@gmail.com", new BankBranch(1, "Desjardin Branch", "abc", 11, "1231231234"), new Bank("Desjardins", 1), "password", "1234567890", "2002-01-01", 5000.0);
        userAccountModel.addRow(tempCust.display());
        userAccountModel.addRow(tempCust2.display());

        // Generate Empty Rows so the table takes up more space
        generateEmptyFillerRows(userAccountModel);
    }

    public JPanel getPanel() {
        return adminDashboardPanel;

    }

    public void generateEmptyFillerRows(DefaultTableModel model) {
        int modelRowCount = model.getRowCount();

        for (int i = modelRowCount; i < AMOUNTOFTABLEROWS; i++) {
            Object[] emptyRow = new Object[model.getColumnCount()];
            model.addRow(emptyRow);
        }
    }

}
