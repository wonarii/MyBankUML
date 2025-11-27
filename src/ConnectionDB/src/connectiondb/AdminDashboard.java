import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class AdminDashboard {
    private JComboBox customerComboBox;
    private JButton enterUserButton;
    private JButton createTellerAccountButton;
    private JButton createBankBranchButton;
    private JButton createUserAccountButton;
    private JButton logOutButton;
    private JPanel adminDashboardPanel;
    private JTable userAccountTable;
    private JScrollPane userAccountsScrollPane;
    private JTable tellerAccountTable;
    private JScrollPane tellerAccountsScrollPane;
    private JLabel adminNameField;
    private JLabel headerNameField;
    private JScrollPane branchScrollPane;
    private JTable branchTable;
    private JButton enterTellerButton;
    private JButton enterBranchButton;
    private JComboBox tellerComboBox;
    private JComboBox branchComboBox;
    private JTextField tellerTextField;
    private JTextField branchTextField;
    private JTextField customerTextField;

    private DefaultTableModel userAccountModel;
    private DefaultTableModel tellerAccountModel;
    private DefaultTableModel branchModel;

    private final int AMOUNTOFTABLEROWS = 4;
    private DriverScreen driverScreen;

    final int[] lastSelectedRow = {-1};
    final int[] lastSelectedRowTeller = {-1};
    final int[] lastSelectedRowBranch = {-1};


    public AdminDashboard(DriverScreen driverScreen) {
        this.driverScreen = driverScreen;
        userAccountsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tellerAccountsScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        branchScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        createBankBranchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateBankBranchPage();
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createBankBranch");
            }
        });

        // ================================ User Table ============================================
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

        // ======================================== Teller Table ==========================================
        // Store the selected row to allow it to get clicked afterwards
        tellerAccountTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                lastSelectedRowTeller[0] = tellerAccountTable.getSelectedRow();
            }
        });

        // Check if the selected row has been clicked
        tellerAccountTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int clickedRow = tellerAccountTable.rowAtPoint(e.getPoint());

                // Check to make sure nothing will happen if we click an empty row
                Object testData = tellerAccountTable.getValueAt(clickedRow, 0);

                if(clickedRow == lastSelectedRowTeller[0] && clickedRow >= 0 && testData != null) {
                    //Get The Selected Row's Information and Send it to the ShowButtonsFunction

                    // Convert the information from the table into an Object[]
                    int columnCount = tellerAccountModel.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = tellerAccountModel.getValueAt(clickedRow, i);
                    }

                    // Send this to the Buttons
                    AdminTellerButtons.showTellerButtons(rowData, driverScreen, adminDashboardPanel);

                }
            }

        });

        // =============================== Branch Table =========================================
        branchTable.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                lastSelectedRowBranch[0] = branchTable.getSelectedRow();
            }
        });

        // Check if the selected row has been clicked
        branchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int clickedRow = branchTable.rowAtPoint(e.getPoint());

                // Check to make sure nothing will happen if we click an empty row
                Object testData = branchTable.getValueAt(clickedRow, 0);

                if(clickedRow == lastSelectedRowBranch[0] && clickedRow >= 0 && testData != null) {
                    //Get The Selected Row's Information and Send it to the ShowButtonsFunction

                    // Convert the information from the table into an Object[]
                    int columnCount = branchModel.getColumnCount();
                    Object[] rowData = new Object[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = branchModel.getValueAt(clickedRow, i);
                    }

                    // Send this to the Buttons
                    AdminBranchButtons.showBranchButtons(rowData, driverScreen, adminDashboardPanel);

                }
            }

        });
        createTellerAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateTellerAccountPage();
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createTellerAccount");
            }
        });
        createUserAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driverScreen.updateCreateUserAccountPage();
                Container parent = adminDashboardPanel.getParent();
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "createUserAccount");
            }
        });


        enterUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSearchResults(customerComboBox, customerTextField);
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


        // ============================================ Tellers ================================================
        String[] adminColumns = {"Account ID","Email","First Name","Last Name","Bank","Branch"};
        tellerAccountModel = new DefaultTableModel(adminColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tellerAccountTable = new JTable(tellerAccountModel);
        tellerAccountTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        tellerAccountTable.setRowHeight(25);

        // ================================== Branches Stuff ====================================
        String[] branchColumns = {"Branch ID", "Branch","Location","Branch Phone", "Bank ID"};

        branchModel = new DefaultTableModel(branchColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        branchTable = new JTable(branchModel);
        branchTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        branchTable.setRowHeight(25);

        updateUserTable();
        updateTellerTable();
        updateBranchTable();
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

    public void updateAdminDashboardPage(){
        Authenticator auth = Authenticator.getAuthenticatorInstance();
        String adminName = auth.getCurrentUser().get("user_first_name").toString() + " " + auth.getCurrentUser().get("user_last_name");
        adminNameField.setText(adminName);
        headerNameField.setText(adminName);

        updateUserTable();
        updateTellerTable();
        updateBranchTable();


    }

    /**
     * This function will update the table for the users
     */
    public void updateUserTable(){

        userAccountModel.setRowCount(0);
        // Create a temporary customer
        // TODO: Remove Later
        Customer tempCust = new Customer("John", "Doe", "joe@gmail.com", new BankBranch(1, "Desjardin Branch", "abc", 11, "1231231234"), new Bank("Desjardins", 1), "password", "1234567890", "2002-01-01", 5000.0);
        Customer tempCust2 = new Customer("Jane", "Doe", "jane@gmail.com", new BankBranch(1, "Desjardin Branch", "abc", 11, "1231231234"), new Bank("Desjardins", 1), "password", "1234567890", "2002-01-01", 5000.0);

        // TODO: Replace this line with the function
        Customer[] tempCustomers = new Customer[]{tempCust, tempCust2};

        for(Customer tempCustomer : tempCustomers) {
            userAccountModel.addRow(tempCustomer.display());
        }
        generateEmptyFillerRows(userAccountModel);
    }

    // This method is called when the admin presses the 'Enter' button
    public void updateSearchResults(JComboBox comboBox, JTextField input) {
        try {
            ConnectionDB db = ConnectionDB.getDatabaseInstance();
            Customer[] customers = new Customer[]{};
            if (comboBox.getSelectedItem().equals("AccountID")) {
                customers = (Customer[]) db.searchById(input.getText());
            } else if (comboBox.getSelectedItem().equals("BranchID")) {
                customers = db.searchByBranchId(input.getText());
            } else if (comboBox.getSelectedItem().equals("Name")) {
                customers = db.searchByName(input.getText());
            }

            userAccountModel.setRowCount(0);
            for(Customer customer : customers) {
                userAccountModel.addRow(customer.display());
            }
            generateEmptyFillerRows(userAccountModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * This function will update the table for the Tellers
     */
    public void updateTellerTable(){

        tellerAccountModel.setRowCount(0);
        // Create a temporary Teller
        // TODO: Remove Later
        BankTeller tempTeller = new BankTeller("Alice", "Rabbit", "a@gmail.com", new BankBranch(1, "Desjardin Branch", "abc", 11, "1231231234"),new Bank("Desjardins", 1), "abc123" );


        // TODO: Replace this line with the function
        BankTeller[] tempTellerList = new BankTeller[]{tempTeller};

        for(BankTeller teller : tempTellerList) {
            tellerAccountModel.addRow(teller.display());
        }
        generateEmptyFillerRows(tellerAccountModel);
    }

    /**
     * This function will update the table for the Branches
     */
    public void updateBranchTable(){
        branchModel.setRowCount(0);
        // Create a temporary BankBranch
        // TODO: ADD THE SEARCH RESULTS
        BankBranch tempBankBranch = new BankBranch(1, "Desjardin Branch", "abc", 11, "1231231234");


        // TODO: Replace this line with the function
        BankBranch[] tempBranchList = new BankBranch[]{tempBankBranch};

        for(BankBranch branch : tempBranchList) {
            branchModel.addRow(branch.display());
        }

        generateEmptyFillerRows(branchModel);
    }

}
